package com.bhushan.url_shortner.dtos;

public record CreateShortUrlResponse(
		Long id,
		String originalUrl,
		String shortCode,
		String shortUrl
		) {
	
}
