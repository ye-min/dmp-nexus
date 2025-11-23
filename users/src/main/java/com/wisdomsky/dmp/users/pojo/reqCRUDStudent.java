package com.wisdomsky.dmp.users.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class reqCRUDStudent extends reqBaseData{
	private int student_id ;
	private String username ;
	private String student_name ;
	private String student_code ;
	private int gender ;
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy/MM/dd")
	private Date birth ;
	private String phone ;
	private String contact ;
	private String e_mail ;
	private int state ;
	
	private int class_id ;
	@JsonIgnore
	private String passwd ;
	@JsonIgnore
	private String roles ;

}
