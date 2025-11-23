package com.wisdomsky.dmp.users.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wisdomsky.dmp.users.feign.pojo.reqGetToken;
import com.wisdomsky.dmp.users.feign.pojo.respGetToken;
import com.wisdomsky.dmp.users.pojo.respT;


@FeignClient(name="dmp-auth")
public interface authFeign {
	@RequestMapping(value="/getToken",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respGetToken doGetToken(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") String userid ,
			@RequestHeader(value="timestamp") String timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody reqGetToken req )  ;
	@RequestMapping(value="/removeToken",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT doRemoveToken(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") String userid ,
			@RequestHeader(value="timestamp") String timestamp ,
			@RequestHeader(value="clientID") String clientid 
			) ;

}
