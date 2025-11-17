package com.wisdomsky.dmp.authority.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wisdomsky.dmp.authority.exception.AuthorityException;
import com.wisdomsky.dmp.authority.model.SignValidateResponse;
import com.wisdomsky.dmp.authority.service.RedisService;
import com.wisdomsky.dmp.library.common.CryptoUtility;

@RestController
@RequestMapping(value = "/sign")
@CrossOrigin
public class SignController {
   private static final Logger logger = LoggerFactory.getLogger(SignController.class);
   private static final String alias = "sign";

   @Autowired
   RedisService redisService;

   @Value("${tjtc.debug:false}")
	private boolean debug = false;

   @PostMapping(value = "/validate-api", produces = "application/json;charset=UTF-8")
   public ResponseEntity<SignValidateResponse> validateAPI(
         @RequestHeader(value="app") String app,
         @RequestHeader(value="account") String account,
         @RequestHeader(value="timestamp") long timestamp,
         @RequestHeader(value="client") String client,
         @RequestHeader(value="digest") String digest,
         @RequestHeader(value="sign") String sign,
         @RequestHeader(value="X-Forwarded-Uri") String uri,
         @RequestBody String request) {

      logger.info("[/{}/validate-api] with {}, {}, {}, {}, {}, {}.", alias, "app: " + app, "account: " + account, "timestamp: " + timestamp, "client: " + client, "sign: " + sign, "digest: " + digest.toString());
      int code = 0;
      String message = "success";
      SignValidateResponse response = new SignValidateResponse();
      
      if (!debug) {
         long current = System.currentTimeMillis();
         if (current < timestamp - 5 * 60 * 1000 || current > timestamp + 15 * 60 * 1000) {
            throw new AuthorityException(-99001, "Invalid request");
         }

         int timeout = 1800;
         if (redisService.checkSign(app, sign, timeout) == false) {
            throw new AuthorityException(-90002, "Duplicate Request");
         }

         String token = redisService.getToken(app, account, client);
         if (token.equals("")) {
            throw new AuthorityException(-99003, "Not logged in or the login has expired.");
         }
         String value = String.format("%s%s%s%s%s%s", app, account, timestamp, client, digest, token);
         String sha1Value = CryptoUtility.sha1(value);
         boolean signResult = sha1Value != null ? sha1Value.equals(sign) : false;
         if (signResult) {
            int tokenTimeout = 7 * 86400; // 60 * 60 * 24 * 7
            try {
               redisService.refreshToken(app, account, client, tokenTimeout);
            } catch (Exception ex) {
               logger.error("[validate/api] refresh token failed.", ex);
            }
            response.setCode(0);
            response.setMessage("success");
         } else {
            throw new AuthorityException(-99004, "Invalid sign");
         }
      }
      response.setCode(code);
      response.setMessage(message);
      if (response.getCode() >= 0) {
         return ResponseEntity.ok().body(response);
      } else {
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
      }
   }

   @PostMapping(value = "/validate-multipart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<SignValidateResponse> validateMultipart(
         @RequestHeader(value="app") String app,
         @RequestHeader(value="account") String account,
         @RequestHeader(value="timestamp") long timestamp,
         @RequestHeader(value="client") String client,
         @RequestHeader(value="digest") String digest,
         @RequestHeader(value="sign") String sign,
         @RequestHeader(value="X-Forwarded-Uri") String uri,
         @RequestPart("file") MultipartFile file,
         @RequestPart(value="description", required=false) String description,
         @RequestPart("mode") String mode) {

      logger.info("[/{}/validate-multipart] with {}, {}, {}, {}, {}, {}.", alias, "app: " + app, "account: " + account, "timestamp: " + timestamp, "client: " + client, "sign: " + sign, "digest: " + digest.toString());
      int code = 0;
      String message = "success";
      SignValidateResponse response = new SignValidateResponse();
      
      if (!debug) {
         long current = System.currentTimeMillis();
         if (current < timestamp - 5 * 60 * 1000 || current > timestamp + 15 * 60 * 1000) {
            throw new AuthorityException(-99001, "Invalid request");
         }

         int timeout = 1800;
         if (redisService.checkSign(app, sign, timeout) == false) {
            throw new AuthorityException(-90002, "Duplicate Request");
         }

         String token = redisService.getToken(app, account, client);
         if (token.equals("")) {
            throw new AuthorityException(-99003, "Not logged in or the login has expired.");
         }
         String value = String.format("%s%s%s%s%s%s", app, account, timestamp, client, digest, token);
         String sha1Value = CryptoUtility.sha1(value);
         boolean signResult = sha1Value != null ? sha1Value.equals(sign) : false;
         if (signResult) {
            int tokenTimeout = 7 * 86400; // 60 * 60 * 24 * 7
            try {
               redisService.refreshToken(app, account, client, tokenTimeout);
            } catch (Exception ex) {
               logger.error("[validate/api] refresh token failed.", ex);
            }
            response.setCode(0);
            response.setMessage("success");
         } else {
            throw new AuthorityException(-99004, "Invalid sign");
         }
      }
      response.setCode(code);
      response.setMessage(message);
      if (response.getCode() >= 0) {
         return ResponseEntity.ok().body(response);
      } else {
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
      }
   }

   @PostMapping(value = "/validate-login", produces = "application/json;charset=UTF-8")
   public ResponseEntity<SignValidateResponse> validateLogin(
         @RequestHeader(value="app") String app,
         @RequestHeader(value="account") String account,
         @RequestHeader(value="timestamp") long timestamp,
         @RequestHeader(value="client") String client,
         @RequestHeader(value="digest") String digest,
         @RequestHeader(value="sign") String sign,
         @RequestHeader(value="X-Forwarded-Uri") String uri,
         @RequestBody String request) {

      logger.info("[/{}/validate-api] with {}, {}, {}, {}, {}, {}.", alias, "app: " + app, "account: " + account, "timestamp: " + timestamp, "client: " + client, "sign: " + sign, "digest: " + digest.toString());
      int code = 0;
      String message = "success";
      SignValidateResponse response = new SignValidateResponse();
      
      if (!debug) {
         long current = System.currentTimeMillis();
         if (current < timestamp - 5 * 60 * 1000 || current > timestamp + 15 * 60 * 1000) {
            throw new AuthorityException(-99001, "Invalid request");
         }

         int timeout = 7200;
         if (redisService.checkSign(app, sign, timeout) == false) {
            throw new AuthorityException(-99002, "Duplicate Request");
         }
      }
      response.setCode(code);
      response.setMessage(message);
      if (response.getCode() >= 0) {
         return ResponseEntity.ok().body(response);
      } else {
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
      }
   }
}
