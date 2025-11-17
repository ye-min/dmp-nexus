package com.wisdomsky.dmp.security.model;

import java.util.List;

import com.wisdomsky.dmp.library.common.ArgumentUtility;

public class MenuQueryParam {
   private Long idEqual;
   private String codeEqual;
   private String codeLike;
   private String nameLike;
   private Integer typeEqual;
   private List<Integer> typeIn;
   private Integer statusEqual;
   private List<Integer> statusIn;
   private Integer parentIdEqual;
   private Long accountIdEqual;
   private Long roleIdEqual;
   private Long permissionIdEqual;
   
   public MenuQueryParam(
      Long idEqual,
         String codeEqual,
         String codeLike,
         String nameLike,
         Integer typeEqual,
         List<Integer> typeIn,
         Integer statusEqual,
         List<Integer> statusIn,
         Integer parentIdEqual,
         Long accountIdEqual,
         Long roleIdEqual,
         Long permissionIdEqual) {
      this.idEqual = idEqual;
      this.codeEqual = codeEqual;
      this.codeLike = codeLike;
      this.nameLike = nameLike;
      this.typeEqual = typeEqual;
      this.typeIn = typeIn;
      this.statusEqual = statusEqual;
      this.statusIn = statusIn;
      this.parentIdEqual = parentIdEqual;
      this.accountIdEqual = accountIdEqual;
      this.roleIdEqual = roleIdEqual;
      this.permissionIdEqual = permissionIdEqual;
   }

   public Long getIdEqual() {
      return idEqual;
   }

   public String getCodeEqual() {
      return codeEqual;
   }

   public String getCodeLike() {
      return codeLike;
   }

   public String getNameLike() {
      return nameLike;
   }

   public Integer getTypeEqual() {
      return typeEqual;
   }

   public List<Integer> getTypeIn() {
      return typeIn;
   }

   public Integer getStatusEqual() {
      return statusEqual;
   }

   public List<Integer> getStatusIn() {
      return statusIn;
   }

   public Integer getParentIdEqual() {
      return parentIdEqual;
   }

   public Long getAccountIdEqual() {
      return accountIdEqual;
   }

   public Long getRoleIdEqual() {
      return roleIdEqual;
   }

   public Long getPermissionIdEqual() {
      return permissionIdEqual;
   }

   public static class Builder {
      private Long idEqual;
      private String codeEqual;
      private String codeLike;
      private String nameLike;
      private Integer typeEqual;
      private List<Integer> typeIn;
      private Integer statusEqual;
      private List<Integer> statusIn;
      private Integer parentIdEqual;
      private Long accountIdEqual;
      private Long roleIdEqual;
      private Long permissionIdEqual;

      public Builder() {
      }

      public Builder withIdEqual(Long value) {
         this.idEqual = ArgumentUtility.validateRequiredLong(value, "id");
         return this;
      }

      public Builder withCodeEqual(String value) {
         this.codeEqual = value;
         return this;
      }

      public Builder withCodeLike(String value) {
         this.codeLike = value;
         return this;
      }

      public Builder withNameLike(String value) {
         this.nameLike = value;
         return this;
      }

      public Builder withTypeEqual(Integer value) {
         this.typeEqual = value;
         return this;
      }

      public Builder withTypeIn(List<Integer> value) {
         this.typeIn = ArgumentUtility.validateOptionalEnumList(value, "type");
         return this;
      }

      public Builder withStatusEqual(Integer value) {
         this.statusEqual = value;
         return this;
      }

      public Builder withStatusIn(List<Integer> value) {
         this.statusIn = ArgumentUtility.validateOptionalEnumList(value, "status");
         return this;
      }

      public Builder withParentIdEqual(Integer value) {
         this.parentIdEqual = value;
         return this;
      }

      public Builder withAccountIdEqual(Long value) {
         this.accountIdEqual = value;
         return this;
      }

      public Builder withRoleIdEqual(Long value) {
         this.roleIdEqual = value;
         return this;
      }

      public Builder withPermissionIdEqual(Long value) {
         this.permissionIdEqual = value;
         return this;
      }

      public MenuQueryParam build() {
         return new MenuQueryParam(
               idEqual, codeEqual, codeLike,
               nameLike, typeEqual, typeIn,
               statusEqual, statusIn, parentIdEqual,
               accountIdEqual, roleIdEqual, permissionIdEqual);
      }
   }
}
