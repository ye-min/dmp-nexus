package com.wisdomsky.dmp.auth.pojo;

import lombok.Data;

@Data
public class reqCheckSign {
	private String baseStr ;
	private String apiName ;
	private String dataType ;
	private String dataValue ;
}
