package com.bhushan.url_shortner.exceptions;

import java.security.MessageDigest;

public class DuplicateUrlException extends RuntimeException {
	
	public DuplicateUrlException(String message) {
		super(message);
	}
	
	
	public DuplicateUrlException(String message , Throwable cause)
	{
		super(message , cause);
	}
	
}
