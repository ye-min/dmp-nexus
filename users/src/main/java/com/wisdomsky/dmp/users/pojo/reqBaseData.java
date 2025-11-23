package com.wisdomsky.dmp.users.pojo;

import lombok.Data;

@Data
public class reqBaseData {
   private String serialNo;
   private int userid;

   private int pageid = 0;
   private int rows = 10;

   public void setRows(int rows) {
      this.rows = rows;
      if (rows <= 0)
         this.rows = 0;
      else if (rows > 500) {
         this.rows = 500;
      } else {
         this.rows = rows;
      }
   }

   public void setPageid(int pageid) {
      if (pageid <= 0)
         this.pageid = 0;
      else
         this.pageid = pageid;
   }
}
