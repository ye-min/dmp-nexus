package com.wisdomsky.dmp.archive.pojo.setting;

import java.util.Date;

import lombok.Data;

@Data
public class Student {
   private int id;
    private String code;
    private String name;
    private String phoneNumber;
    private String email;
    private int gender;
    private Date birth;
    private int group;
    private int status;
}
