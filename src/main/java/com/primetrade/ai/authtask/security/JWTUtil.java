package com.primetrade.ai.authtask.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.expiration}")
	private long jwtExpirationMs;

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}

	public String generateToken(String username) {

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

		return Jwts.builder().setSubject(username) // email
				.setIssuedAt(now).setExpiration(expiryDate).signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public String getUsernameFromToken(String token) {
		return getClaims(token).getSubject();
	}

	public Date getExpirationFromToken(String token) {
		return getClaims(token).getExpiration();
	}

	public boolean validateToken(String token) {
		try {
			getClaims(token);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	private Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}
}
