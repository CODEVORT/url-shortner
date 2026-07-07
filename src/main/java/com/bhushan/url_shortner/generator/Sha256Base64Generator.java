package com.bhushan.url_shortner.generator;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;
import com.bhushan.url_shortner.exceptions.ShortCodeGenerationException;
import com.bhushan.url_shortner.repositoires.UrlRepository;

import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class Sha256Base64Generator implements ShortCodeGenerator{



	private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	private static final int SHORT_CODE_LENGTH = 8;
	
	private static final int MAX_TRIES = 10 ;
	
	private final UrlRepository urlRepository;


	@Override
	public String generateUniqueCode(String originalUrl) {
		for(int attempts = 0; attempts < MAX_TRIES ; attempts++)
		{
			String candidate = generateCode(originalUrl , attempts);
			
			if(!urlRepository.existsByShortCode(candidate))
			{
				return candidate;
			}
		}
		throw new ShortCodeGenerationException(
                "Unable to generate unique shortcode."
        );
	}
	
	
	private String generateCode(String url , int attempts)
	{
		String value = attempts == 0 ?  url : url+ "#" + attempts;
		byte[] hash = sha256(value);
		return toBase62(hash)
                .substring(0, SHORT_CODE_LENGTH);
	}
	
	
	private byte[] sha256(String value)
	{
		try
		{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			
			return digest.digest(value.getBytes(StandardCharsets.UTF_8));
		}
		catch (NoSuchAlgorithmException e) {
			throw new ShortCodeGenerationException(
		            "SHA-256 algorithm unavailable",
		            e
		    );
		}
	}
	
	
	private String toBase62(byte[] bytes)
	{
		BigInteger number = new BigInteger(1, bytes);
		
		StringBuilder result = new StringBuilder();
		
		while(number.compareTo(BigInteger.ZERO) > 0)
		{
			BigInteger [] divRem = 
					number.divideAndRemainder(
							BigInteger.valueOf(62)
							);
			result.append(
					BASE62.charAt(divRem[1].intValue())
					);
			number = divRem[0];
		}
		
		return result.reverse().toString();
	}

}
