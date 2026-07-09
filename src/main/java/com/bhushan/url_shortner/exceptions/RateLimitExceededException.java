package com.bhushan.url_shortner.exceptions;

public class RateLimitExceededException  extends RuntimeException{
	
	public RateLimitExceededException(String message)
	{
		super(message);
	}

}
