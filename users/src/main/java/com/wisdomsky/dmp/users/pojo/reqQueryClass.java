package com.wisdomsky.dmp.users.pojo;

import lombok.Data;

@Data
public class reqQueryClass extends reqBaseData {
   private int teacher_id = -1;
   private int class_id = -1;
   private int class_year = -1;
   private String class_name;
   private int state = -1;
}
