package com.wisdomsky.dmp.archive.exception;

import java.lang.Exception;

public class NoRedisFieldException extends Exception {

   private static final long serialVersionUID = 1L;
   
   
   /** 
    * @return 
    */
   public NoRedisFieldException() {
      super();
   }
   
   
   /** 
    * @param message
    * @return 
    */
   public NoRedisFieldException(java.lang.String message) {
      super(message);
   }

   
   /** 
    * @param ex
    * @return 
    */
   public NoRedisFieldException(Exception ex) {
      super(ex);
   }
}