package com.wisdomsky.dmp.authority.config;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wisdomsky.dmp.authority.exception.AuthorityException;
import com.wisdomsky.dmp.library.model.SimpleResponseTemplate;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestControllerAdvice
@Order(1)
public class AuthorityExceptionHandler {
   @ExceptionHandler(AuthorityException.class)
   public ResponseEntity<SimpleResponseTemplate> handleAuthorityException(AuthorityException ex) {
      SimpleResponseTemplate response = new SimpleResponseTemplate();
      response.setCode(ex.getCode());
      response.setMessage(ex.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
   }

   @ExceptionHandler(Exception.class)
   public ResponseEntity<SimpleResponseTemplate> handleException(Exception ex) {
      SimpleResponseTemplate response = new SimpleResponseTemplate();
      response.setCode(-10000);
      response.setMessage(ex.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
   }
}