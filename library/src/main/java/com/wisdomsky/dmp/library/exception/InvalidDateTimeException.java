package com.wisdomsky.dmp.library.exception;

import java.lang.Exception;

public class InvalidDateTimeException extends RuntimeException {

   private static final long serialVersionUID = 1L;
   
   
   /** 
    * @return 
    */
   public InvalidDateTimeException() {
      super();
   }
   
   
   /** 
    * @param message
    * @return 
    */
   public InvalidDateTimeException(java.lang.String message) {
      super(message);
   }

   
   /** 
    * @param ex
    * @return 
    */
   public InvalidDateTimeException(Exception ex) {
      super(ex);
   }
}