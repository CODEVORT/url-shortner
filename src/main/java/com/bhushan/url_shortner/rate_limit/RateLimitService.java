package com.bhushan.url_shortner.rate_limit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.bhushan.url_shortner.exceptions.RateLimitExceededException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RateLimitService {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
	
	private final StringRedisTemplate  redisTemplate;
	private final RateLimitProperties properties;
	
	public void validateCreateUrlLimit(String ipAddress)
	{
		String key = buildKey(ipAddress);
		Long count = redisTemplate.opsForValue().increment(key);
		
		if (count != null && count == 1) {
            redisTemplate.expire(
                    key,
                    properties.windowSeconds(),
                    TimeUnit.SECONDS
            );
        }
		
		if (count != null && count > properties.maxRequests()) {
            throw new RateLimitExceededException(
                    "Too many requests. Please try again later."
            );
        }
	}
	
	private String buildKey(String ipAddress) {

        String currentMinute =
                LocalDateTime.now().format(FORMATTER);

        return String.format(
                "rate_limit:create_url:%s:%s",
                ipAddress,
                currentMinute
        );
    }
}
