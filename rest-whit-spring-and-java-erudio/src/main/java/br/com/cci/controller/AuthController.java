package br.com.cci.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cci.Service.AuthServices;
import br.com.cci.data.vo.v1.security.AccountCredentialsVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication Edpoint")
@RequestMapping("/auth")
@RestController
public class AuthController {
	
	@Autowired
	AuthServices authServices;

	@SuppressWarnings("rawtypes")
	@Operation(summary = "Authenticates a user and returns a token")
	@PostMapping(value = "/signin")
	public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {
		if (checkIfParamsIsNotNull(data))  {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		}
		
		var token = authServices.signin(data);
		
		if (token == null)  return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		return token;
	}

	
	
	private boolean checkIfParamsIsNotNull(AccountCredentialsVO data) {
		return data == null || data.getUsername() == null || 
				data.getUsername().isBlank()  || data.getPassword() == null || 
					data.getPassword().isBlank();
	}
	
	
	@SuppressWarnings("rawtypes")
	@Operation(summary = "Refresh token for authenticated user and return a token")
	@PutMapping(value = "/refresh/{username}")
	public ResponseEntity refreshToken(@PathVariable String userName, @RequestHeader("Authorization") String refreshToken) {
		if (checkIfParamsIsNotNull(userName, refreshToken))  {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		}
		
		var token = authServices.refreshToken(userName, refreshToken);
		
		if (token == null)  return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		return token;
	}



	private boolean checkIfParamsIsNotNull(String userName, String refreshToken) {
		return refreshToken == null || refreshToken.isBlank() || userName == null || userName.isBlank();
	}
	
	
}
