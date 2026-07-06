package com.bhushan.url_shortner.services;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisCacheService implements CacheService {

	public final StringRedisTemplate redisTemplate;
	
	
	//function to get redirect url from the redis if present
	@Override
	public String get(String key) {
		
		try
		{
			return redisTemplate
					.opsForValue()
					.get(key);
		}catch(Exception e)
		{
			log.error("Redis gt failed for key = {}", key , e);
			return null;
			
		}
	}

	@Override
	public void put(String key, String value) {
		try
		{
			redisTemplate.opsForValue()
			.set(
					key, 
					value , 
					Duration.ofHours(24)
					
				);
			
		}catch(Exception e)
		{
			log.error("Redis put failed for key = {} " , key , e );
			
		}
		
	}

	@Override
	public void evict(String key) {
		try
		{
			redisTemplate
				.delete(key);
		}catch(Exception e)
		{
			log.error(
                    "Redis DELETE failed for key={}",
                    key,
                    e
            );
		}
	}
	
	
	/*                        
	 *                         WHY CATCH EXCEPTIONS
	 *   
	 *    Consider Scenerio :- Redis is down and application is using get(key) function :- 
	 *    
	 *    	in this scenerio redis will throw an exception RedisConnectionFailureException but our
	 *    api will return 500-Internal Server Error Redis is cahce (memory) and PostgreSQL is the SOurce OF Truth
	 *    If exception then it should fallback to Database
	 *    
	 *    
	 *   
	 *   
	 *   
	 *   */

}
