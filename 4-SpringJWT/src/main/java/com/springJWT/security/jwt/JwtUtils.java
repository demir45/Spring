package com.springJWT.security.jwt;


import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.springJWT.service.KisiServiceImpl;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Component
public class JwtUtils {
	
	@Value("${springJWT.app.jwtExpirationMs}") 
	private int jwtExpirationMs;
	
	@Value("${springJWT.app.jwtSecret}") 
	private String jwtSecret;

	public String jwtOlustur(Authentication authentication) {
		
		KisiServiceImpl kisiBilgileri = (KisiServiceImpl) authentication.getPrincipal();
		
		return Jwts.builder()
				.setSubject(kisiBilgileri.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime()+jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}
	
	public String usernameAl(String token) {
		
		return Jwts.parser().setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody().getSubject();
	}
	
	public boolean JwtTokenGecerle(String authToken) {
		try {
			
			Jwts.parser().setSigningKey(jwtSecret)
						 .parseClaimsJws(authToken);
			return true;
		} catch (Exception e) {
			System.out.println("JWT Hatası : "+e.getMessage());
		}
		return false;
	}
	
}
