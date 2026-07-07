package com.bhushan.url_shortner.validation.validator;

import java.net.URI;

import com.bhushan.url_shortner.validation.annotation.ValidUrl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UrlValidator implements ConstraintValidator<ValidUrl, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(value == null || value.isBlank())
		{
			return true;
		}
		try
		{
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
		}
		
	}
	
	

}
