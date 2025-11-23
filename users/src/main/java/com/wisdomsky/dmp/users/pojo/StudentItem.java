package com.wisdomsky.dmp.users.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class StudentItem {
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
	private int isvalid ;
	private int state ;
	
	private int class_id ;
	private String class_name ;
	@JsonIgnore
	private int total ;
}
