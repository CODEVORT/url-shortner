package com.bhushan.url_shortner.repositoires;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bhushan.url_shortner.entities.UrlEntity;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
	
	Optional<UrlEntity> findByShortCode(String shortCode);
	
	Optional<UrlEntity> findByOriginalUrl(String originalUrl);
	
	Boolean existsByShortCode(String shortCode);

}
