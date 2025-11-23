package com.wisdomsky.dmp.users.feign.pojo;

import lombok.Data;

@Data
public class respGetToken {
	private int errcode ;
	private String errmsg ;
	private String userId ;
	private String tcode ;
	private String token ;
	private int expireTime ;

}
