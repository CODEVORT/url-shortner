package com.bhushan.url_shortner.exceptions;

public record ValidationError(
			String field,
			String message
		) {

}
