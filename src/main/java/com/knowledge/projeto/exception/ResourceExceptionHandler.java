package com.knowledge.projeto.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<StandardError> businessError(BusinessException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(),
											  status.value(), 
											  "Violação de regra de negócio",
											  e.getMessage(),
											  request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFoundError(ResourceNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(),
											  status.value(),
											  "Recurso não encontrado",
											  e.getMessage(),
											  request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<StandardError> invalidPasswordError(InvalidPasswordException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(),
											   status.value(),
											   "Invalid password!",
											   e.getMessage(),
											   request.getRequestURI());
		return ResponseEntity.status(status).body(err);	
	}
}
