package com.wisdomsky.dmp.users.pojo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class MenuItem {
	private int mid ;
	private String mtitle ;
	private String mdesc ;
	private String murl ;
	private String mpath ;
	private int mrole ;
	private int mtype ;
	private String micon ;
	private List<MenuItem> subList = new ArrayList<MenuItem>();

	@JsonIgnore
	private int parent_id ;
	private int mdispid ;

}
