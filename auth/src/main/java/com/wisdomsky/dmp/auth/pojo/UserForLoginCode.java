package com.wisdomsky.dmp.auth.pojo;

import lombok.Data;

@Data
public class UserForLoginCode {
	private String username ;
	private int used ;
	private long settime ;

}
