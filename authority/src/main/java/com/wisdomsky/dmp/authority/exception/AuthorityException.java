package com.wisdomsky.dmp.authority.exception;

public class AuthorityException extends RuntimeException {

   private static final long serialVersionUID = 1L;
   private final int code;
   private final String message;

   public AuthorityException(int code, String message) {
      super(message);
      this.code = code;
      this.message = message;
  }

  public int getCode() {
      return code;
  }

  @Override
  public String getMessage() {
      return message;
  }
}