package com.wisdomsky.dmp.archive.service.data.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson2.JSONObject;
import com.wisdomsky.dmp.archive.common.TimestampUtility;
import com.wisdomsky.dmp.archive.exception.InvalidJSONException;
import com.wisdomsky.dmp.archive.exception.SQLExecutionException;
import com.wisdomsky.dmp.archive.mapper.data.DataSetMapper;
import com.wisdomsky.dmp.archive.pojo.data.DataSet;
import com.wisdomsky.dmp.archive.service.data.DataSetService;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
public class DataSetServiceImpl implements DataSetService{
   @Autowired
   DataSetMapper mapper;

   @Override
   public long count() {
      try {
         return mapper.count();
      } catch (Exception ex) {
         String errorMessage = "Failed to retrieve the record count from dataset records.";
         log.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int insert(DataSet item) {
      try {
         return mapper.insert(item);
      } catch (Exception ex) {
         String errorMessage = "Failed to insert the dataset record.";
         log.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int update(int id, String comment) {
      try {
         return mapper.update(id, comment);
      } catch (Exception ex) {
         String errorMessage = "Failed to insert the dataset record.";
         log.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int delete(int id) {
      try {
         return mapper.delete(id);
      } catch (Exception ex) {
         String errorMessage = "Failed to delete the dataset record.";
         log.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }
   
   @Override
   public List<DataSet> retrieveList() {
      try {
         return mapper.findList();
      } catch (Exception ex) {
         String errorMessage = "Failed to retrieve list data from dataset records.";
         log.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }

   }

   @Override
   public List<DataSet> retrieveList(int pageIndex, int pageSize) {
      try {
         int count = pageSize;
         pageIndex = pageIndex > 1 ? pageIndex : 1;
         int offset = (pageIndex - 1) * count;
         return mapper.findPageList(count, offset);
      } catch (Exception ex) {
         String errorMessage = "Failed to retrieve list data from dataset records.";
         log.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }

   }

   @Override
   public DataSet retrieveDetail(int id) { 
      try {
         return mapper.findDetail(id);
      } catch (Exception ex) {
         String errorMessage = "Failed to retrieve the detail data from dataset records.";
         log.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public JSONObject toJSONObject(DataSet item) {
      JSONObject obj = new JSONObject();
      obj.put("id", item.getId());
      obj.put("uploadTime", TimestampUtility.sqlTimestamp2String(item.getUploadTime(), 1));
      obj.put("name", item.getName());
      obj.put("content", item.getContent());
      obj.put("size", item.getSize());
      obj.put("comment", item.getComment());
      return obj;
   }

   @Override
   public DataSet parseJSONObject(JSONObject jsonObject) throws InvalidJSONException {
      try {
         checkJSONObject(jsonObject);

         int id = jsonObject.getIntValue("id");
         String uploadTimeValue = jsonObject.getString("uploadTime");
         Timestamp uploadTime = TimestampUtility.string2SqlTimestamp(uploadTimeValue, 2);
         String name = jsonObject.getString("name");
         String content = jsonObject.getString("content");
         int size = jsonObject.getIntValue("size");
         String comment = jsonObject.getString("comment");

         DataSet item = new DataSet(id, uploadTime, name, content, size, comment);
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
      if (!jsonObject.containsKey("uploadTime")) {
         String message = String.format("The JSON object need the uploadTime property.");
         throw new InvalidJSONException(message);
      }
      if (!jsonObject.containsKey("name")) {
         String message = String.format("The JSON object need the name property.");
         throw new InvalidJSONException(message);
      }
      if (!jsonObject.containsKey("content")) {
         String message = String.format("The JSON object need the content property.");
         throw new InvalidJSONException(message);
      }
      if (!jsonObject.containsKey("size")) {
         String message = String.format("The JSON object need the size property.");
         throw new InvalidJSONException(message);
      }
      if (!jsonObject.containsKey("comment")) {
         String message = String.format("The JSON object need the comment property.");
         throw new InvalidJSONException(message);
      }
   }
}
