package com.nmpc.kindergarten.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Configuration
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
public class ApplicationProperties {
	
	@Value("${jwt.secret}")
	private String jwtKey;
	
	@Value("${jwt.expiration}")
	private Long jwtExpiration;

	public String getJwtKey() {
		return jwtKey;
	}

	public void setJwtKey(String jwtKey) {
		this.jwtKey = jwtKey;
	}

	public Long getJwtExpiration() {
		return jwtExpiration;
	}

	public void setJwtExpiration(Long jwtExpiration) {
		this.jwtExpiration = jwtExpiration;
	}
	
	

}
