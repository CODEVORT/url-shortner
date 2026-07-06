package com.bhushan.url_shortner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {
	
	@Bean
	public StringRedisTemplate stringRedsiTemplate(
			RedisConnectionFactory redisConnectionFactory
			)
	{
		return new StringRedisTemplate(
				redisConnectionFactory
				);
	}
	
}
