package com.wisdomsky.dmp.auth.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wisdomsky.dmp.auth.common.SignUtil;
import com.wisdomsky.dmp.auth.common.randomToken;
import com.wisdomsky.dmp.auth.dao.db_tokens;
import com.wisdomsky.dmp.auth.pojo.CodeItem;
import com.wisdomsky.dmp.auth.pojo.UserForLoginCode;
import com.wisdomsky.dmp.auth.pojo.reqCheckCode;
import com.wisdomsky.dmp.auth.pojo.reqCheckSign;
import com.wisdomsky.dmp.auth.pojo.reqGetToken;
import com.wisdomsky.dmp.auth.pojo.respCheckCode;
import com.wisdomsky.dmp.auth.pojo.respCheckSign;
import com.wisdomsky.dmp.auth.pojo.respGetSmscode;
import com.wisdomsky.dmp.auth.pojo.respGetToken;
import com.wisdomsky.dmp.auth.pojo.respLoginCode;
import com.wisdomsky.dmp.auth.pojo.respT;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class tokenUtils {
	@Autowired 
	private db_tokens db_token ;
	
	
	public respLoginCode setLoginCode(String appid ,String username) {
		respLoginCode resp = new respLoginCode() ;
		try {
			if("ANTICOVID".equals(appid)) {
				String code = randomToken.getaToken(80) ;
				int ret = db_token.setNewLoginCode(appid, username, code) ;
				if(ret > 0) {
					resp.setErrcode(0);
					resp.setErrmsg("success");
					CodeItem codeItem = new CodeItem() ;
					codeItem.setLogincode(code);
					resp.setData(codeItem);				
				}				
			}
			else {
				resp.setErrcode(-99999);
				resp.setErrmsg("invalid appid");
			}
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("system error");
		}
		finally {
			return resp ;
		}
	}
	
	public respT checkLoginCode(String appid ,String code) {
		respT resp = new respT ();
		try {
			if("ANTICOVID".equals(appid)) {
				UserForLoginCode user = new UserForLoginCode();
				user.setUsername(db_token.getLoginCodeStatus(appid, code));
				resp.setData(user ) ;
				resp.setErrcode(0);
				resp.setErrmsg("success");
				
			}
			else {
				resp.setErrcode(-99999);
				resp.setErrmsg("invalid appid");
				
			}
		}catch(Exception e) {
			resp.setErrcode(-70003);
			resp.setErrmsg("system error");
		}
		finally {
			return resp ;			
		}
	}
	
	public boolean SingleClientAPP(String appid) {
		boolean ret = false ;
		if(appid.equals("HTMAPP")) {
			ret = true ;
		}
		return ret ;
	}
	public respT removeToken(String appid ,String userid ,String code) {
		respT resp = new respT ();
		try {
			String clientid = code ;
			if(SingleClientAPP(appid)) {
				clientid = "" ;
			}
			boolean ret = db_token.removeLoginToken(appid, userid, clientid);
			if(ret) {
				resp.setErrcode(0);
				resp.setErrmsg("success");				
			}
			else {
				resp.setErrcode(-70008);
				resp.setErrmsg("success");
			}			
		}catch(Exception e) {
			resp.setErrcode(-70003);
			resp.setErrmsg("system error");
		}
		finally {
			return resp ;			
		}
	}
	
	public respGetToken getToken(reqGetToken req,String appid,String userid) {
		respGetToken resp = new respGetToken() ;
		String token = "";
		String tcode = "" ;
		try {
			token = randomToken.getaToken(128);
			tcode = randomToken.getatcode(6);
			String clientid = tcode ;
			if(SingleClientAPP(appid)) {
				clientid = "" ;
			}
			int ret = db_token.setNewTokenForUser(appid,userid, token, clientid, tcode) ;
			if(ret>0) {
				resp.setErrcode(0);
				resp.setErrmsg("OK");
				resp.setUserId(req.getUserId());
				resp.setToken(token);
				resp.setTcode(tcode);
				resp.setExpireTime(86400);
			}
			else {
				resp.setErrcode(-70005);
				resp.setErrmsg("Set Token error");
				
			}
		}catch(Exception e) {
			resp.setErrcode(-70006);
			resp.setErrmsg("Set Token error!");
			
		}
		
		
		return resp ;
	}
	
	public boolean chkNewToken(boolean isDebug , String appid , long timestamp,String sign) {
		boolean ret = true ;
		String token = "" ;
		if(isDebug) {
			return ret;
		}
		ret = false;
		
		long timeout = 1800 ;
		
		try {
			ret  = db_token.setNewSigntoDB(appid, timestamp, sign,timeout);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return ret ;
		
	}

	public respCheckSign checkAdminToken(boolean isDebug  ,reqCheckSign req ,String userid,String tcode,String sign,String appid) {
		respCheckSign resp = new respCheckSign() ;
		String token = "" ;
		if(isDebug) {
			resp.setErrcode(0);
			return resp;
			
		}
		
		try {
			String clientid = tcode ;
			if(SingleClientAPP(appid)) {
				clientid = "" ;
			}
			token = db_token.getTokenForUserClient(appid,userid, clientid,tcode);
			if(token.equals("")) {
				resp.setErrcode(-70001);
				resp.setErrmsg("invalid sign");
			}
			else {
				boolean isSign = SignUtil.checkSignatureSample(sign, req.getBaseStr()+token) ;
				if(isSign) {
					resp.setErrcode(0);
					resp.setErrmsg("OK");
				}
				else {
					resp.setErrcode(-70001);
					resp.setErrmsg("invalid sign");
				}
			}
		}
		catch(Exception e) {
			resp.setErrcode(-70006);
			resp.setErrmsg("system error");
		}
		
		return resp ;
	}

	public respCheckSign checkToken(boolean isDebug ,reqCheckSign req ,String userid,String tcode,String sign,String appid) {
		respCheckSign resp = new respCheckSign() ;
		String token = "" ;
		if(isDebug) {
			resp.setErrcode(0);
			return resp;
			
		}
		
		try {
			String clientid = tcode ;
			if(SingleClientAPP(appid)) {
				clientid = "" ;
			}
			token = db_token.getTokenForUserClient(appid,userid , clientid, tcode);
			if(token.equals("")) {
				resp.setErrcode(-70011);
				resp.setErrmsg("not login or login is expired");
			}
			else {
				boolean isSign = SignUtil.checkSignatureSample(sign, req.getBaseStr()+token) ;
				if(isSign) {
					try {
						db_token.refreshUserToken(appid, userid, clientid);						
					}
					catch(Exception e) {
						log.error("[checkToken] REFRESHERROR : {}", e.toString());
					}
					resp.setErrcode(0);
					resp.setErrmsg("OK");
				}
				else {
					resp.setErrcode(-70001);
					resp.setErrmsg("invalid sign");
				}
			}
		}
		catch(Exception e) {
			resp.setErrcode(-70006);
			resp.setErrmsg("system error");
		}
		
		return resp ;
	}
	
	public respGetSmscode setSmsCode(String appid , String phone  ) {
		respGetSmscode resp = new respGetSmscode() ;
		int expire = 900 ;
		try {
			String smscode = randomToken.getasmscode(6);
			String salt = randomToken.getatcode(6);

			int ret = db_token.setNewSmsCode(appid,phone,salt ,smscode, expire) ;
			if(ret>0) {
				resp.setErrcode(ret);
				resp.setErrmsg("success");
				resp.setPhone(phone);
				resp.setSalt(salt);
				resp.setSmscode(smscode);
				resp.setExpireTime(expire);
				
			}
			else{
				resp.setErrcode(-70021);
				resp.setErrmsg("set smscode error");
			}
		}catch(Exception e) {
			resp.setErrcode(-70006);
			resp.setErrmsg("system error");			
		}
		
		return resp ;
	}
	public respT checkSmsCode(boolean isDebug , reqCheckSign req , String appid,String phone ,String salt ,String sign) {
		respT resp = new respT() ;
		String token = "" ;
		if(isDebug) {
			resp.setErrcode(0);
			return resp;
			
		}
		
		try {
			token = db_token.getSmsCode(appid, phone, salt);
			if(token == null || token.equals("")) {
				resp.setErrcode(-70022);
				resp.setErrmsg("smscode is not exist or expired");				
			}
			else {
				log.info("cur token is :{},{}",token , SignUtil.genSingatureSample(token).toLowerCase()) ;
				log.info("cur sign is :{},{}",sign );
				boolean isSign = SignUtil.checkSignatureSample(sign, req.getBaseStr()+SignUtil.genSingatureSample(token).toLowerCase()) ;
				if(isSign) {
					resp.setErrcode(0);
					resp.setErrmsg("OK");
					db_token.removeSmsCode(appid, phone, salt , randomToken.getatcode(8));
				}
				else {
					resp.setErrcode(-70001);
					resp.setErrmsg("invalid sign");
				}
			}
		}catch(Exception e) {
			resp.setErrcode(-70006);
			resp.setErrmsg("system error");
			
		}
		
		return resp ;
	}

	public respT getSmsCode(boolean isDebug , String appid , reqCheckCode req) {
		respT resp = new respT() ;
		String token = "" ;
		if(isDebug) {
			resp.setErrcode(0);
			return resp;
			
		}
		
		try {
			token = db_token.getSmsCode(appid, req.getPhone(), req.getSalt());
			if(token == null || token.equals("")) {
				resp.setErrcode(-70022);
				resp.setErrmsg("smscode is not exist or expired");				
			}
			else {
				log.info("cur token is :{},{}",token , SignUtil.genSingatureSample(token).toLowerCase()) ;
				respCheckCode data = new respCheckCode();
				data.setCode(SignUtil.genSingatureSample(token).toLowerCase());
				resp.setErrcode(0);
				resp.setErrmsg("OK");
				resp.setData(data);
			}
		}catch(Exception e) {
			resp.setErrcode(-70006);
			resp.setErrmsg("system error");
			
		}
		
		return resp ;
	}
	public respT removeSmsCode(boolean isDebug , String appid , reqCheckCode req) {
		respT resp = new respT() ;
		String token = "" ;
		if(isDebug) {
			resp.setErrcode(0);
			return resp;
			
		}
		
		try {
//			token = db_token.getSmsCode(appid, req.getPhone(), req.getSalt());
			db_token.removeSmsCode(appid, req.getPhone(), req.getSalt() , randomToken.getatcode(8));
			resp.setErrcode(0);
			resp.setErrmsg("success");
		}catch(Exception e) {
			resp.setErrcode(-70006);
			resp.setErrmsg("system error");
			
		}
		
		return resp ;
	}

}
