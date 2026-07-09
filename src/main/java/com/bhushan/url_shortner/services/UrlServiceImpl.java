package com.bhushan.url_shortner.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bhushan.url_shortner.cache.CacheConstants;
import com.bhushan.url_shortner.dtos.CreateShortUrlRequest;
import com.bhushan.url_shortner.dtos.CreateShortUrlResponse;
import com.bhushan.url_shortner.entities.UrlEntity;
import com.bhushan.url_shortner.generator.ShortCodeGenerator;
import com.bhushan.url_shortner.repositoires.UrlRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class UrlServiceImpl implements UrlService {
	
	private static final Logger log = LoggerFactory.getLogger(UrlServiceImpl.class);

    private final UrlRepository urlRepository;
    private final CacheService  cacheServie;
    private final ShortCodeGenerator shortCodeGenerator;

    @Value("${app.base-url}")
    private String baseUrl;

    @Override
    public CreateShortUrlResponse createShortUrl(CreateShortUrlRequest request) {
    	long start = System.currentTimeMillis();
    	

        String originalUrl =
                request.original_url();
                
        Optional<UrlEntity> existing =
                urlRepository.findByOriginalUrl(originalUrl);
        
        log.info("findByOriginalUrl took {} ms",
                System.currentTimeMillis() - start);

        if (existing.isPresent()) {
            return map(existing.get());
        }

        try
        {
        	String shortCode =
                    shortCodeGenerator.generateUniqueCode(
                            originalUrl
                    );
        	UrlEntity entity =
                    new UrlEntity();
        	
        	entity.setOriginalUrl(originalUrl);
            entity.setShortCode(shortCode);
            
            UrlEntity saved =
                    urlRepository.saveAndFlush(entity);
            
            String cacheKey = CacheConstants.URL_KEY_PREFRIX + saved.getShortCode();
            
            
            long cacheStart = System.currentTimeMillis();
            cacheServie.put(
            		cacheKey, 
            		saved.getOriginalUrl()
            		);
            
            log.info("redis put took time = {} " , System.currentTimeMillis() - cacheStart);
            
            

            return map(saved);
        }catch(DataIntegrityViolationException e)
        {
        	UrlEntity alreadyCreated =
        			urlRepository.findByOriginalUrl(originalUrl)
        				.orElseThrow();
        	
        	return map(alreadyCreated);
        }finally
        {
        	log.info("service took time = {} " , System.currentTimeMillis() - start);
        }
        
    }

    private CreateShortUrlResponse map(
            UrlEntity entity
    ) {

        return new CreateShortUrlResponse(
                entity.getUrlId(),
                entity.getOriginalUrl(),
                entity.getShortCode(),
                baseUrl + "/" + entity.getShortCode()
        );
    }
}