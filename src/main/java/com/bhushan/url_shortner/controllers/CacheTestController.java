package com.bhushan.url_shortner.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bhushan.url_shortner.services.CacheService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cache")
@RequiredArgsConstructor
public class CacheTestController {
	private final CacheService cacheService;
	
	@GetMapping("/test")
	public String test()
	{
		cacheService.put("hello", "world");
		return cacheService.get("hello");
	}
}
