package com.wisdomsky.dmp.security.config;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wisdomsky.dmp.library.model.SimpleResponseTemplate;
import com.wisdomsky.dmp.security.exception.SecurityException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestControllerAdvice
public class SecurityExceptionHandler {
   @ExceptionHandler(SecurityException.class)
   public ResponseEntity<SimpleResponseTemplate> handleSecurityException(SecurityException ex) {
      SimpleResponseTemplate response = new SimpleResponseTemplate();
      response.setCode(ex.getCode());
      response.setMessage(ex.getMessage());
      return ResponseEntity.status(HttpStatus.OK).body(response);
   }
}