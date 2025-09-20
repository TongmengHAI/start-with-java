package com.example.ratelimit;

import com.example.ratelimit.ratelimit.RateLimitProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RateLimitProperties.class)
public class RatelimitApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatelimitApplication.class, args);
	}

}
