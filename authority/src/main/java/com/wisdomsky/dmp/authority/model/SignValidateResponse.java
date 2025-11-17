package com.wisdomsky.dmp.authority.model;

public class SignValidateResponse {
	private int code;
	private String message;
	
   public SignValidateResponse() {};

   public SignValidateResponse(int code, String message) {
      this.code = code;
      this.message = message;
   }

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
