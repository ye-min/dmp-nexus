package com.wisdomsky.dmp.library.model;

public class SortField {
   private String field;
   private boolean ascending;

   public SortField() {
   }

   public SortField(String field, boolean ascending) {
      this.field = field;
      this.ascending = ascending;
   }

   // Getters and Setters
   public String getField() {
      return field;
   }

   public void setField(String field) {
      this.field = field;
   }

   public boolean isAscending() {
      return ascending;
   }

   public void setAscending(boolean ascending) {
      this.ascending = ascending;
   }

   @Override
   public String toString() {
      return "SortField{" +
               "field='" + field + '\'' +
               ", ascending=" + ascending +
               '}';
   }
}