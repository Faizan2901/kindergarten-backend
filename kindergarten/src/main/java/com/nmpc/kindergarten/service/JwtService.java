package com.nmpc.kindergarten.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;
	
    private Key getSigningKey() {
    	return Keys.hmacShaKeyFor(secret.getBytes());
    }
    
    public String generateToken(String username) {
    	Map<String, Object> claims=new HashMap<>();
    	return createToken(claims, username);
    }
    
    private String createToken(Map<String, Object> claims,String subject) {
    	return Jwts.builder()
    			.setClaims(claims)
    			.setSubject(subject)
    			.setIssuedAt(new Date())
    			.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
    			.signWith(getSigningKey(),SignatureAlgorithm.HS256)
    			.compact();
    }
    
    public String extractUsername(String token) {
    	return extractClaim(token, Claims::getSubject);
    }
    
    public boolean isTokenValid(String token,String username) {
    	final String extractUsername =  extractUsername(token);
    	return extractUsername.equals(username) && !isTokenExpired(token);
    }
    
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder()
                                  .setSigningKey(getSigningKey())
                                  .build()
                                  .parseClaimsJws(token)
                                  .getBody();
        return claimsResolver.apply(claims);
    }
    
}
