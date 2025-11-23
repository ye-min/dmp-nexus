package com.wisdomsky.dmp.auth.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class db_tokens {
	@Autowired 
	private StringRedisTemplate jedis;
	
	public int setNewLoginCode(String appid , String username ,String code) {
		int ret = -1 ;
		try {
//			Map<Object,Object> tmap = new HashMap<Object,Object>();
//			tmap.put("uname", username);
//			tmap.put("used", 1);
//			tmap.put("settime", String.valueOf(System.currentTimeMillis())) ;
			String key = "LOGINCODE:APPID:"+ appid + ":"+ code;
			boolean b = jedis.opsForValue().setIfAbsent(key, username, 3*60, TimeUnit.SECONDS);
			if(b)
				ret = 1;
//			jedis.opsForHash().putAll(key, tmap);
//			jedis.expire(key, 3* 3600,TimeUnit.SECONDS) ;
		}catch(Exception e) {
			log.error("[setNewLoginCode] is exception :{},{}",appid+":"+ username ,code);
			e.printStackTrace();
		}
		return ret ;
	
	}
	
	public String getLoginCodeStatus(String appid, String code) {
		String rets =null;
		try {
			String key = "LOGINCODE:APPID:"+ appid + ":"+ code;
			rets = jedis.opsForValue().getAndSet(key, "expired");
			jedis.expire(key, 1,TimeUnit.SECONDS);
		}
		catch(Exception e) {
			log.error("[getLoginCodeStatus] is exception :{},{}",appid ,code);
			e.printStackTrace();		
		}
		finally {
			return rets ;
		}
	}
	
	public int setNewSmsCode(String appid , String phone , String salt , String code, int expire) {
		int ret = -1 ;
		String key = "";
		try {
			key = "SMSCODE:APPID:" + appid + ":" + phone + ":" + salt  ;
			boolean b = jedis.opsForValue().setIfAbsent(key, code, expire, TimeUnit.SECONDS) ;
			if(b)
				ret = 1 ;
		}catch(Exception e) {
			log.error("setNewSmsCode is exception :{},{}",key , e.toString());
		}
		return ret ;
	}
	
	public String getSmsCode(String appid,String phone , String salt) {
		String rets = null ;
		String key = "SMSCODE:APPID:" + appid + ":" + phone + ":" + salt  ;
		try {
			rets = jedis.opsForValue().get(key);
		}catch(Exception e) {
			log.error("setNewSmsCode is exception :{},{}",key , e.toString());
		}
		
		return rets ;
	}
	public int removeSmsCode(String appid,String phone,String salt,String tmpstr) {
		int ret = -1 ;
		String key = "SMSCODE:APPID:" + appid + ":" + phone + ":" + salt  ;
		try {
			jedis.opsForValue().getAndSet(key, "expired" + tmpstr );
			jedis.expire(key, 1,TimeUnit.SECONDS);
		}catch(Exception e) {
			log.error("setNewSmsCode is exception :{},{}",key , e.toString());
		}
		return ret ;
	}
	
	public boolean removeLoginToken(String appid,String userid,String tcode) {
		boolean b = false;
		String key = "TOKEN:APPID:"+ appid + ":USERID:"+userid+":"+tcode;
		try {
			b = jedis.delete(key);
		}catch(Exception e) {
			log.error("[removeLoginToken] is exception :{},{}",userid+":"+ tcode ,appid);
			e.printStackTrace();
			
		}
		finally {
			return b;
		}
	}
	public boolean refreshUserToken(String appid,String userid,String tcode) {
		boolean b = false;
		try {
			String key = "";
			key = "TOKEN:APPID:"+ appid + ":USERID:"+userid+":"+tcode;
			jedis.expire(key, 3* 86400,TimeUnit.SECONDS) ;				
			b = true;
		}catch(Exception e) {
			log.error("[refreshUserToken] is exception :{},{}",userid+":"+ tcode ,appid);
			e.printStackTrace();
			
		}
		finally {
			return b;
		}
	}
		
	public int setNewTokenForUser(String appid,String userid ,String token ,String tcode,String clientid) {
		int ret = -1 ;
		try {
//			Jedis jedis = rp.getJedis() ;
			Map<Object,Object> tmap = new HashMap<Object,Object>();
			tmap.put("token", token);
			tmap.put("settime", String.valueOf(System.currentTimeMillis())) ;
			tmap.put("clientid", clientid) ;
			int uid = Integer.parseInt(userid) ;
			String key = "";
			key = "TOKEN:APPID:"+ appid + ":USERID:"+userid+":"+tcode;
			jedis.opsForHash().putAll(key, tmap);
			jedis.expire(key, 3* 86400,TimeUnit.SECONDS) ;				
			ret = 1;
		}catch(Exception e) {
			log.error("[setNewTokenForUser] is exception :{},{}",userid+":"+ tcode ,token);
			e.printStackTrace();
		}
		return ret ;
	}
	 
	public String getTokenForUserClient(String appid,String userid  ,String tcode,String clientid) {
		String rets = "" ;
		try {
			int uid = Integer.parseInt(userid) ;
			String key = "";
				key = "TOKEN:APPID:"+ appid + ":USERID:"+userid+":"+tcode;
//			String token  =  (String)jedis.opsForHash().get(key, "token");
			List<Object> token_data = jedis.opsForHash().multiGet(key, Arrays.asList("token","clientid")) ;
			if(token_data.get(0) == null) {
				
			}
			else {
				if(((String)token_data.get(1)).equals(clientid)) {
					rets = (String)token_data.get(0) ;
					log.info("get token is :"+rets);
				}
			}
		}catch(Exception e) {
			log.error("[getTokenForUser] is exception :{}",userid+":"+ tcode );
			e.printStackTrace();
		}
		return rets ;
		
	}
	
	public String  getTokenForUser(String appid,String userid  ,String tcode) {
		String rets = "" ;
		try {
			int uid = Integer.parseInt(userid) ;
			String key = "";
				key = "TOKEN:APPID:"+ appid + ":USERID:"+userid+":"+tcode;
			String token  =  (String)jedis.opsForHash().get(key, "token");
//			jedis.hget("TOKEN:USERID:"+userid+":"+tcode, "token") ;
			if(token != null){
				rets = token ;
				log.info("get token is :"+token);
			}
			else{
				rets = "";
			}
		}catch(Exception e) {
			log.error("[getTokenForUser] is exception :{}",userid+":"+ tcode );
			e.printStackTrace();
		}
		return rets ;
	}
	
	public boolean setNewSigntoDB(String appid,long timestamp ,String sign,long timeout) {
		boolean ret = false;
		long curtime = System.currentTimeMillis();
		if(curtime < timestamp- 5*60*1000  || curtime> timestamp + 15* 60* 1000) {
			return false;
		}

		try{
			String key = "UNIQUEREQ:"+appid + ":" + sign ;
			ret = jedis.getConnectionFactory().getConnection().setNX(key.getBytes(), String.valueOf(System.currentTimeMillis()).getBytes()) ;
			if(ret)
				jedis.expire(key , timeout, TimeUnit.SECONDS) ;
			
		}catch(Exception e){
			log.error("[setNewSigntoDB] Exception is comming {},{}","UNIQUEREQ:"+sign,sign);
			e.printStackTrace();
		}
		
		return ret ;

	}	


}
