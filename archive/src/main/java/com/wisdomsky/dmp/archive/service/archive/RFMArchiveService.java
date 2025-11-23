package com.wisdomsky.dmp.archive.service.archive;

import java.util.List;

import com.alibaba.fastjson2.JSONObject;
import com.wisdomsky.dmp.archive.exception.InvalidJSONException;
import com.wisdomsky.dmp.archive.pojo.archive.RFMArchive;
import com.wisdomsky.dmp.archive.pojo.archive.StudentRFMArchive;

public interface RFMArchiveService {
   long count();
   long studentCount(int accountType, List<Integer> groupList, int studentId, String startTime, String endTime);
   long personalCount(int accountId, String startTime, String endTime);

   int insert(RFMArchive item);
   int delete(int id);

   List<RFMArchive> retrieveList();
   List<StudentRFMArchive> retrieveStudentList(int pageIndex, int pageSize, int accountType, List<Integer> groupList, int studentId, String startTime, String endTime);
   List<RFMArchive> retrievePersonalList(int pageIndex, int pageSize, int accountId, String startTime, String endTime);

   RFMArchive retrieveDetail(int id);
   
   JSONObject toJSONObject(RFMArchive item);
   JSONObject toJSONObject(StudentRFMArchive item);
   RFMArchive parseJSONObject(JSONObject jsonObject) throws InvalidJSONException;
}
