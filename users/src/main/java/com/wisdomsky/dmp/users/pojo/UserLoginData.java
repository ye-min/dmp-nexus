package com.wisdomsky.dmp.users.pojo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UserLoginData {
	private String clientID ;
	private String token ;
	private LoginUserInfo userInfo;
	private List<RoleItem> roles = new ArrayList<RoleItem>();
	private List<MenuItem> menus = new ArrayList<MenuItem>();

}
