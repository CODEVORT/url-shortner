package com.bhushan.url_shortner.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bhushan.url_shortner.validation.validator.UrlValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = UrlValidator.class)
@Target({
	ElementType.FIELD ,
	ElementType.PARAMETER ,
	ElementType.RECORD_COMPONENT
})
@Retention(
		RetentionPolicy.RUNTIME
		)
public @interface ValidUrl {
	String message() default "Invalid URL format";
	
	Class<?>[] groups() default {};
	
	Class < ? extends Payload> [] payload() default {};
	
}
