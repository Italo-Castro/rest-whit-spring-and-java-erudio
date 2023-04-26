package br.com.cci.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNullException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public RequiredObjectIsNullException(String ex) {
		super(ex);
	}
	
	public RequiredObjectIsNullException() {
		super("IT IS NOT ALLOWED TO PERSIST A NULL OBJECT");
	}
	
}
 