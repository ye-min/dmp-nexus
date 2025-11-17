package com.wisdomsky.dmp.library.exception;

import java.lang.Exception;

public class InvalidArgumentException extends RuntimeException {

   private static final long serialVersionUID = 1L;
   
   
   /** 
    * @return 
    */
   public InvalidArgumentException() {
      super();
   }
   
   
   /** 
    * @param message
    * @return 
    */
   public InvalidArgumentException(java.lang.String message) {
      super(message);
   }

   
   /** 
    * @param ex
    * @return 
    */
   public InvalidArgumentException(Exception ex) {
      super(ex);
   }
}