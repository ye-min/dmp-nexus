package com.wisdomsky.dmp.auth.pojo;

import lombok.Data;

@Data
public class respLoginCode {
	private int errcode ;
	private String errmsg ;
	private CodeItem data ;

}
