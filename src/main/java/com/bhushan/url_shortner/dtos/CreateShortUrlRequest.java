package com.bhushan.url_shortner.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateShortUrlRequest(
			@NotBlank(message =  "original url is required")
			String original_url
		) {

}
