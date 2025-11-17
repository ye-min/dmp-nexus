package com.wisdomsky.dmp.library.model;

public class RequestParam {
   private String serialCode = "";
   private Long operatorId;

   public String getSerialCode() {
      return serialCode;
   }
   public void setSerialCode(String serialCode) {
      this.serialCode = serialCode;
   }

   public Long getOperatorId() {
      return operatorId;
   }
   public void setOperatorId(Long operatorId) {
      this.operatorId = operatorId;
   }
}
