package br.com.cci.data.vo.v1.security;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class AccountCredentialsVO implements Serializable{

	private static final long serialVersionUID = 1L;

	
	private String userName;
	private Boolean authenticated;
	private Date created;
	private Date expiration;
	private String accessToken;
	private String regreshToken;
	
	public AccountCredentialsVO() {}
	
	public AccountCredentialsVO(String userName, Boolean authenticated, Date created, Date expiration,
			String accessToken, String regreshToken) {
		this.userName = userName;
		this.authenticated = authenticated;
		this.created = created;
		this.expiration = expiration;
		this.accessToken = accessToken;
		this.regreshToken = regreshToken;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(Boolean authenticated) {
		this.authenticated = authenticated;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRegreshToken() {
		return regreshToken;
	}

	public void setRegreshToken(String regreshToken) {
		this.regreshToken = regreshToken;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accessToken, authenticated, created, expiration, regreshToken, userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountCredentialsVO other = (AccountCredentialsVO) obj;
		return Objects.equals(accessToken, other.accessToken) && Objects.equals(authenticated, other.authenticated)
				&& Objects.equals(created, other.created) && Objects.equals(expiration, other.expiration)
				&& Objects.equals(regreshToken, other.regreshToken) && Objects.equals(userName, other.userName);
	}
	
	
	
	
	
	
	
}
