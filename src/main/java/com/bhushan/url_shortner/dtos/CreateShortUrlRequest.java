package com.bhushan.url_shortner.dtos;

import com.bhushan.url_shortner.validation.annotation.ValidUrl;

import jakarta.validation.constraints.NotBlank;

public record CreateShortUrlRequest(
			@NotBlank(message =  "original url is required")
			@ValidUrl
			String original_url
		) {

}
