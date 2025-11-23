package com.wisdomsky.dmp.users.pojo;

import lombok.Data;

@Data
public class reqQueryStudent extends reqBaseData {
   private int teacher_id = -1;
   private int student_id = -1;
   private int class_id = -1;
   private String student_name;
   private String student_code;
   private int state = -1;
}
