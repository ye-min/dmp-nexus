package com.wisdomsky.dmp.admin.exception;

public class MessageException extends RuntimeException {

   private static final long serialVersionUID = 1L;
   private final int code;
   private final String message;

   public MessageException(int code, String message) {
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