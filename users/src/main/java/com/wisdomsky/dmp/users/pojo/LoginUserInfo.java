package com.wisdomsky.dmp.users.pojo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class LoginUserInfo {
	private int userID ;
	private String username ;
	private String realname ;
	private int usertype ;
	private Timestamp login_time ;
	@JsonIgnore
	private String passwd ;
	@JsonIgnore
	private String roles_str ;
	@JsonIgnore
	private int isvalid ;
}
