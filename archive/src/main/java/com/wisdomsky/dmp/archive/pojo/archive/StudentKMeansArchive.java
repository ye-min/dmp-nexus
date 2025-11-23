package com.wisdomsky.dmp.archive.pojo.archive;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentKMeansArchive {
   private int id;
   private Timestamp recordTime;
   private int groupId;
   private String groupName;
   private int accountId;
   private String accountName;
   private int accountType;
   private String resultDataSet;
   private String resultFieldList;
   private String parameter;
   // private String rawDataSet;
   // private String userFieldName;
   // private String featureFieldName;
   // private int kValue;
   // private int iterationCount;
}
