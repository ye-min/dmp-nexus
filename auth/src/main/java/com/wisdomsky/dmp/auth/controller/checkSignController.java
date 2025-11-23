package com.wisdomsky.dmp.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wisdomsky.dmp.auth.pojo.reqCheckSign;
import com.wisdomsky.dmp.auth.pojo.respCheckSign;
import com.wisdomsky.dmp.auth.utils.tokenUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/checkSign")
@Tag(name = "验证签名")
@Slf4j
public class checkSignController {
	@Autowired
	private tokenUtils tu ;
	
	@Value("${app.isdebug}")
	private boolean isDebug ;
	
	
	@Operation(summary  = "验证签名")	
	@RequestMapping(value="/newsignValid",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public ResponseEntity<respCheckSign> doNewCheckSign(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") String userid ,
			@RequestHeader(value="clientID") String clientID ,
			@RequestHeader(value="timestamp") String timestamp ,
			@RequestHeader(value="sign") String sign ,
			@RequestHeader(value="X-Forwarded-Uri") String uri ,
			@RequestBody String post_data
			)
	{
//		String timestamp = "1234";
//		isDebug = true ;
		
		reqCheckSign req = new reqCheckSign() ;
		 req.setApiName(uri);
		 req.setBaseStr(appid+userid+timestamp+clientID+post_data);
		log.info("[doCheckSign] is {}.apiName is :{}"+",sign:" +sign,req.getBaseStr() + ",appid:"+ appid+ ",ts:"+ timestamp,req.getApiName());

		respCheckSign resp = new respCheckSign();
		try {
			if(isDebug) {
				resp.setErrcode(0);
				resp.setErrmsg("IN DEBUG MODE");
			}
			else {
				boolean isNew = tu.chkNewToken(isDebug, userid , Long.parseLong(timestamp), sign) ;
				
				if(isNew) {
					resp = tu.checkToken(isDebug ,req, userid,clientID,sign,appid) ;
				}
				else {
					resp.setErrcode(-71001);
					resp.setErrmsg("Invalid timestamp");
				}
				
			}
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		finally {
			if(resp.getErrcode()>=0) {
				return ResponseEntity.ok().body(resp);
			}
			else {
				return ResponseEntity.status(401).body(resp);
			}
		}
		
	}
	@Operation(summary  = "验证签名是否正确")	
	@RequestMapping(value="/newsignValidForUpload",method=RequestMethod.POST ,produces="application/json;charset=UTF-8",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<respCheckSign> doNewCheckSignForUpload(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") String userid ,
			@RequestHeader(value="clientID") String clientID ,
			@RequestHeader(value="timestamp") String timestamp ,
			@RequestHeader(value="sign") String sign ,
			@RequestHeader(value="X-Forwarded-Uri") String uri ,
			@RequestParam( required=false)  String serialNo ,
			@RequestParam( required=false)  String size ,
			@RequestParam( required=false)  String sha1 ,
			@RequestParam( required=false) String type ,
			@RequestPart(required = true) MultipartFile files
			)
	{
//		String timestamp = "1234";
//		isDebug = true ;
		
		reqCheckSign req = new reqCheckSign() ;
		 req.setApiName(uri);
		 if(serialNo == null)
			 serialNo = "";
		 if(size == null)
			 size = "" ;
		 if(sha1 == null)
			 sha1 = "" ;
		 int start= uri.indexOf("file?") ;
		 start += 5;
		 String params[] = uri.substring(start).split("&") ;
		 for(int i=0;i<params.length ;i++) {
			 String kv[] = params[i].split("=") ;
			 switch(kv[0]) {
			 case "serialNo":
				 serialNo = kv[1] ;
				 break;
			 case "size":
				 size = kv[1] ;
				 break;
			 case "sha1":
				 sha1 = kv[1] ;
				 break;
			 case "type":
				 type = kv[1] ;
				 break;
			 }
		 }
		 req.setBaseStr(appid+userid+timestamp+clientID+serialNo+size+sha1);
		log.info("[doNewCheckSignForUpload] is {}.apiName is :{}"+",sign:" +sign,req.getBaseStr() + ",appid:"+ appid+ ",ts:"+ timestamp,req.getApiName());

		respCheckSign resp = new respCheckSign();
		try {
			if(isDebug) {
				resp.setErrcode(0);
				resp.setErrmsg("IN DEBUG MODE");
			}
			else {
				boolean isNew = tu.chkNewToken(isDebug, userid , Long.parseLong(timestamp), sign) ;
				
				if(isNew) {
					resp = tu.checkToken(isDebug ,req, userid,clientID,sign,appid) ;
				}
				else {
					resp.setErrcode(-71001);
					resp.setErrmsg("Invalid timestamp");
				}
				
			}
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		finally {
			if(resp.getErrcode()>=0) {
				return ResponseEntity.ok().body(resp);
			}
			else {
				return ResponseEntity.status(401).body(resp);
			}
		}
		
	}
	@Operation(summary  = "旧验证签名")
	@RequestMapping(value="/signValid",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respCheckSign doCheckSign(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") String userid ,
			@RequestHeader(value="clientID") String clientID ,
			@RequestHeader(value="timestamp") String timestamp ,
			@RequestHeader(value="sign") String sign ,
			@RequestHeader(value="X-Forwarded-Uri") String uri ,
			@RequestBody String post_data
			)
	{
//		String timestamp = "1234";
		
		reqCheckSign req = new reqCheckSign() ;
		 req.setApiName(uri);
		 req.setBaseStr(appid+userid+timestamp+clientID+post_data);
		log.info("[doCheckSign] is {}.apiName is :{}"+",sign:" +sign,req.getBaseStr() + ",appid:"+ appid+ ",ts:"+ timestamp,req.getApiName());

		respCheckSign resp = new respCheckSign();
		try {
			boolean isNew = tu.chkNewToken(isDebug, userid,Long.parseLong(timestamp), sign) ;
			
			if(isNew) {
				resp = tu.checkToken(isDebug ,req, userid,"",sign,appid) ;
			}
			else {
				resp.setErrcode(-71001);
				resp.setErrmsg("Invalid timestamp");
			}
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		finally {
			return resp ;			
		}
		
	}
	@Operation(summary  = "内置验证签名")
	@RequestMapping(value="/signValidForAdmin",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respCheckSign doCheckSignForAdmin(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") String userid ,
			@RequestHeader(value="clientID") String tcode ,
			@RequestHeader(value="timestamp") String timestamp ,
			@RequestHeader(value="sign") String sign ,
			@RequestBody reqCheckSign req

			)
	{
		if(req.getBaseStr().length()>1024) {
			log.info("[doCheckSignForAdmin] is {}.apiName is :{}"+",sign:" +sign,req.getBaseStr().substring(0,128) + ",appid:"+ appid+ ",ts:"+ timestamp,req.getApiName());
			
		}
		else {
			log.info("[doCheckSignForAdmin] is {}.apiName is :{}"+",sign:" +sign,req.getBaseStr() + ",appid:"+ appid+ ",ts:"+ timestamp,req.getApiName());
			
		}
		respCheckSign resp = new respCheckSign();
		try {
			if(isDebug) {
				resp.setErrcode(0);
				resp.setErrmsg("IN DEBUG MODE");
			}
			else {
				boolean isNew = tu.chkNewToken(isDebug, userid , Long.parseLong(timestamp), sign) ;
				
				if(isNew) {
					resp = tu.checkToken(isDebug ,req, userid,tcode,sign,appid) ;
				}
				else {
					resp.setErrcode(-71001);
					resp.setErrmsg("Invalid timestamp");
				}
				
			}
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			log.error("sign:{},{}",sign,e.getMessage());
			e.printStackTrace();
		}
		return resp ;
		
	}
	@Operation(summary  = "WS验证签名")
	@RequestMapping(value="/signValidForWS",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respCheckSign doCheckSignForWS(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") String userid ,
			@RequestHeader(value="clientID") String tcode ,
			@RequestHeader(value="timestamp") String timestamp ,
			@RequestHeader(value="sign") String sign ,
			@RequestBody reqCheckSign req

			)
	{
		log.info("[doCheckSignForAdmin] is {}.apiName is :{}"+",sign:" +sign,req.getBaseStr() + ",appid:"+ appid+ ",ts:"+ timestamp,req.getApiName());
		respCheckSign resp = new respCheckSign();
		try {
//			boolean isNew = tu.chkNewToken(timestamp, sign) ;
//			
//			if(isNew) {
				resp = tu.checkToken(isDebug , req, userid,tcode,sign,appid) ;
//			}
//			else {
//				resp.setErrcode(-71001);
//				resp.setErrmsg("Invalid timestamp");
//			}
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		return resp ;
		
	}

}
