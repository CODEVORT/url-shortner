package com.bhushan.url_shortner.services;

import org.springframework.stereotype.Service;

import com.bhushan.url_shortner.cache.CacheConstants;
import com.bhushan.url_shortner.entities.UrlEntity;
import com.bhushan.url_shortner.exceptions.UrlNotFoundException;
import com.bhushan.url_shortner.repositoires.UrlRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedirectServiceImpl implements RedirectService {
	
	
	private final UrlRepository urlRepository;
	private final CacheService cacheService;
	
	
	@Override
	public String getOriginalService(String shortCode) {

		String cacheKey = CacheConstants.URL_KEY_PREFRIX + shortCode;
		
		String cachedUrl = cacheService.get(cacheKey);
		
		if(cachedUrl != null)
		{
			log.info(
					"Cache HIT for shortCode = {} " ,
					shortCode
					);
			
			return shortCode;
		}
		log.info(
                "Cache MISS for shortCode={}",
                shortCode
        );
		
		UrlEntity urlEntity = urlRepository
									.findByShortCode(shortCode)
									.orElseThrow(
											() -> 
											new UrlNotFoundException(
													"Short code not found: " + shortCode
													)
											);
		cacheService.put(cacheKey, urlEntity.getOriginalUrl());
		return urlEntity.getOriginalUrl();
	}


	
	

}
