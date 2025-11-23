package com.wisdomsky.dmp.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wisdomsky.dmp.auth.pojo.SmsCodeItem;
import com.wisdomsky.dmp.auth.pojo.reqCheckCode;
import com.wisdomsky.dmp.auth.pojo.reqCheckSign;
import com.wisdomsky.dmp.auth.pojo.respGetSmscode;
import com.wisdomsky.dmp.auth.pojo.respT;
import com.wisdomsky.dmp.auth.utils.tokenUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/smscode")
@Tag(name = "smscode操作")
@Slf4j
public class smsCodeTokenController {
	@Autowired
	private tokenUtils tu ;

	@Operation(summary = "获取一个新smscode")	

	@RequestMapping(value="/setnew",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respGetSmscode doSetSmsCode(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") String phone ,
			@RequestHeader(value="timestamp") String timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody SmsCodeItem req

			)
	{
		log.info("[doSetSmsCode-recv] is {}.body is :{}","userid:"+ phone + ",sign:" +sign,req.toString());
		respGetSmscode resp = new respGetSmscode();
		try {
			resp = tu.setSmsCode(appid, phone);
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		log.info("[doSetSmsCode-send] {}",resp.toString());
		return resp ;
		
	}
	@Operation(summary = "验证smscode")	
	@RequestMapping(value="/check",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT doChkSmsCode(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") String userid ,
			@RequestHeader(value="timestamp") String timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody reqCheckSign req

			)
	{
		log.info("[doChkSmsCode-recv] is {}.body is :{}","userid:"+ userid + ",sign:" +sign,req.toString());
		respT resp = new respT();
		try {
			resp = tu.checkSmsCode(false, req, appid, userid, clientid, sign) ;
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		log.info("[doSetSmsCode-send] {}",resp.toString());
		return resp ;
		
	}
	@Operation(summary = "获取smscode")	
	@RequestMapping(value="/getone",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT doGetSmsCode(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") String userid ,
			@RequestHeader(value="timestamp") String timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody reqCheckCode req

			)
	{
		log.info("[doGetSmsCode-recv] is {}.body is :{}","userid:"+ userid + ",sign:" +sign,req.toString());
		respT resp = new respT();
		try {
			resp = tu.getSmsCode(false, appid, req) ;
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		log.info("[doGetSmsCode-send] {}",resp.toString());
		return resp ;
		
	}
	@Operation(summary = "删除smscode")	
	@RequestMapping(value="/delone",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT doDelSmsCode(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") String userid ,
			@RequestHeader(value="timestamp") String timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody reqCheckCode req

			)
	{
		log.info("[doDelSmsCode-recv] is {}.body is :{}","userid:"+ userid + ",sign:" +sign,req.toString());
		respT resp = new respT();
		try {
			resp = tu.removeSmsCode(false, appid, req) ;
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		log.info("[doDelSmsCode-send] {}",resp.toString());
		return resp ;
		
	}

}
