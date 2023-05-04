package br.com.cci.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.cci.data.vo.v1.security.AccountCredentialsVO;
import br.com.cci.data.vo.v1.security.TokenVO;
import br.com.cci.repositories.UserRepository;
import br.com.cci.security.Jwt.JwtTokenProvider;

@Service
public class AuthServices {

	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	
	@Autowired
	private UserRepository repository;
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity signin(AccountCredentialsVO data) {
		try {
			var userName = data.getUsername();
			var password = data.getPassword();
			
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userName, password));
			System.out.println("huum");
			
			var user = repository.findByUserName(userName);
			
			var tokenResponse = new TokenVO();
			if (user != null) {
				tokenResponse = tokenProvider.createAcessToken(userName, user.getRoles());
			}else {
				throw new UsernameNotFoundException("Username " + userName + " not found!");
			}
			return ResponseEntity.ok(tokenResponse);
		}catch(Exception e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}
}
