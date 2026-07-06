package com.bhushan.url_shortner.services;

public interface CacheService {
	String get(String key);
	
	void put(String key , String value);
	
	void evict(String key);
	
	
}
