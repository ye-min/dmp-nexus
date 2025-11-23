package com.wisdomsky.dmp.archive.service.archive;

import java.util.List;

import com.alibaba.fastjson2.JSONObject;
import com.wisdomsky.dmp.archive.exception.InvalidJSONException;
import com.wisdomsky.dmp.archive.pojo.archive.KMeansArchive;
import com.wisdomsky.dmp.archive.pojo.archive.StudentKMeansArchive;

public interface KMeansArchiveService {
   long count();
   long studentCount(int accountType, List<Integer> groupList, int studentId, String startTime, String endTime);
   long personalCount(int accountId, String startTime, String endTime);

   int insert(KMeansArchive item);
   int delete(int id);
   
   List<KMeansArchive> retrieveList();
   List<StudentKMeansArchive> retrieveStudentList(int pageIndex, int pageSize, int accountType, List<Integer> groupList, int studentId, String startTime, String endTime);
   List<KMeansArchive> retrievePersonalList(int pageIndex, int pageSize, int accountId, String startTime, String endTime);

   KMeansArchive retrieveDetail(int id);
   
   JSONObject toJSONObject(KMeansArchive item);
   JSONObject toJSONObject(StudentKMeansArchive item);
   KMeansArchive parseJSONObject(JSONObject jsonObject) throws InvalidJSONException;
}
