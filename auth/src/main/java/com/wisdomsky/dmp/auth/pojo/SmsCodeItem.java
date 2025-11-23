package com.wisdomsky.dmp.auth.pojo;

import lombok.Data;

@Data
public class SmsCodeItem {
	private String phone ;
	private String salt ;
	private String smscode ;
	private long expired ;

}
