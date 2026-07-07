package com.bhushan.url_shortner.exceptions;

import java.util.List;

public record ApiErrorResponse(
			String code,
			String message,
			List<ValidationError> error
		) {

}
