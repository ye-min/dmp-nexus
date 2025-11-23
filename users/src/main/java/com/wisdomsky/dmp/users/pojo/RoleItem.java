package com.wisdomsky.dmp.users.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class RoleItem {
	private int rid ;
	private String rtitle ;
	@JsonIgnore
	private String rdesc ;

}
