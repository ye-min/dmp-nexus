package com.wisdomsky.dmp.users.feign.pojo;

public class reqGetToken {
	private String userId ;
	private String tcode ;
	private int qtype ;
	
	public String getUserId() {
		return this.userId ;
	}
	public void setUserId(String userid) {
		this.userId = userid ;
	}
	public String getTcode() {
		return this.tcode ;
	}
	public void setTcode(String tcode) {
		this.tcode = tcode ;
	}
	
	public int getQtype() {
		return this.qtype ;
	}
	public void setQtype(int qtype) {
		this.qtype = qtype ;
	}

}
