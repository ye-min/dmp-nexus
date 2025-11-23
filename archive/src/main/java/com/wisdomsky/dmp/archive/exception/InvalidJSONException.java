package com.wisdomsky.dmp.archive.exception;

import java.lang.Exception;

public class InvalidJSONException extends RuntimeException {

   private static final long serialVersionUID = 1L;
   
   
   /** 
    * @return 
    */
   public InvalidJSONException() {
      super();
      // setLevel(7);
   }
   
   
   /** 
    * @param message
    * @return 
    */
   public InvalidJSONException(java.lang.String message) {
      super(message);
      // setLevel(7);
   }

   
   /** 
    * @param ex
    * @return 
    */
   public InvalidJSONException(Exception ex) {
      super(ex);
      // setLevel(7);
   }

   public InvalidJSONException(java.lang.String message, Exception ex) {
      super(message, ex);
      // setLevel(7);
   }
}