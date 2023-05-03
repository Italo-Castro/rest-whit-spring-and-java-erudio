package br.com.cci.security.Jwt;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.cci.data.vo.v1.security.TokenVO;
import br.com.cci.exceptions.InvalidJwtAuthenticationException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtTokenProvider {

	
	@Value("${security.jwt.token.secret-key:secret}")
	private String secretKey = "secret";
	
	@Value("${security.jwt.token.expire-length:3600000}")
	private long validityInMiliseconds = 3600000; //1HR
	
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	Algorithm algorithm = null;
	
	
	@PostConstruct ////instancia dos beans, processa as as anotations, injeta as dp, e depois os post contruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		algorithm = Algorithm.HMAC256(secretKey.getBytes());
	}
	
	public TokenVO createAcessToken(String userName, List<String> roles) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMiliseconds);
		
		var acessToken = getAcesToken(userName, roles, now, validity);
		var refreshToken = getRefreshToken(userName, roles, now);
		
		return new TokenVO(userName, true, now, validity, acessToken, refreshToken);
	}
	

	private String getAcesToken(String userName, List<String> roles, Date now, Date validity) {
		String issueUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		
		
		return JWT.create() 
				.withClaim("roles", roles)
				.withIssuedAt(now)
				.withExpiresAt(validity)
				.withSubject(userName)
				.withIssuer(issueUrl)
				.sign(algorithm)
				.strip();
	}
	
	private String getRefreshToken(String userName, List<String> roles, Date now) {
		var validityRefreshToken = new Date(now.getTime() + (validityInMiliseconds * 3));
		
		return JWT.create() 
				.withClaim("roles", roles)
				.withIssuedAt(now)
				.withExpiresAt(validityRefreshToken)
				.withSubject(userName)
				.sign(algorithm)
				.strip();
	}
	
	
	 
	public Authentication getAuthentication (String token) {
		DecodedJWT decodedJWT = decodedToken(token);
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(decodedJWT.getSubject());
		
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private DecodedJWT decodedToken(String token) {
		Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());
		JWTVerifier verifier = JWT.require(alg).build();
		DecodedJWT decodedJWT = verifier.verify(token);
		
		return decodedJWT;
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		
		if (bearerToken != null && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring("Bearer".length());
		}
		return null;
	}
	public boolean validateToken(String token) {
		DecodedJWT decodedJWT = decodedToken(token);
		
		try {
			if (decodedJWT.getExpiresAt().before(new Date())) {
				return false;
			}
			return true;
		}catch(Exception e) {
			throw new InvalidJwtAuthenticationException("Expired or invalid JWT token!");
		}
	}
}

	  