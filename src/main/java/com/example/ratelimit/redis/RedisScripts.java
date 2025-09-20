package com.example.ratelimit.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.script.DefaultRedisScript;

@Configuration
public class RedisScripts {

    @Bean
    public DefaultRedisScript<Long> incrementWithTtlScript() {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setScriptText(
                "local c = redis.call('INCR', KEYS[1]); " +
                        "return c;"
        );
        return script;
    }
}
//   "if c == 1 then redis.call('EXPIRE', KEYS[1], ARGV[1]); end; " +
