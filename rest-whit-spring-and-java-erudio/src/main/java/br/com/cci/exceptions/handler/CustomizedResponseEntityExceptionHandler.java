package br.com.cci.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.cci.exceptions.ExceptionResponse;
import br.com.cci.exceptions.RequiredObjectIsNullException;
import br.com.cci.exceptions.ResourceNotFoundException;
import br.com.cci.exceptions.UnsupportedMathOperationException;

@RestController
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request){ 
		
		ExceptionResponse exReponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<>(exReponse, HttpStatus.INTERNAL_SERVER_ERROR);	
	}

	
	@ExceptionHandler(UnsupportedMathOperationException.class)
	public final ResponseEntity<ExceptionResponse> h(Exception ex, WebRequest request){ 
	
		ExceptionResponse exReponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
	
		return new ResponseEntity<>(exReponse, HttpStatus.BAD_REQUEST);	
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(Exception ex, WebRequest request){ 
	
		ExceptionResponse exReponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
	
		return new ResponseEntity<>(exReponse, HttpStatus.NOT_FOUND);	
	}
	
	@ExceptionHandler(RequiredObjectIsNullException.class)
	public final ResponseEntity<ExceptionResponse> handleBadRequestException(Exception ex, WebRequest request){ 
		
		ExceptionResponse exReponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<>(exReponse, HttpStatus.BAD_REQUEST);	
	}
	
	
	
	
	
}
