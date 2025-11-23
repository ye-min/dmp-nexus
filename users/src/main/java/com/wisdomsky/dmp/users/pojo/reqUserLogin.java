package com.wisdomsky.dmp.users.pojo;

import lombok.Data;

@Data
public class reqUserLogin extends reqBaseData {
	private int type =1;
	private String username ;
	private int usertype =0;
}
