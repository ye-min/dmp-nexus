package com.wisdomsky.dmp.security.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wisdomsky.dmp.library.model.ResponseTemplate;
import com.wisdomsky.dmp.library.model.SimpleResponseTemplate;
import com.wisdomsky.dmp.security.model.TokenCreateResponse;

@FeignClient(name="dmp-authority")
public interface TokenServiceFeignClient {

   @RequestMapping(value="/dmp/authority/token/create", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
   public ResponseTemplate<TokenCreateResponse> createToken(
         @RequestHeader(value="app") String app,
         @RequestHeader(value="account") String account);

   @RequestMapping(value="/dmp/authority/token/delete", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
   public SimpleResponseTemplate deleteToken(
         @RequestHeader(value="app") String app,
         @RequestHeader(value="account") String account,
         @RequestHeader(value="client") String client);
}