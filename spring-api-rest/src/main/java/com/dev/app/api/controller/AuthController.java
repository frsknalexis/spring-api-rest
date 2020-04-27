package com.dev.app.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dev.app.api.model.User;
import com.dev.app.api.security.AccountCredentialsVO;
import com.dev.app.api.security.jwt.JwtTokenProvider;
import com.dev.app.api.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Authentication API", description = "Description for Authentication", tags = { "Authentication V1" })
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@ApiOperation(value = "Authentication for specific User with Credentials")
	@RequestMapping(value = "/signin", method = RequestMethod.POST, 
					consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml" },
					produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml" })
	public ResponseEntity<?> signin(@RequestBody AccountCredentialsVO credentials) {
		try {
			String username = credentials.getUsername();
			String password = credentials.getPassword();
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
			User usuario = userService.findByUsername(username);
			
			String token = "";
			
			if (usuario != null) {
				token = jwtTokenProvider.createToken(usuario.getUsername(), usuario.getRoles());
			} else {
				throw new UsernameNotFoundException("Username " + username + " not found");
			}
			
			Map<String, Object> model = new HashMap<>();
			model.put("username", username);
			model.put("token", token);
			return ResponseEntity.ok().body(model);
		} catch(AuthenticationException ex) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}
}