package com.wisdomsky.dmp.archive.exception;

import java.lang.Exception;

public class RedisNotImplementException extends Exception {

   private static final long serialVersionUID = 1L;
   
   
   /** 
    * @return 
    */
   public RedisNotImplementException() {
      super();
   }
   
   
   /** 
    * @param message
    * @return 
    */
   public RedisNotImplementException(java.lang.String message) {
      super(message);
   }

   
   /** 
    * @param ex
    * @return 
    */
   public RedisNotImplementException(Exception ex) {
      super(ex);
   }
}