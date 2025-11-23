package com.wisdomsky.dmp.auth.pojo;

import lombok.Data;

@Data
public class respGetSmscode {
	private int errcode ;
	private String errmsg ;
	private String phone ;
	private String salt ;
	private String smscode ;
	private int expireTime ;

}
