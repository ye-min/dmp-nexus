package com.wisdomsky.dmp.users.pojo;

import lombok.Data;

@Data
public class reqCRUDClass extends reqBaseData {
	private int class_id ;
	private String class_code ;
	private String class_name ;
	private int teacher_id ;
	private int class_year ;
	private int state ;

}
