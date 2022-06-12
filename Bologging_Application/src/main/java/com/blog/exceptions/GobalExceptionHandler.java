package com.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.payloads.ApiResponce;

@RestControllerAdvice
public class GobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponce> resourceNotFoundHandler( ResourceNotFoundException exception){
		String string = exception.getMessage();
		ApiResponce apiResponce = new ApiResponce(string, false);
		return new ResponseEntity<>(apiResponce,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> MethodArgumentNotValidException(MethodArgumentNotValidException ex){
		Map<String , String> map = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error)->{
			
			String fieldNAme = ((FieldError)error).getField();
			String defaultMessage = error.getDefaultMessage();
			map.put(fieldNAme, defaultMessage);
		});
		
		return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
	}
}
