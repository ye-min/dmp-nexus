package com.wisdomsky.dmp.archive.exception;

import java.lang.Exception;

public class InvalidParameterException extends RuntimeException {

   private static final long serialVersionUID = 1L;
   
   
   /** 
    * @return 
    */
   public InvalidParameterException() {
      super();
   }
   
   
   /** 
    * @param message
    * @return 
    */
   public InvalidParameterException(java.lang.String message) {
      super(message);
   }

   
   /** 
    * @param ex
    * @return 
    */
   public InvalidParameterException(Exception ex) {
      super(ex);
   }
}