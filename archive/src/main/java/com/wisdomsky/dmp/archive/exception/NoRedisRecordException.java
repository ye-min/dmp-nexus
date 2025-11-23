package com.wisdomsky.dmp.archive.exception;

import java.lang.Exception;

public class NoRedisRecordException extends Exception {

   private static final long serialVersionUID = 1L;
   
   
   /** 
    * @return 
    */
   public NoRedisRecordException() {
      super();
   }
   
   
   /** 
    * @param message
    * @return 
    */
   public NoRedisRecordException(java.lang.String message) {
      super(message);
   }

   
   /** 
    * @param ex
    * @return 
    */
   public NoRedisRecordException(Exception ex) {
      super(ex);
   }
}