package com.wisdomsky.dmp.library.model;

public class PageParam {
   private Integer index;
   private Integer size;

   public PageParam() {
   }

   public PageParam(Integer index, Integer size) {
      this.index = index;
      this.size = size;
   }
   
   public Integer getIndex() {
      return index;
   }
   public void setIndex(Integer index) {
      this.index = index;
   }
   public Integer getSize() {
      return size;
   }
   public void setSize(Integer size) {
      this.size = size;
   }
}
