package com.wisdomsky.dmp.users.pojo;

import lombok.Data;

@Data
public class respPageData<T> {
	private int total ;
	private int pageid ;
	private T pageData ;

}
