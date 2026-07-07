package com.bhushan.url_shortner.services;

public interface RedirectService {
	String getOriginalUrl(String shortCode);
}
