package com.bhushan.url_shortner.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
		name = "urls",
		uniqueConstraints = {
				@UniqueConstraint(name = "uk_short_code" , columnNames = "short_code")
			}
		)

public class UrlEntity {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long urlId;
	
	@Column(name = "original_url" ,  nullable = false , columnDefinition = "TEXT")
	private String originalUrl;
	
	@Column(name = "short_code" , nullable = false , length = 8)
	private String shortCode;
	
	@Column(
			name = "created_at",
			nullable = false,
			updatable = false
			)
	private LocalDateTime createdAt;
	
	@PrePersist
	public void prePersist()
	{
		createdAt = LocalDateTime.now();
	}
}
