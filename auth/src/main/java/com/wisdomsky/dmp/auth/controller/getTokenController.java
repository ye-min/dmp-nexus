package com.wisdomsky.dmp.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wisdomsky.dmp.auth.pojo.reqGetToken;
import com.wisdomsky.dmp.auth.pojo.respGetToken;
import com.wisdomsky.dmp.auth.pojo.respT;
import com.wisdomsky.dmp.auth.utils.tokenUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/")
@Tag(name = "token操作")
@Slf4j
public class getTokenController {

	
	@Autowired
	private tokenUtils tu ;
	
	@Operation(summary = "获取一个新token")	
	@RequestMapping(value="/getToken",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respGetToken doGetToken(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") String userid ,
			@RequestHeader(value="timestamp") String timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody reqGetToken req

			)
	{
		log.info("[doGetToken] is {}.body is :{}","userid:"+ userid + ",sign:" +sign,req);
		respGetToken resp = new respGetToken();
		try {
			resp = tu.getToken(req,appid,userid);
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		return resp ;
		
	}
	@Operation(summary = "删除一个token")	
	@RequestMapping(value="/removeToken",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT doRemoveToken(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") String userid ,
			@RequestHeader(value="timestamp") String timestamp ,
			@RequestHeader(value="clientID") String clientid 
			)
	{
		log.info("[doRemoveToken] is {}.body is :{}","userid:"+userid + ",tcode:" +clientid);
		respT resp = new respT();
		try {
			resp = tu.removeToken(appid, userid, clientid);
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		return resp ;
		
	}

}
