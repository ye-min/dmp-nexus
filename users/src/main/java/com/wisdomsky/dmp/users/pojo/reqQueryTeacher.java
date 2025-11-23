package com.wisdomsky.dmp.users.pojo;

import lombok.Data;

@Data
public class reqQueryTeacher extends reqBaseData {
   private int teacher_id = -1;
   private String teacher_name;
   private int state = -1;
}
