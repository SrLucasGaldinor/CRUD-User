package com.knowledge.demo.config.security;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.knowledge.demo.entity.User;

@Service
public class TokenService {

	@Value("${api.security.token.secret}")
	private String secret;

	public String generateToken(User user) {
		try {

			Algorithm algorithm = Algorithm.HMAC256(secret);

			String token = JWT.create().withIssuer("crud-user").withSubject(user.getEmail())
					.withExpiresAt(Instant.now().plusSeconds(86400)).sign(algorithm);
			return token;
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Error while authenticating.");
		}
	}
	
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);

			return JWT.require(algorithm)
					  .withIssuer("crud-user")
					  .build()
					  .verify(token)
					  .getSubject();
			
		} catch (JWTVerificationException exception) {
			return null;
		}
	}

}
