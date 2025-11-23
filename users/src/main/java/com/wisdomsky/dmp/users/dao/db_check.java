package com.wisdomsky.dmp.users.dao;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class db_check {
	@Autowired 
	private StringRedisTemplate jedis;
	
	public boolean setNewSigntoDB(String appid,long timestamp ,String sign,long timeout) {
		boolean ret = false;
		long curtime = System.currentTimeMillis();
		if(curtime < timestamp- 5*60*1000  || curtime> timestamp + 15* 60* 1000) {
			return false;
		}

		try{
			String key = "UNIQUEREQ:"+appid + ":" + sign ;
			ret =jedis.opsForValue().setIfAbsent(key,  String.valueOf(System.currentTimeMillis()), timeout, TimeUnit.SECONDS) ;
//			ret = jedis.getConnectionFactory().getConnection().setNX(key.getBytes(), String.valueOf(System.currentTimeMillis()).getBytes()) ;
//			if(ret)
//				jedis.expire(key , timeout, TimeUnit.SECONDS) ;
			
		}catch(Exception e){
			log.error("[setNewSigntoDB] Exception is comming {},{}","UNIQUEREQ:"+sign,sign);
			e.printStackTrace();
		}		
		return ret ;
	}

}
