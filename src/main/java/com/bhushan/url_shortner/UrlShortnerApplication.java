package com.bhushan.url_shortner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.bhushan.url_shortner.rate_limit.RateLimitProperties;

@SpringBootApplication
@EnableConfigurationProperties(RateLimitProperties.class)
public class UrlShortnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortnerApplication.class, args);
	}

}
