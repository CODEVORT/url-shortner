package com.bhushan.url_shortner.controllers;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bhushan.url_shortner.services.RedirectService;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
public class RedirectController {
	private final RedirectService redirectService;
	
	@GetMapping("/{shortCode}")
	public ResponseEntity<Void> redirect(
			@Pattern(
			        regexp = "^[A-Za-z0-9]{8}$",
			        message = "Invalid short code format"
			    )
			@PathVariable String shortCode
			)
	{
		String originalUrl = redirectService.getOriginalUrl(shortCode);
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
		
	}
}
