package com.wisdomsky.dmp.archive.pojo.common;

import lombok.Data;

@Data
public class ResponseTemplate<T> {
	private int errcode ;
	private String errmsg ;
	private T data ;
}
