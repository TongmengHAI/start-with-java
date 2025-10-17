package com.example.ratelimit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuditTriggerGeneratorService {

    @Autowired
    private JdbcTemplate jdbc;

    /**
     * Generate 5 triggers (BI, AI, BU, AU, AD) for the given table, targeting the audit_logs schema:
     *   id | created_at | ip_address | method | new_value | old_value | operation | path | row_id | table_name | user_agent | create_by
     */
    public String generateAuditTriggers(String tableName, String schema) throws SQLException {
        try (Connection con = Objects.requireNonNull(jdbc.getDataSource()).getConnection()) {
            DatabaseMetaData md = con.getMetaData();
            String catalog = con.getCatalog();
            String schemaPattern = (schema == null || schema.isBlank()) ? con.getSchema() : schema;

            List<ColumnInfo> cols = readColumns(md, catalog, schemaPattern, tableName);
            if (cols.isEmpty()) {
                throw new IllegalArgumentException("No columns found for table: " + tableName);
            }

            String pkCol = primaryKey(md, catalog, schemaPattern, tableName)
                    .orElseGet(() -> guessFallbackPk(cols, tableName));

            boolean hasCreatedBy = hasColumn(cols, "created_by");
            boolean hasUpdatedBy = hasColumn(cols, "updated_by");

            String jsonOld = buildJsonObjectPairs(cols, "OLD");
            String jsonNew = buildJsonObjectPairs(cols, "NEW");

            String bi = beforeInsert(tableName, hasCreatedBy, hasUpdatedBy);
            String ai = afterInsert(tableName, pkCol, jsonNew);
            String bu = beforeUpdate(tableName, hasUpdatedBy);
            String au = afterUpdate(tableName, pkCol, jsonOld, jsonNew);
            String ad = afterDelete(tableName, pkCol, jsonOld);

            return String.join("\n\n", bi, ai, bu, au, ad);
        }
    }

    // ======== internals ========

    private static final class ColumnInfo {
        String name;
        String typeName;
        int dataType;   // java.sql.Types
        int ordinal;    // 1-based if available
    }

    private List<ColumnInfo> readColumns(DatabaseMetaData md, String catalog, String schema, String table) throws SQLException {
        List<ColumnInfo> list = new ArrayList<>();
        try (ResultSet rs = md.getColumns(catalog, schema, table, null)) {
            while (rs.next()) {
                ColumnInfo c = new ColumnInfo();
                c.name = rs.getString("COLUMN_NAME");
                c.typeName = rs.getString("TYPE_NAME");
                c.dataType = rs.getInt("DATA_TYPE");
                int ord = 999999;
                try { ord = rs.getInt("ORDINAL_POSITION"); } catch (SQLException ignore) {}
                c.ordinal = ord;
                list.add(c);
            }
        }
        list.sort(Comparator.comparingInt(ci -> ci.ordinal));
        return list;
    }

    private Optional<String> primaryKey(DatabaseMetaData md, String catalog, String schema, String table) throws SQLException {
        try (ResultSet rs = md.getPrimaryKeys(catalog, schema, table)) {
            if (rs.next()) return Optional.ofNullable(rs.getString("COLUMN_NAME"));
        }
        return Optional.empty();
    }

    private String guessFallbackPk(List<ColumnInfo> cols, String table) {
        for (String c : List.of("id", table + "_id", "pk", "uuid")) {
            if (hasColumn(cols, c)) return c;
        }
        return cols.getFirst().name; // last resort
    }

    private boolean hasColumn(List<ColumnInfo> cols, String name) {
        for (ColumnInfo c : cols) if (c.name.equalsIgnoreCase(name)) return true;
        return false;
    }

    private String buildJsonObjectPairs(List<ColumnInfo> cols, String q) {
        return cols.stream()
                .map(c -> "'" + c.name + "', " + valueExpr(c, q))
                .collect(Collectors.joining(",\n      "));
    }

    private String valueExpr(ColumnInfo c, String q) {
        String col = q + ".`" + c.name + "`";
        String tn = (c.typeName == null ? "" : c.typeName).toUpperCase(Locale.ROOT);

        if (tn.contains("DATETIME") || tn.contains("TIMESTAMP")) {
            return "DATE_FORMAT(" + col + ", '%Y-%m-%d %H:%i:%s')";
        } else if (tn.equals("DATE")) {
            return "DATE_FORMAT(" + col + ", '%Y-%m-%d')";
        } else if (tn.equals("TIME")) {
            return "DATE_FORMAT(" + col + ", '%H:%i:%s')";
        }
        return col;
    }

    private String qIdent(String ident) {
        return "`" + ident.replace("`", "``") + "`";
    }

    private String triggerHeaderDropCreate(String trigName, String event, String table) {
        return ""
                + "DROP TRIGGER IF EXISTS " + qIdent(trigName) + ";\n"
                + "DELIMITER $$\n"
                + "CREATE TRIGGER " + qIdent(trigName) + "\n"
                + event + " ON " + qIdent(table) + " FOR EACH ROW\n";
    }

    // BEFORE INSERT: created_by (if present), updated_by -> NULL (if present)
    private String beforeInsert(String table, boolean hasCreatedBy, boolean hasUpdatedBy) {
        String trig = "trg_" + table + "_bi";
        StringBuilder sb = new StringBuilder();
        sb.append(triggerHeaderDropCreate(trig, "BEFORE INSERT", table));
        sb.append("BEGIN\n");
        if (hasCreatedBy) {
            sb.append("  SET NEW.`created_by` = COALESCE(@app_user, USER());\n");
        }
        if (hasUpdatedBy) {
            sb.append("  SET NEW.`updated_by` = NULL;\n");
        }
        sb.append("END$$\nDELIMITER ;");
        return sb.toString();
    }

    // AFTER INSERT: write new_value only
    private String afterInsert(String table, String pkCol, String jsonNew) {
        String trig = "trg_" + table + "_ai";
        return triggerHeaderDropCreate(trig, "AFTER INSERT", table)
                + "BEGIN\n"
                + "  INSERT INTO `audit_logs` (\n"
                + "    `table_name`, `operation`, `row_id`, `old_value`, `new_value`,\n"
                + "    `ip_address`, `create_by`\n"
                + "  ) VALUES (\n"
                + "    '" + table + "', 'INSERT', NEW." + qIdent(pkCol) + ",\n"
                + "    NULL,\n"
                + "    JSON_OBJECT(\n"
                + "      " + jsonNew + "\n"
                + "    ),\n"
                + "    COALESCE(@ip_address, NULL),\n"
                + "    COALESCE(@app_user, USER())\n"
                + "  );\n"
                + "END$$\nDELIMITER ;";
    }

    // BEFORE UPDATE: updated_by (if present)
    private String beforeUpdate(String table, boolean hasUpdatedBy) {
        String trig = "trg_" + table + "_bu";
        StringBuilder sb = new StringBuilder();
        sb.append(triggerHeaderDropCreate(trig, "BEFORE UPDATE", table));
        sb.append("BEGIN\n");
        if (hasUpdatedBy) {
            sb.append("  SET NEW.`updated_by` = COALESCE(@app_user, USER());\n");
        }
        sb.append("END$$\nDELIMITER ;");
        return sb.toString();
    }

    // AFTER UPDATE: write old_value & new_value
    private String afterUpdate(String table, String pkCol, String jsonOld, String jsonNew) {
        String trig = "trg_" + table + "_au";
        return triggerHeaderDropCreate(trig, "AFTER UPDATE", table)
                + "BEGIN\n"
                + "  INSERT INTO `audit_logs` (\n"
                + "    `table_name`, `operation`, `row_id`, `old_value`, `new_value`,\n"
                + "    `ip_address`, `create_by`\n"
                + "  ) VALUES (\n"
                + "    '" + table + "', 'UPDATE', NEW." + qIdent(pkCol) + ",\n"
                + "    JSON_OBJECT(\n"
                + "      " + jsonOld + "\n"
                + "    ),\n"
                + "    JSON_OBJECT(\n"
                + "      " + jsonNew + "\n"
                + "    ),\n"
                + "    COALESCE(@ip_address, NULL),\n"
                + "    COALESCE(@app_user, USER())\n"
                + "  );\n"
                + "END$$\nDELIMITER ;";
    }

    // AFTER DELETE: write old_value only
    private String afterDelete(String table, String pkCol, String jsonOld) {
        String trig = "trg_" + table + "_ad";
        return triggerHeaderDropCreate(trig, "AFTER DELETE", table)
                + "BEGIN\n"
                + "  INSERT INTO `audit_logs` (\n"
                + "    `table_name`, `operation`, `row_id`, `old_value`, `new_value`,\n"
                + "    `ip_address`, `create_by`\n"
                + "  ) VALUES (\n"
                + "    '" + table + "', 'DELETE', OLD." + qIdent(pkCol) + ",\n"
                + "    JSON_OBJECT(\n"
                + "      " + jsonOld + "\n"
                + "    ),\n"
                + "    NULL,\n"
                + "    COALESCE(@ip_address, NULL),\n"
                + "    COALESCE(@app_user, USER())\n"
                + "  );\n"
                + "END$$\nDELIMITER ;";
    }
}
