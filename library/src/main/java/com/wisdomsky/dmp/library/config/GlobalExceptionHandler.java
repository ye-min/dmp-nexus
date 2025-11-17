package com.wisdomsky.dmp.library.config;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wisdomsky.dmp.library.exception.InvalidArgumentException;
import com.wisdomsky.dmp.library.exception.SQLExecutionException;
import com.wisdomsky.dmp.library.model.SimpleResponseTemplate;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestControllerAdvice
public class GlobalExceptionHandler {

   private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

   @ExceptionHandler(InvalidArgumentException.class)
   public ResponseEntity<SimpleResponseTemplate> handleInvalidArgumentException(InvalidArgumentException ex) {
      logger.error("Invalid argument exception: {}", ex.getMessage());
      SimpleResponseTemplate response = new SimpleResponseTemplate();
      response.setCode(-10001);
      response.setMessage(ex.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
   }

   @ExceptionHandler(SQLExecutionException.class)
   public ResponseEntity<SimpleResponseTemplate> handleSQLExecutionExceptionException(SQLExecutionException ex) {
      logger.error("SQL execution exception: ", ex);
      SimpleResponseTemplate response = new SimpleResponseTemplate();
      response.setCode(-99009);
      // response.setMessage(ex.getMessage());
      response.setMessage("Unknown SQL Exception. Please contact the administrator.");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
   }

   @ExceptionHandler(UnrecognizedPropertyException.class)
   public ResponseEntity<SimpleResponseTemplate> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex) {
      logger.error("Unrecognized property exception: {}", ex.getMessage());
      SimpleResponseTemplate response = new SimpleResponseTemplate();
      response.setCode(-10004);
      // response.setMessage(ex.getMessage());
      response.setMessage("Request parameter exception, please check the request parameters.");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
   }

   @ExceptionHandler(Exception.class)
   public ResponseEntity<SimpleResponseTemplate> handleException(Exception ex) {
      logger.error("Unexpected exception: ", ex);
      SimpleResponseTemplate response = new SimpleResponseTemplate();
      response.setCode(-10003);
      // response.setMessage(ex.getMessage());
      response.setMessage("Unknown Exception. Please contact the administrator.");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
   }
}