package com.wisdomsky.dmp.users.pojo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ClassItem {
	private int class_id ;
	private String class_code ;
	private String class_name ;
	private int teacher_id ;
	private int class_year ;
	private int state ;
	private Timestamp create_time ;
	
	private String teacher_name ;
	
	private int student_count ;
	
	@JsonIgnore
	private int total ;
}
