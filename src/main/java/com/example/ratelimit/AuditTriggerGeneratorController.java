package com.example.ratelimit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(
        value = "/api/audit",
        produces = "text/plain; charset=UTF-8"
)
public class AuditTriggerGeneratorController {

    @Autowired
    private AuditTriggerGeneratorService service;

    // GET /api/audit/generate/students
    @GetMapping("/generate")
    public String  generate(
            @RequestParam(defaultValue = "") String table,
            @RequestParam(required = false) String schema
    ) throws Exception {
        return service.generateAuditTriggers(table, schema);

    }

}