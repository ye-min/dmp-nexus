package com.wisdomsky.dmp.archive.exception;

import java.lang.Exception;

public class SQLExecutionException extends RuntimeException {

   private static final long serialVersionUID = 1L;
   
   
   /** 
    * @return 
    */
   public SQLExecutionException() {
      super();
   }
   
   
   /** 
    * @param message
    * @return 
    */
   public SQLExecutionException(java.lang.String message) {
      super(message);
   }

   
   /** 
    * @param ex
    * @return 
    */
   public SQLExecutionException(Exception ex) {
      super(ex);
   }
}