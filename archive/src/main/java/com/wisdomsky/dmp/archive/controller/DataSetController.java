package com.wisdomsky.dmp.archive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wisdomsky.dmp.archive.exception.InvalidParameterException;
import com.wisdomsky.dmp.archive.exception.SQLExecutionException;
import com.wisdomsky.dmp.archive.pojo.common.ResponseTemplate;
import com.wisdomsky.dmp.archive.pojo.data.DataSet;
import com.wisdomsky.dmp.archive.service.data.DataSetService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping(value = "/archive/dataset")
@Tag(name = "DataSet")
@CrossOrigin
@Slf4j
public class DataSetController extends BaseController {

   @Autowired
   private DataSetService service;

   @PostMapping(value = "/list", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<JSONObject> getDataSetList(
         @RequestHeader(value="appID") String appid ,
         @RequestHeader(value="userID") String userid ,
         @RequestHeader(value="timestamp") long timestamp ,
         @RequestHeader(value="clientID") String clientid ,
         @RequestHeader(value="Sign") String sign ,
         @RequestBody String request) {

      log.info("[getDataSetList] is {}.body is :{}", "userid:" + userid + ",sign:" + sign, request);
      int code = 0;
      String message = "success";
      JSONObject data = new JSONObject();
      JSONArray itemList = new JSONArray();
      ResponseTemplate<JSONObject> response = new ResponseTemplate<JSONObject>();
      try {
         List<DataSet> list = service.retrieveList();
         for (DataSet item : list) {
            JSONObject json = service.toJSONObject(item);
            itemList.add(json);
         }
         long total = service.count();
         data.put("total", total);
         data.put("itemList", itemList);
         response.setData(data);
      } catch (InvalidParameterException ex) {
         code = -70109;
         message = ex.getMessage();
      } catch (SQLExecutionException ex) {
         code = -70501;
         message = ex.getMessage();
      } catch (Exception ex) {
         code = -70002;
         message = "Get the dataset list failed.";
         logger.error(message, ex);
      } finally {
         response.setErrcode(code);
         response.setErrmsg(message);
      }
      return response;
   }
   
   @PostMapping(value = "/list/page", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<JSONObject> getDataSetPageList(
         @RequestHeader(value="appID") String appid ,
         @RequestHeader(value="userID") String userid ,
         @RequestHeader(value="timestamp") long timestamp ,
         @RequestHeader(value="clientID") String clientid ,
         @RequestHeader(value="Sign") String sign ,
         @RequestBody String request) {

      log.info("[getDataSetPageList] is {}.body is :{}", "userid:" + userid + ",sign:" + sign, request);
      int code = 0;
      String message = "success";
      JSONObject data = new JSONObject();
      JSONArray itemList = new JSONArray();
      ResponseTemplate<JSONObject> response = new ResponseTemplate<JSONObject>();
      try {
         JSONObject jsonRequest = JSON.parseObject(request);
         int pageIndex = parseIntValue(jsonRequest, "pageIndex");
         int pageSize = parseIntValue(jsonRequest, "pageSize");
         List<DataSet> list = service.retrieveList(pageIndex, pageSize);
         for (DataSet item : list) {
            JSONObject json = service.toJSONObject(item);
            itemList.add(json);
         }
         long total = service.count();
         data.put("total", total);
         data.put("itemList", itemList);
         response.setData(data);
      } catch (InvalidParameterException ex) {
         code = -70109;
         message = ex.getMessage();
      } catch (SQLExecutionException ex) {
         code = -70501;
         message = ex.getMessage();
      } catch (Exception ex) {
         code = -70002;
         message = "Get the dataset pagination list failed.";
         logger.error(message, ex);
      }
      finally {
         response.setErrcode(code);
         response.setErrmsg(message);
      }
      return response;
   }
   
   @PostMapping(value = "/detail", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<JSONObject> getDataSetDetail(
         @RequestHeader(value="appID") String appid ,
         @RequestHeader(value="userID") String userid ,
         @RequestHeader(value="timestamp") long timestamp ,
         @RequestHeader(value="clientID") String clientid ,
         @RequestHeader(value="Sign") String sign ,
         @RequestBody String request) {

      log.info("[getDataSetDetail] is {}.body is :{}", "userid:" + userid + ",sign:" + sign, request);
      int code = 0;
      String message = "success";
      JSONObject data = new JSONObject();
      ResponseTemplate<JSONObject> response = new ResponseTemplate<JSONObject>();
      try {
         JSONObject jsonRequest = JSON.parseObject(request);
         int id = parseIntValue(jsonRequest, "id");
         DataSet item = service.retrieveDetail(id);
         data = service.toJSONObject(item);
         long size = data.size();
         logger.debug(Long.toString(size));
         response.setData(data);
      } catch (InvalidParameterException ex) {
         code = -70109;
         message = ex.getMessage();
      } catch (SQLExecutionException ex) {
         code = -70502;
         message = ex.getMessage();
      } catch (Exception ex) {
         code = -70002;
         message = "Get the dataset detail failed.";
         logger.error(message, ex);
      }
      finally {
         response.setErrcode(code);
         response.setErrmsg(message);
      }
      return response;
   }

   @PostMapping(value = "/create", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<JSONObject> createDataSet(
         @RequestHeader(value="appID") String appid ,
         @RequestHeader(value="userID") String userid ,
         @RequestHeader(value="timestamp") long timestamp ,
         @RequestHeader(value="clientID") String clientid ,
         @RequestHeader(value="Sign") String sign ,
         @RequestBody String request) {

      log.info("[createDataSet] is {}.body is :{}", "userid:" + userid + ",sign:" + sign, request);
      int code = 0;
      String message = "success";
      JSONObject data = new JSONObject();
      ResponseTemplate<JSONObject> response = new ResponseTemplate<JSONObject>();
      try {
         JSONObject jsonRequest = JSON.parseObject(request);
         DataSet item = service.parseJSONObject(jsonRequest);

         int id = service.insert(item);
         data.put("id", id);
      } catch (InvalidParameterException ex) {
         code = -70109;
         message = ex.getMessage();
      } catch (SQLExecutionException ex) {
         code = -70503;
         message = ex.getMessage();
      } catch (Exception ex) {
         code = -70002;
         message = "Create the dataset failed.";
         logger.error(message, ex);
      } finally {
         response.setErrcode(code);
         response.setErrmsg(message);
      }
      return response;
   }
   
   @PostMapping(value = "/update", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<JSONObject> updateDataSet(
         @RequestHeader(value="appID") String appid ,
         @RequestHeader(value="userID") String userid ,
         @RequestHeader(value="timestamp") long timestamp ,
         @RequestHeader(value="clientID") String clientid ,
         @RequestHeader(value="Sign") String sign ,
         @RequestBody String request) {

      log.info("[updateDataSet] is {}.body is :{}", "userid:" + userid + ",sign:" + sign, request);
      int code = 0;
      String message = "success";
      JSONObject data = new JSONObject();
      ResponseTemplate<JSONObject> response = new ResponseTemplate<JSONObject>();
      try {
         JSONObject jsonRequest = JSON.parseObject(request);
         int id = parseIntValue(jsonRequest, "id");
         String comment = parseStringValue(jsonRequest, "comment", "");

         service.update(id, comment);
         data.put("id", id);
      } catch (InvalidParameterException ex) {
         code = -70109;
         message = ex.getMessage();
      } catch (SQLExecutionException ex) {
         code = -70503;
         message = ex.getMessage();
      } catch (Exception ex) {
         code = -70002;
         message = "Create the dataset failed.";
         logger.error(message, ex);
      }
      finally {
         response.setErrcode(code);
         response.setErrmsg(message);
      }
      return response;
   }

   @PostMapping(value = "/delete", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<JSONObject> deleteDataSet(
         @RequestHeader(value="appID") String appid ,
         @RequestHeader(value="userID") String userid ,
         @RequestHeader(value="timestamp") long timestamp ,
         @RequestHeader(value="clientID") String clientid ,
         @RequestHeader(value="Sign") String sign ,
         @RequestBody String request) {

      log.info("[deleteDataSet] is {}.body is :{}", "userid:" + userid + ",sign:" + sign, request);
      int code = 0;
      String message = "success";
      // JSONObject data = new JSONObject();
      ResponseTemplate<JSONObject> response = new ResponseTemplate<JSONObject>();
      try {
         JSONObject jsonRequest = JSON.parseObject(request);
         int id = parseIntValue(jsonRequest, "id");
         service.delete(id);
      } catch (InvalidParameterException ex) {
         code = -70109;
         message = ex.getMessage();
      } catch (SQLExecutionException ex) {
         code = -70504;
         message = ex.getMessage();
      } catch (Exception ex) {
         code = -70002;
         message = "Delete the dataset failed.";
         logger.error(message, ex);
      }
      finally {
         response.setErrcode(code);
         response.setErrmsg(message);
      }
      return response;
   }
}
