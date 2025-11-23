package com.wisdomsky.dmp.archive.exception;

import java.lang.Exception;

public class InvalidDataTypeException extends RuntimeException {

   private static final long serialVersionUID = 1L;
   
   
   /** 
    * @return 
    */
   public InvalidDataTypeException() {
      super();
   }
   
   
   /** 
    * @param message
    * @return 
    */
   public InvalidDataTypeException(java.lang.String message) {
      super(message);
   }

   
   /** 
    * @param ex
    * @return 
    */
   public InvalidDataTypeException(Exception ex) {
      super(ex);
   }
}