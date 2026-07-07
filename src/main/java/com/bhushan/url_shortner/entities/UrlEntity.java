package com.bhushan.url_shortner.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
		name = "urls",
		schema = "url_shrt_schema",
		uniqueConstraints = {
				@UniqueConstraint(name = "uk_short_code" , columnNames = "short_code")
			}
		)

public class UrlEntity {
	


	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name = "url_id")
	private Long urlId;
	
	@Column(name = "original_url" ,  nullable = false , columnDefinition = "TEXT")
	private String originalUrl;
	
	@Column(name = "short_code" , nullable = false , length = 8)
	private String shortCode;
	
//	@Column(name = "url_hash", nullable = false, length = 64)
//	private String urlHash;
	
	@Column(
			name = "created_at",
			nullable = false,
			updatable = false
			)
	private LocalDateTime createdAt;
	
	public Long getUrlId() {
		return urlId;
	}

	public void setUrlId(Long urlId) {
		this.urlId = urlId;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	@PrePersist
	public void prePersist()
	{
		
		createdAt = LocalDateTime.now();
	}

//	public String getUrlHash() {
//		return urlHash;
//	}
//
//	public void setUrlHash(String urlHash) {
//		this.urlHash = urlHash;
//	}
}
