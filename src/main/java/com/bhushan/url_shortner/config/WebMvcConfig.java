package com.bhushan.url_shortner.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bhushan.url_shortner.rate_limit.RateLimitInterceptor;

import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
	
	 private final RateLimitInterceptor rateLimitInterceptor;

	    @Override
	    public void addInterceptors(
	            InterceptorRegistry registry) {

	        registry.addInterceptor(rateLimitInterceptor)
	                .addPathPatterns("/api/v1/urls");
	    }

}
