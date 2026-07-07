package com.bhushan.url_shortner.controllers;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bhushan.url_shortner.services.RedirectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RedirectController {
	private final RedirectService redirectService;
	
	@GetMapping("/{shortCode}")
	public ResponseEntity<Void> redirect(@PathVariable String shortCode)
	{
		String originalUrl = redirectService.getOriginalUrl(shortCode);
		System.out.println("Redirecting " + shortCode + " -> " + originalUrl);
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
		
	}
}
