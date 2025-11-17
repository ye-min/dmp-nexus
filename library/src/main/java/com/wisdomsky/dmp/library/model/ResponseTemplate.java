package com.wisdomsky.dmp.library.model;

public class ResponseTemplate<T> {
	private long code ;
	private String message ;
	private T data ;

	public long getCode() {
		return code;
  }

  public void setCode(long code) {
		this.code = code;
  }

  public String getMessage() {
		return message;
  }

  public void setMessage(String message) {
		this.message = message;
  }

  public T getData() {
		return data;
  }

  public void setData(T data) {
		this.data = data;
  }
}