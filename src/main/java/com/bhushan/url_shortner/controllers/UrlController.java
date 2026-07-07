package com.bhushan.url_shortner.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bhushan.url_shortner.dtos.CreateShortUrlRequest;
import com.bhushan.url_shortner.dtos.CreateShortUrlResponse;
import com.bhushan.url_shortner.services.UrlService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/urls")
@RequiredArgsConstructor
public class UrlController {
	
	private final UrlService urlService;
	
	@PostMapping
	public ResponseEntity<CreateShortUrlResponse> createShortUrl(
			@Valid @RequestBody CreateShortUrlRequest request
			)
	{
		
		return ResponseEntity.ok(
				urlService.createShortUrl(request)
				);
	}
	
}
