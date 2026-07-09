package com.bhushan.url_shortner.rate_limit;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rate-limit.create-url")
public record RateLimitProperties(
			long maxRequests,
			long windowSeconds
		) {
	
}
