package br.com.cci.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.cci.exceptions.UnsupportedMathOperationException;

@RestController
public class MathController {
	
	
	 private static final AtomicLong counter = new AtomicLong();
	 
	 
	 @RequestMapping(value = "/sum/{numberOne}/{numbeTwo}",method=RequestMethod.GET)
	 public Double sum (
			 @PathVariable(value = "numberOne") String numberOne, 
			 @PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		 
		 
		 if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			 throw new UnsupportedMathOperationException("Please set a numeric value");
		 }
		 
		return convertToDouble(numberOne) + convertToDouble(numberTwo);
	 }


	private Double convertToDouble(String strNumber) {
		if(strNumber == null) return 0D; 
		String number = strNumber.replace(",", ".");
		
		if (isNumeric(number)) {
			return Double.parseDouble(number);
		}
		
		return null;
	}


	private boolean isNumeric(String strNumber) {
		if(strNumber == null) return false;
		String number = strNumber.replace(",", ".");
		return number.matches("[-+]?[0-0]*\\.?[0-9]+"); 
	}
	
	
}	
