package com.wisdomsky.dmp.archive.pojo.setting;

import lombok.Data;

@Data
public class Group {
   private int id;
   private String code;
   private String name;
   private int status;
   private int gradeYear;
   private int studentAmount;
   private Integer teacherId;
   private String teacherName;
}
