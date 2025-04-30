package com.nmpc.kindergarten.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BudgetExceededException.class)
	public ResponseEntity<String> handleBudgetExceeded(BudgetExceededException budgetExceededException){
		return ResponseEntity
				.badRequest()
				.body(budgetExceededException.getMessage());
	}
	
	@ExceptionHandler(UserAlreadyPresent.class)
	public ResponseEntity<String> userAlreadyPresent(UserAlreadyPresent userAlreadyPresent){
		return ResponseEntity
				.badRequest()
				.body(userAlreadyPresent.getMessage());
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<Map<String, String>> userNotFound(UsernameNotFoundException ex) {
	    Map<String, String> error = new HashMap<>();
	    error.put("message", ex.getMessage());
	    return ResponseEntity.badRequest().body(error);
	}

	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<Map<String, String>> invalidCredentials(InvalidCredentialsException ex) {
	    Map<String, String> error = new HashMap<>();
	    error.put("message", ex.getMessage());
	    return ResponseEntity.badRequest().body(error);
	}

}
