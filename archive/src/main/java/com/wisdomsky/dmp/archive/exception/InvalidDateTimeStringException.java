package com.wisdomsky.dmp.archive.exception;

import java.lang.Exception;

public class InvalidDateTimeStringException extends Exception {

   private static final long serialVersionUID = 1L;
   
   
   /** 
    * @return 
    */
   public InvalidDateTimeStringException() {
      super();
   }
   
   
   /** 
    * @param message
    * @return 
    */
   public InvalidDateTimeStringException(java.lang.String message) {
      super(message);
   }

   
   /** 
    * @param ex
    * @return 
    */
   public InvalidDateTimeStringException(Exception ex) {
      super(ex);
   }
}