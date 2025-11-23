package com.wisdomsky.dmp.users.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class TeacherItem {
	private int teacher_id ;
	private String username ;
	private String teacher_name ;
	private String phone ;
	private String contact ;
	private String e_mail ;
	private int state ;
	private String teacher_intro ;
	@JsonIgnore
	private int total ;
}
