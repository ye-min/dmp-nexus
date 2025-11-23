package com.wisdomsky.dmp.archive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wisdomsky.dmp.archive.exception.InvalidParameterException;
import com.wisdomsky.dmp.archive.exception.SQLExecutionException;
import com.wisdomsky.dmp.archive.pojo.archive.RFMArchive;
import com.wisdomsky.dmp.archive.pojo.archive.StudentRFMArchive;
import com.wisdomsky.dmp.archive.pojo.common.ResponseTemplate;
import com.wisdomsky.dmp.archive.pojo.setting.Group;
import com.wisdomsky.dmp.archive.service.archive.RFMArchiveService;
import com.wisdomsky.dmp.archive.service.setting.GroupService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/archive/rfm")
@Tag(name = "RFM")
@CrossOrigin
@Slf4j
public class RFMArchiveController extends BaseController {

   @Autowired
   private RFMArchiveService service;

   @Autowired
   private GroupService groupService;

   @PostMapping(value = "/list", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<JSONObject> getRFMArchiveList(
         @RequestHeader(value="appID") String appid ,
         @RequestHeader(value="userID") String userid ,
         @RequestHeader(value="timestamp") long timestamp ,
         @RequestHeader(value="clientID") String clientid ,
         @RequestHeader(value="Sign") String sign ,
         @RequestBody String request) {

      log.info("[getRFMArchiveList] is {}.body is :{}", "userid:" + userid + ",sign:" + sign, request);
      int code = 0;
      String message = "success";
      JSONObject data = new JSONObject();
      JSONArray itemList = new JSONArray();
      ResponseTemplate<JSONObject> response = new ResponseTemplate<JSONObject>();
      try {
         List<RFMArchive> list = service.retrieveList();
         for (RFMArchive item : list) {
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
         code = -70701;
         message = ex.getMessage();
      } catch (Exception ex) {
         code = -70002;
         message = "Get the RFM archive list failed.";
         logger.error(message, ex);
      } finally {
         response.setErrcode(code);
         response.setErrmsg(message);
      }
      return response;
   }
   
   @PostMapping(value = "/list/personal/page", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<JSONObject> getPersonalRFMArchiveList(
         @RequestHeader(value="appID") String appid ,
         @RequestHeader(value="userID") String userid ,
         @RequestHeader(value="timestamp") long timestamp ,
         @RequestHeader(value="clientID") String clientid ,
         @RequestHeader(value="Sign") String sign ,
         @RequestBody String request)
   {
      log.info("[getPersonalRFMArchiveList] is {}.body is :{}", "userid:" + userid + ",sign:" + sign, request);
      int code = 0;
      String message = "success";
      JSONObject data = new JSONObject();
      JSONArray itemList = new JSONArray();
      ResponseTemplate<JSONObject> response = new ResponseTemplate<JSONObject>();
      try {
         JSONObject jsonRequest = JSON.parseObject(request);
         int pageIndex = parseIntValue(jsonRequest, "pageIndex");
         int pageSize = parseIntValue(jsonRequest, "pageSize");
         int accountId = parseIntValue(jsonRequest, "accountId");
         String startTime = parseStringValue(jsonRequest, "startTime", "");
         String endTime = parseStringValue(jsonRequest, "endTime", "");
         List<RFMArchive> list = service.retrievePersonalList(pageIndex, pageSize, accountId, startTime, endTime);
         for (RFMArchive item : list) {
            JSONObject json = service.toJSONObject(item);
            itemList.add(json);
         }
         long total = service.personalCount(accountId, startTime, endTime);
         data.put("total", total);
         data.put("itemList", itemList);
         response.setData(data);
      } catch (InvalidParameterException ex) {
         code = -70109;
         message = ex.getMessage();
      } catch (SQLExecutionException ex) {
         code = -70702;
         message = ex.getMessage();
      } catch (Exception ex) {
         code = -70002;
         message = "Get the personal RFM archive list failed.";
         logger.error(message, ex);
      }
      finally {
         response.setErrcode(code);
         response.setErrmsg(message);
      }
      return response;
   }
   
   @PostMapping(value = "/list/page", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<JSONObject> getRFMArchivePageList(
         @RequestHeader(value="appID") String appid ,
         @RequestHeader(value="userID") String userid ,
         @RequestHeader(value="timestamp") long timestamp ,
         @RequestHeader(value="clientID") String clientid ,
         @RequestHeader(value="Sign") String sign ,
         @RequestBody String request)
   {
      log.info("[getRFMArchivePageList] is {}.body is :{}", "userid:" + userid + ",sign:" + sign, request);
      int code = 0;
      String message = "success";
      JSONObject data = new JSONObject();
      JSONArray itemList = new JSONArray();
      ResponseTemplate<JSONObject> response = new ResponseTemplate<JSONObject>();
      try {
         JSONObject jsonRequest = JSON.parseObject(request);
         int pageIndex = parseIntValue(jsonRequest, "pageIndex");
         int pageSize = parseIntValue(jsonRequest, "pageSize");
         int accountType = parseIntValue(jsonRequest, "accountType", -1);
         int groupId = parseIntValue(jsonRequest, "groupId", -1);
         int studentId = parseIntValue(jsonRequest, "studentId", -1);
         String startTime = parseStringValue(jsonRequest, "startTime", "");
         String endTime = parseStringValue(jsonRequest, "endTime", "");
         List<Integer> groupIdList = new ArrayList<Integer>();
         if (studentId >= 0 || groupId == -1) {
            int teacherId = Integer.parseInt(userid);
            List<Group> groupList = groupService.retrieveListByTeacher(teacherId);
            for (Group group: groupList) {
               groupIdList.add(group.getId());
            }
         } else {
            groupIdList.add(groupId);
         }
         
         List<StudentRFMArchive> list = service.retrieveStudentList(pageIndex, pageSize, accountType, groupIdList, studentId, startTime, endTime);
         for (StudentRFMArchive item : list) {
            JSONObject json = service.toJSONObject(item);
            itemList.add(json);
         }
         long total = service.studentCount(accountType, groupIdList, studentId, startTime, endTime);
         data.put("total", total);
         data.put("itemList", itemList);
         response.setData(data);
      } catch (InvalidParameterException ex) {
         code = -70109;
         message = ex.getMessage();
      } catch (SQLExecutionException ex) {
         code = -70703;
         message = ex.getMessage();
      } catch (Exception ex) {
         code = -70002;
         message = "Get the RFM archive page list failed.";
         logger.error(message, ex);
      }
      finally {
         response.setErrcode(code);
         response.setErrmsg(message);
      }
      return response;
   }

   @PostMapping(value = "/detail", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<JSONObject> getRFMArchiveDetail(
         @RequestHeader(value="appID") String appid ,
         @RequestHeader(value="userID") String userid ,
         @RequestHeader(value="timestamp") long timestamp ,
         @RequestHeader(value="clientID") String clientid ,
         @RequestHeader(value="Sign") String sign ,
         @RequestBody String request) {

      log.info("[getRFMArchiveDetail] is {}.body is :{}", "userid:" + userid + ",sign:" + sign, request);
      int code = 0;
      String message = "success";
      JSONObject data = new JSONObject();
      ResponseTemplate<JSONObject> response = new ResponseTemplate<JSONObject>();
      try {
         JSONObject jsonRequest = JSON.parseObject(request);
         int id = parseIntValue(jsonRequest, "id");
         RFMArchive item = service.retrieveDetail(id);
         data = service.toJSONObject(item);
         response.setData(data);
      } catch (InvalidParameterException ex) {
         code = -70109;
         message = ex.getMessage();
      } catch (SQLExecutionException ex) {
         code = -70704;
         message = ex.getMessage();
      } catch (Exception ex) {
         code = -70002;
         message = "Get the RFM archive detail failed.";
         logger.error(message, ex);
      }
      finally {
         response.setErrcode(code);
         response.setErrmsg(message);
      }
      return response;
   }

   @PostMapping(value = "/create", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<JSONObject> createRFMArchive(
         @RequestHeader(value="appID") String appid ,
         @RequestHeader(value="userID") String userid ,
         @RequestHeader(value="timestamp") long timestamp ,
         @RequestHeader(value="clientID") String clientid ,
         @RequestHeader(value="Sign") String sign ,
         @RequestBody String request) {

      log.info("[createRFMArchive] is {}.body is :{}", "userid:" + userid + ",sign:" + sign, request);
      int code = 0;
      String message = "success";
      JSONObject data = new JSONObject();
      ResponseTemplate<JSONObject> response = new ResponseTemplate<JSONObject>();
      try {
         JSONObject jsonRequest = JSON.parseObject(request);
         RFMArchive item = service.parseJSONObject(jsonRequest);
         int id = service.insert(item);
         data.put("id", id);
         response.setData(data);
      } catch (InvalidParameterException ex) {
         code = -70109;
         message = ex.getMessage();
      } catch (SQLExecutionException ex) {
         code = -70705;
         message = ex.getMessage();
      } catch (Exception ex) {
         code = -70002;
         message = "Create the RFM archive failed.";
         logger.error(message, ex);
      }
      finally {
         response.setErrcode(code);
         response.setErrmsg(message);
      }
      return response;
   }

   @PostMapping(value = "/delete", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<JSONObject> deleteRFMArchive(
         @RequestHeader(value="appID") String appid ,
         @RequestHeader(value="userID") String userid ,
         @RequestHeader(value="timestamp") long timestamp ,
         @RequestHeader(value="clientID") String clientid ,
         @RequestHeader(value="Sign") String sign ,
         @RequestBody String request) {

      log.info("[deleteRFMArchive] is {}.body is :{}", "userid:" + userid + ",sign:" + sign, request);
      int code = 0;
      String message = "success";
      ResponseTemplate<JSONObject> response = new ResponseTemplate<JSONObject>();
      try {
         JSONObject jsonRequest = JSON.parseObject(request);
         int id = parseIntValue(jsonRequest, "id");
         service.delete(id);
      } catch (InvalidParameterException ex) {
         code = -70109;
         message = ex.getMessage();
      } catch (SQLExecutionException ex) {
         code = -70706;
         message = ex.getMessage();
      } catch (Exception ex) {
         code = -70002;
         message = "Delete the RFM archive failed.";
         logger.error(message, ex);
      }
      finally {
         response.setErrcode(code);
         response.setErrmsg(message);
      }
      return response;
   }
}
