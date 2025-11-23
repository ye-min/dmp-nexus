package com.wisdomsky.dmp.users.utils;

import java.util.List;

import org.springframework.stereotype.Component;

import com.wisdomsky.dmp.users.pojo.RoleItem;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommonUtils {
	public String parseList2RoleStr(List<RoleItem> list) {
		String rets = "" ;
		
		try {
			if(list != null && list.size()>0) {
				int flag = 0 ;
				for(int i=0;i<list.size() ;i++) {
					if(flag++ >0) {
						rets += "," ;
					}
					rets += list.get(i).getRid() ;
					
				}
			}
		}catch(Exception e) {
			
		}
		
		return rets ;
	}


	public boolean BlankString(String str) {
		boolean ret = false ;
		if(str ==  null ) {
			ret = true ;
		}
		else {
			if(str.trim().equals("")) {
				ret = true ;
			}
		}
		return ret ;
	}

}
