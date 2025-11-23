package com.wisdomsky.dmp.archive.service.data;

import java.util.List;

import com.alibaba.fastjson2.JSONObject;
import com.wisdomsky.dmp.archive.exception.InvalidJSONException;
import com.wisdomsky.dmp.archive.pojo.data.DataSet;

public interface DataSetService {
   long count();

   int insert(DataSet item);
   int update(int id, String comment);
   int delete(int id);
   
   List<DataSet> retrieveList();
   List<DataSet> retrieveList(int pageIndex, int pageSize);

   DataSet retrieveDetail(int id);
   
   JSONObject toJSONObject(DataSet item);
   DataSet parseJSONObject(JSONObject jsonObject) throws InvalidJSONException;
}
