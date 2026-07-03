package com.bhushan.url_shortner.services;

import java.lang.StackWalker.Option;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bhushan.url_shortner.UrlShortnerApplication;
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

    private final UrlRepository urlRepository;

    private final ShortCodeGenerator shortCodeGenerator;

    @Value("${app.base-url}")
    private String baseUrl;

    @Override
    public CreateShortUrlResponse createShortUrl(
            CreateShortUrlRequest request
    ) {

        String originalUrl =
                request.original_url();

        Optional<UrlEntity> existing =
                urlRepository.findByOriginalUrl(originalUrl);

        if (existing.isPresent()) {
            return map(existing.get());
        }

        String shortCode =
                shortCodeGenerator.generateUniqueCode(
                        originalUrl
                );

        UrlEntity entity =
                new UrlEntity();

        entity.setOriginalUrl(originalUrl);
        entity.setShortCode(shortCode);

        UrlEntity saved =
                urlRepository.save(entity);

        return map(saved);
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