package com.example.ratelimit.ratelimit;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "ratelimit")
public class RateLimitProperties {

    private boolean enabled = true;
    private int limit = 100;
    private int windowSeconds = 60;
    private String bucketName = "api";
    private String headerName; // optional; if set, use header-based identity

    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public void setLimit(int limit) { this.limit = limit; }

    public void setWindowSeconds(int windowSeconds) { this.windowSeconds = windowSeconds; }

    public void setBucketName(String bucketName) { this.bucketName = bucketName; }

    public void setHeaderName(String headerName) { this.headerName = headerName; }
}
