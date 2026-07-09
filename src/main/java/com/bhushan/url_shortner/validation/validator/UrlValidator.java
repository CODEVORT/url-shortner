package com.bhushan.url_shortner.validation.validator;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bhushan.url_shortner.validation.annotation.ValidUrl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UrlValidator implements ConstraintValidator<ValidUrl, String>{
	private static final Logger log =
	        LoggerFactory.getLogger(UrlValidator.class);

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		long start = System.nanoTime();
		
		if(value == null || value.isBlank())
		{
			return true;
		}
		try
		{
			// To be remove in deployment
			System.out.println("Custome validator executed");
			URI uri = URI.create(value);
			String schema = uri.getScheme();
			String host = uri.getHost();
			
			return host != null 
					&& schema != null
					&& (schema.equalsIgnoreCase("http") 
							|| schema.equalsIgnoreCase("https") );
			
		}catch(Exception e)
		{
			return false;
		}finally {
			long elapsedMs =
	                (System.nanoTime() - start) / 1_000_000;

	            log.info(
	                "UrlValidator executed in {} ms",
	                elapsedMs
	            );
		}
		
	}
	
	

}
