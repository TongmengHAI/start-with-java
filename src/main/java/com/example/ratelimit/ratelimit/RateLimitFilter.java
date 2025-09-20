package com.example.ratelimit.ratelimit;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;

@Order(Ordered.HIGHEST_PRECEDENCE) // ensure it runs early
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final StringRedisTemplate redis;
    private final DefaultRedisScript<Long> incrScript;
    private final RateLimitProperties props;

    public RateLimitFilter(StringRedisTemplate redis,
                           DefaultRedisScript<Long> incrementWithTtlScript,
                           RateLimitProperties props) {
        this.redis = redis;
        this.incrScript = incrementWithTtlScript;
        this.props = props;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest req) {
        // Skip limiter for non-API paths or when disabled
        String p = req.getRequestURI();
        return !props.isEnabled()
                || p.startsWith("/actuator")
                || p.startsWith("/swagger")
                || p.equals("/favicon.ico");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws ServletException, IOException {

        int windowSec = props.getWindowSeconds();
        int limit = props.getLimit();


        long now = Instant.now().getEpochSecond();
        long windowStart = (now / windowSec) * windowSec;
        long windowEnd = windowStart + windowSec;

        String clientId = resolveClientId(req);
        String key = String.format("rl:%s:%s:%d",
                props.getBucketName(), clientId, windowStart);

        Long count = redis.execute(incrScript,
                Collections.singletonList(key),
                String.valueOf(windowSec));

        long used = (count == null) ? 1L : count;

        String path = req.getRequestURI();


//        Per-route limits
        if (path.startsWith("/auth/login")) { limit = 10; windowSec = 60; }
        if (path.startsWith("/reports/"))   { limit = 30; windowSec = 60; }


        // Helpful headers
        res.setHeader("X-RateLimit-Limit", String.valueOf(limit));
        res.setHeader("X-RateLimit-Remaining", String.valueOf(Math.max(0, limit - used)));
        res.setHeader("X-RateLimit-Reset", String.valueOf(windowEnd));
        res.setHeader("Access-Control-Expose-Headers",
                "X-RateLimit-Limit,X-RateLimit-Remaining,X-RateLimit-Reset,Retry-After");

        // Block if over the limit
        if (used > limit) {
            long retryAfter = Math.max(1, windowEnd - now);
            res.setHeader("Retry-After", String.valueOf(retryAfter));
            res.setStatus(429);
            res.setContentType("application/json");
            res.getWriter().write("{\"error\":\"rate_limited\",\"message\":\"Too many requests. Please retry later.\"}");
            return; // stop chain
        }

        chain.doFilter(req, res);
    }

    private String resolveClientId(HttpServletRequest req) {
        // 1) API key header (if configured)
        if (StringUtils.hasText(props.getHeaderName())) {
            String apiKey = req.getHeader(props.getHeaderName());
            if (StringUtils.hasText(apiKey)) return "h:" + apiKey.trim();
        }
        // 2) X-Forwarded-For (first IP)
        String xff = req.getHeader("X-Forwarded-For");
        if (StringUtils.hasText(xff)) {
            String first = xff.split(",")[0].trim();
            if (StringUtils.hasText(first)) return "ip:" + normalizeLoopback(first);
        }
        // 3) Remote address
        return "ip:" + normalizeLoopback(req.getRemoteAddr());
    }

    private String normalizeLoopback(String ip) {
        // normalize IPv6 loopback to IPv4 for prettier keys
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
