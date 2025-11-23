package com.wisdomsky.dmp.users.pojo;

import lombok.Data;

@Data
public class reqUpdateUserPassword extends reqBaseData{
	private int uid ;
	private String newpasswd ;

}
