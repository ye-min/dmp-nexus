package com.wisdomsky.dmp.users.pojo;

import lombok.Data;

@Data
public class respT<T> {
	private int errcode ;
	private String errmsg ;
	private T data ;

}
