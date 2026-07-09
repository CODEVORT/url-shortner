package com.bhushan.url_shortner.rate_limit;

import java.net.http.HttpRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {
		
	private final RateLimitService rateLimitService;
	
	@Override
	public boolean preHandle(
				HttpServletRequest request ,
				HttpServletResponse response ,
				Object handler
			)
	{
		
		String ipAddress = extractClientIp(request);
		rateLimitService.validateCreateUrlLimit(ipAddress);
		
		return true;
		
	}
	
	
	private String extractClientIp(HttpServletRequest request) {

        String xForwardedFor =
                request.getHeader("X-Forwarded-For");

        if (xForwardedFor != null &&
                !xForwardedFor.isBlank() &&
                !"unknown".equalsIgnoreCase(xForwardedFor)) {

            return xForwardedFor.split(",")[0].trim();
        }

        return request.getRemoteAddr();
    }
}
