package com.wisdomsky.dmp.archive.service.archive.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson2.JSONObject;
import com.wisdomsky.dmp.archive.common.TimestampUtility;
import com.wisdomsky.dmp.archive.exception.InvalidJSONException;
import com.wisdomsky.dmp.archive.exception.SQLExecutionException;
import com.wisdomsky.dmp.archive.mapper.archive.RFMArchiveMapper;
import com.wisdomsky.dmp.archive.pojo.archive.RFMArchive;
import com.wisdomsky.dmp.archive.pojo.archive.StudentRFMArchive;
import com.wisdomsky.dmp.archive.service.archive.RFMArchiveService;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
public class RFMArchiveServiceImpl implements RFMArchiveService{
   @Autowired
   RFMArchiveMapper mapper;

   @Override
   public long count() {
      try {
         return mapper.count();
      } catch (Exception ex) {
         String errorMessage = "Failed to retrieve the record count from rfm archive records.";
         log.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public long studentCount(int accountType, List<Integer> groupList, int studentId, String startTime, String endTime) {
      try {
         return mapper.studentCount(accountType, groupList, studentId, startTime, endTime);
      } catch (Exception ex) {
         String errorMessage = "Failed to retrieve the student record count from rfm archive records.";
         log.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public long personalCount(int accountId, String startTime, String endTime) {
      try {
         return mapper.personalCount(accountId, startTime, endTime);
      } catch (Exception ex) {
         String errorMessage = "Failed to retrieve the personal record count from rfm archive records.";
         log.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int insert(RFMArchive item) {
      try {
         return mapper.insert(item);
      } catch (Exception ex) {
         String errorMessage = "Failed to insert the rfm archive record.";
         log.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int delete(int id) {
      try {
         return mapper.delete(id);
      } catch (Exception ex) {
         String errorMessage = "Failed to delete the rfm archive record.";
         log.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }
   
   @Override
   public List<RFMArchive> retrieveList() {
      try {
         return mapper.findList();
      } catch (Exception ex) {
         String errorMessage = "Failed to retrieve list data from rfm archive records.";
         log.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public List<RFMArchive> retrievePersonalList(int pageIndex, int pageSize, int accountId, String startTime, String endTime) {
      try {
         int count = pageSize;
         pageIndex = pageIndex > 1 ? pageIndex : 1;
         int offset = (pageIndex - 1) * count;
         return mapper.findPersonalList(count, offset, accountId, startTime, endTime);
      } catch (Exception ex) {
         String errorMessage = "Failed to retrieve paginated list data from personal rfm archive records.";
         log.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public List<StudentRFMArchive> retrieveStudentList(int pageIndex, int pageSize, int accountType, List<Integer> groupList, int studentId, String startTime, String endTime) {
      try {
         int count = pageSize;
         pageIndex = pageIndex > 1 ? pageIndex : 1;
         int offset = (pageIndex - 1) * count;
         return mapper.findStudentPageList(count, offset, accountType, groupList, studentId, startTime, endTime);
      } catch (Exception ex) {
         String errorMessage = "Failed to retrieve paginated list data from rfm archive records.";
         log.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public RFMArchive retrieveDetail(int id) {
      try {
         return mapper.findDetail(id);
      } catch (Exception ex) {
         String errorMessage = "Failed to retrieve the detail data from rfm archive records.";
         log.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public JSONObject toJSONObject(RFMArchive item) {
      JSONObject obj = new JSONObject();
      obj.put("id", item.getId());
      obj.put("recordTime", TimestampUtility.sqlTimestamp2String(item.getRecordTime(), 1));
      obj.put("accountId", item.getAccountId());
      obj.put("accountName", item.getAccountName());
      obj.put("accountType", item.getAccountType());
      obj.put("resultDataSet", item.getResultDataSet());
      obj.put("resultFieldList", item.getResultFieldList());
      obj.put("parameter", item.getParameter());
      return obj;
   }

   @Override
   public JSONObject toJSONObject(StudentRFMArchive item) {
      JSONObject obj = new JSONObject();
      obj.put("id", item.getId());
      obj.put("recordTime", TimestampUtility.sqlTimestamp2String(item.getRecordTime(), 1));
      obj.put("groupId", item.getGroupId());
      obj.put("groupName", item.getGroupName());
      obj.put("accountId", item.getAccountId());
      obj.put("accountName", item.getAccountName());
      obj.put("accountType", item.getAccountType());
      obj.put("resultDataSet", item.getResultDataSet());
      obj.put("resultFieldList", item.getResultFieldList());
      obj.put("parameter", item.getParameter());
      return obj;
   }

   @Override
   public RFMArchive parseJSONObject(JSONObject jsonObject) throws InvalidJSONException {
      try {
         checkJSONObject(jsonObject);

         int id = jsonObject.getIntValue("id");
         String recordTimeValue = jsonObject.getString("recordTime");
         Timestamp recordTime = TimestampUtility.string2SqlTimestamp(recordTimeValue, 2);
         int accountId = jsonObject.getIntValue("accountId");
         String accountName = jsonObject.getString("accountName");
         int accountType = jsonObject.getIntValue("accountType");
         String resultDataSet = jsonObject.getString("resultDataSet");
         String resultFieldList = jsonObject.getString("resultFieldList");
         String parameter = jsonObject.getString("parameter");

         RFMArchive item = new RFMArchive(id, recordTime, accountId, accountName, accountType, resultDataSet, resultFieldList, parameter);
         return item;
      } catch (Exception ex) {
         throw new InvalidJSONException(ex);
      }
   }

   private void checkJSONObject(JSONObject jsonObject) throws InvalidJSONException {
      if (!jsonObject.containsKey("id")) {
         String message = String.format("The JSON object need the id property.");
         throw new InvalidJSONException(message);
      }
      if (!jsonObject.containsKey("recordTime")) {
         String message = String.format("The JSON object need the recordTime property.");
         throw new InvalidJSONException(message);
      }
      if (!jsonObject.containsKey("accountId")) {
         String message = String.format("The JSON object need the accountId property.");
         throw new InvalidJSONException(message);
      }
      if (!jsonObject.containsKey("resultDataSet")) {
         String message = String.format("The JSON object need the resultDataSet property.");
         throw new InvalidJSONException(message);
      }
      if (!jsonObject.containsKey("resultFieldList")) {
         String message = String.format("The JSON object need the resultFieldList property.");
         throw new InvalidJSONException(message);
      }
      if (!jsonObject.containsKey("parameter")) {
         String message = String.format("The JSON object need the parameter property.");
         throw new InvalidJSONException(message);
      }
   }
}
