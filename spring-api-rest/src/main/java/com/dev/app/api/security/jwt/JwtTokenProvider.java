package com.dev.app.api.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.dev.app.api.controller.exception.InvalidJwtAuthenticationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenProvider {

	@Value("${security.jwt.token.secret-key:secret}")
	private String secretKey = "secret";
	
	@Value("${security.jwt.token.expire-length: 3600000}")
	private long validityInMilliSeconds = 3600000;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@PostConstruct
	public void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	public String createToken(String username, List<String> roles) {
		BiFunction<String, List<String>, String> biFunction = (u, r) -> {
			Claims claims = Jwts.claims().setSubject(u);
			claims.put("roles", r);
			
			Date now = new Date();
			Date validity = new Date(now.getTime() + validityInMilliSeconds);
			return Jwts.builder()
					.setClaims(claims)
					.setIssuedAt(now)
					.setExpiration(validity)
					.signWith(SignatureAlgorithm.HS256, secretKey)
					.compact();
		};
		return biFunction.apply(username, roles);
	}
	
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
	}
	
	private String getUsername(String token) {
		UnaryOperator<String> function = (s) -> {
			return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(s).getBody().getSubject();
		};
		return function.apply(token);
	}
	
	public String resolveToken(HttpServletRequest request) {
		Optional<String> bearerToken = Optional.ofNullable(request.getHeader("Authorization"));
		if (bearerToken.isPresent()) {
			if (bearerToken.get().startsWith("Bearer ")) {
				return bearerToken.get().substring(7, bearerToken.get().length());
			}
		}
		return bearerToken.orElse(null);
	}
	
	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			if (claims.getBody().getExpiration().before(new Date()))
				return false;
			return true;
		} catch(Exception e) {
			throw new InvalidJwtAuthenticationException("Expired or invalid token");
		}
	}
}