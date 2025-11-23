package com.wisdomsky.dmp.archive.exception;

import java.lang.Exception;

public class InvalidTimeSignException extends Exception {

   private static final long serialVersionUID = 1L;
   
   
   /** 
    * @return 
    */
   public InvalidTimeSignException() {
      super();
   }
   
   
   /** 
    * @param message
    * @return 
    */
   public InvalidTimeSignException(java.lang.String message) {
      super(message);
   }

   
   /** 
    * @param ex
    * @return 
    */
   public InvalidTimeSignException(Exception ex) {
      super(ex);
   }
}