package com.example.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.enums.UserRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtils {
	public String extractUsername(String token) {
		return extractClaim(token,Claims::getSubject);
	}
	
	public  <T> T extractClaim(String token,Function<Claims,T> claimsResolver){
		final Claims claims=extractALLClaims(token);
		return claimsResolver.apply(claims);
	}
	public String generateToken(UserDetails userdetail,UserRole userRole) {
		return generateToken(new HashMap<>(),userdetail,userRole);
	}
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, UserRole userRole) {
	    Map<String, Object> claims = new HashMap<>(extraClaims);
	    claims.put("UserRole", userRole);

	    return Jwts.builder()
	            .setClaims(claims)
	            .setSubject(userDetails.getUsername())
	            .setIssuedAt(new Date(System.currentTimeMillis()))
	            .setExpiration(new Date(System.currentTimeMillis() + 100000* 60 * 24))
	            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
	            .compact();
	}

	public boolean isTokenValid(String token,UserDetails userdetails) {
		final String userName=extractUsername(token);
		return (userName.equals(userdetails.getUsername()))&& !isTokenExpired(token);
	}
	public boolean isTokenExpired(String token) {
		return extractexpiration(token).before(new Date());
	}
	private Date extractexpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	private Claims extractALLClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build()
				.parseClaimsJws(token)
				.getBody();
	}
	private Key getSignInKey() {
		byte[] keyBytes=Decoders.BASE64.decode("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		return Keys.hmacShaKeyFor(keyBytes); 
	}
}
