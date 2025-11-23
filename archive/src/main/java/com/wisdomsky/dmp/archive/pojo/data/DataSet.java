package com.wisdomsky.dmp.archive.pojo.data;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataSet {
   private int id;
   private Timestamp uploadTime;
   private String name;
   private String content;
   private int size;
   private String comment;
}
