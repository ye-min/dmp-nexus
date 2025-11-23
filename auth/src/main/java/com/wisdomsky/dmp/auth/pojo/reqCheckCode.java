package com.wisdomsky.dmp.auth.pojo;

import lombok.Data;

@Data
public class reqCheckCode {
	private String phone ;
	private String salt ;
	private String code ;

}
