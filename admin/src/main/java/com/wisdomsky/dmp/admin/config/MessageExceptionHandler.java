package com.wisdomsky.dmp.admin.config;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wisdomsky.dmp.admin.exception.MessageException;
import com.wisdomsky.dmp.library.model.SimpleResponseTemplate;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestControllerAdvice
public class MessageExceptionHandler {
   @ExceptionHandler(MessageException.class)
   public ResponseEntity<SimpleResponseTemplate> handleMessageException(MessageException ex) {
      SimpleResponseTemplate response = new SimpleResponseTemplate();
      response.setCode(ex.getCode());
      response.setMessage(ex.getMessage());
      return ResponseEntity.status(HttpStatus.OK).body(response);
   }
}