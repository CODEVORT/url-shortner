package com.bhushan.url_shortner.services;

import com.bhushan.url_shortner.dtos.CreateShortUrlRequest;
import com.bhushan.url_shortner.dtos.CreateShortUrlResponse;

public interface UrlService {
	CreateShortUrlResponse createShortUrl(CreateShortUrlRequest request);
}
