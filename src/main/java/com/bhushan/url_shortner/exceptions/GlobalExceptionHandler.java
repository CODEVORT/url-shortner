package com.bhushan.url_shortner.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger log =
	        LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException e)
	{
		
		List<ValidationError> errors = e.getBindingResult()
											.getFieldErrors()
											.stream()
											.map(error -> new ValidationError(
														error.getField(),
														error.getDefaultMessage()
													))
											.collect(Collectors.toList());
		
		ApiErrorResponse response = new ApiErrorResponse(
					ErrorCode.VALIDATION_ERROR.name(),
					"Request validation Failed",
					errors
				);
		log.warn("Request validation failed");
		return ResponseEntity.badRequest().body(response);
		
		
	}
	
	@ExceptionHandler (UrlNotFoundException.class)
	public ResponseEntity<ApiErrorResponse > handleUrlNotFOundException(UrlNotFoundException e)
	{
		ApiErrorResponse response = new ApiErrorResponse(
					ErrorCode.URL_NOT_FOUND.name(),
					e.getMessage(),
					null
				);
		log.warn("Short URL not found: {}", e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	
	@ExceptionHandler(UrlAlredyExistsException.class)
	public ResponseEntity<ApiErrorResponse> handleUrlAlreadyExistException(UrlAlredyExistsException e)
	{
		ApiErrorResponse response = new ApiErrorResponse(
					ErrorCode.URL_ALREADY_EXISTS.name(),
					e.getMessage(),
					null
				);
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}
	
	@ExceptionHandler(ShortCodeGenerationException.class)
	public ResponseEntity<ApiErrorResponse> handleShortCodeGenerationFailure(
	        ShortCodeGenerationException e) {

	    ApiErrorResponse response =
	            new ApiErrorResponse(
	                    ErrorCode.SHORT_CODE_GENERATION_FAILED.name(),
	                    e.getMessage(),
	                    null
	            );

	    log.error("Short code generation failed", e);
	    return ResponseEntity
	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(response);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(ConstraintViolationException e)
	{
		ApiErrorResponse response = new ApiErrorResponse(
					ErrorCode.CONSTRAINT_VIOLATION_EXCEPTION.name(),
					e.getMessage(),
					null
				);
		log.error("Constraint violation exception");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	@ExceptionHandler(RateLimitExceededException.class)
	public ResponseEntity<ApiErrorResponse> handleRateLimitExceedsException(RateLimitExceededException e)
	{
		log.warn("Rate limit exceeded ");
		ApiErrorResponse response = new ApiErrorResponse(
					ErrorCode.RATE_LIMIT_EXCEEDED.name(),
					e.getMessage(),
					List.of()
				);
		
		
		
		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleUnexpectedException(
	        Exception ex) {

	    ApiErrorResponse response =
	            new ApiErrorResponse(
	                    ErrorCode.INTERNAL_SERVER_ERROR.name(),
	                    "An unexpected error occurred",
	                    null
	            );
	    log.error("Unexpected exception occurred", ex);

	    return ResponseEntity
	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(response);
	}
	
	
	
	
}
