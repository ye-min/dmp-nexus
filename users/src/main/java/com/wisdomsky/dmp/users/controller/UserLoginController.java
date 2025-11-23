package com.wisdomsky.dmp.users.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSON;
import com.wisdomsky.dmp.users.dao.db_check;
import com.wisdomsky.dmp.users.pojo.reqBaseData;
import com.wisdomsky.dmp.users.pojo.reqUpdateUserPassword;
import com.wisdomsky.dmp.users.pojo.reqUserLogin;
import com.wisdomsky.dmp.users.pojo.respT;
import com.wisdomsky.dmp.users.utils.UserUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/users")
@Tag(name = "用户登录相关")
@CrossOrigin
@Slf4j

public class UserLoginController {
	@Autowired
	private UserUtils au;
	@Autowired
	private db_check cu ;
	@Operation(summary = "2.1.1	用户登录")	
	@RequestMapping(value="/login",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT doUserLogin(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") String userid ,
			@RequestHeader(value="timestamp") long timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody String post_str

			)
	{
		log.info("[doEmployeeLogin-recv] is {}.body is :{}","userid:"+ userid + ",sign:" +sign,post_str);
		respT resp = new respT();
		try {
			long ts = System.currentTimeMillis() ;
			boolean isNew = cu.setNewSigntoDB(appid, timestamp, sign, 7200) ;
			if(isNew) {
				reqUserLogin req = JSON.parseObject(post_str , reqUserLogin.class) ;
				Pattern pattern = Pattern.compile("[(\\pP|\\pS)]"); //Pattern.compile("[^a-zA-Z0-9]");
				Matcher matcher = pattern.matcher(req.getUsername()) ;
				if(matcher.find()) {
					resp.setErrcode(-100014);		
					resp.setErrmsg("该用户不存在");
				}
				else {
					if(req != null) {
						resp = au.loginByUserName(req, appid+userid+timestamp+clientid+post_str, sign , appid) ;				
					}
					else {
						resp.setErrcode(-70109);
						resp.setErrmsg("请求数据错误");
					}
					
				}				
			}
			else {
				resp.setErrcode(-70007);
				resp.setErrmsg("无效或重复的请求");
			}
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		finally {
			log.info("[doEmployeeLogin-send] is {}", JSON.toJSONString(resp));
			return resp ;			
		}
		
	}
	@Operation(summary = "2.1.4	修改密码")	
	@RequestMapping(value="/chgpasswd",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT doUserChgPasswd(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") int userid ,
			@RequestHeader(value="timestamp") long timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody reqUpdateUserPassword req

			)
	{
		log.info("[doEmployeeChgPasswd-recv] is {}.body is :{}","userid:"+ userid + ",sign:" +sign, JSON.toJSONString(req));
		respT resp = new respT();
		try {
			req.setUserid(userid);
			resp = au.chgUserPassword(userid, req);
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		finally {
			log.info("[doEmployeeChgPasswd-send] is {}", JSON.toJSONString(resp));
			return resp ;			
		}
		
	}
	@Operation(summary = "2.1.2	用户退出")	
	@RequestMapping(value="/logout",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT doUserLogout(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") int userid ,
			@RequestHeader(value="timestamp") long timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody reqBaseData req

			)
	{
		log.info("[doUserLogout-recv] is {}.body is :{}","userid:"+ userid + ",sign:" +sign, JSON.toJSONString(req));
		respT resp = new respT();
		try {
			req.setUserid(userid);
			resp = au.logout(appid,clientid,userid,timestamp) ;
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		finally {
			log.info("[doUserLogout-send] is {}", JSON.toJSONString(resp));
			return resp ;			
		}
		
	}
	@Operation(summary = "2.1.3	用户详情")	
	@RequestMapping(value="/myInfo",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT doUserMyInfo(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") int userid ,
			@RequestHeader(value="timestamp") long timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody reqBaseData req

			)
	{
		log.info("[doUserMyInfo-recv] is {}.body is :{}","userid:"+ userid + ",sign:" +sign, JSON.toJSONString(req));
		respT resp = new respT();
		try {
			req.setUserid(userid);
			resp = au.getMyUserInfo(req) ;
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		finally {
			log.info("[doUserMyInfo-send] is {}", JSON.toJSONString(resp));
			return resp ;			
		}
		
	}
	@Operation(summary = "2.1.5	初始化用户密码")	
	@RequestMapping(value="/resetUserPasswd",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT doUserResetPasswd(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") int userid ,
			@RequestHeader(value="timestamp") long timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody reqUpdateUserPassword req

			)
	{
		log.info("[doUserResetPasswd-recv] is {}.body is :{}","userid:"+ userid + ",sign:" +sign, JSON.toJSONString(req));
		respT resp = new respT();
		try {
			req.setUserid(userid);
			resp = au.resetUserPassword(userid, req);
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		finally {
			log.info("[doUserResetPasswd-send] is {}", JSON.toJSONString(resp));
			return resp ;			
		}
		
	}

}
