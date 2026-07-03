package com.bhushan.url_shortner.exceptions;

public class UrlAlredyExistsException extends RuntimeException {
	
	public UrlAlredyExistsException(String message)
	{
		super(message);
	}
	
	public UrlAlredyExistsException(String message , Throwable cause)
	{
		super(message , cause);
	}
	
}
