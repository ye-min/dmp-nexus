package com.wisdomsky.dmp.security.model;

import java.util.List;

import com.wisdomsky.dmp.library.common.ArgumentUtility;

public class PermissionQueryParam {
   private Long idEqual;
   private String codeEqual;
   private String codeLike;
   private String nameLike;
   private Integer statusEqual;
   private List<Integer> statusIn;
   private Long accountIdEqual;
   private Long roleIdEqual;
   private Long menuIdEqual;

   public PermissionQueryParam(Long idEqual, String codeEqual, String codeLike, 
                             String nameLike, Integer statusEqual, List<Integer> statusIn, 
                             Long accountIdEqual, Long roleIdEqual, Long menuIdEqual) {
      this.idEqual = idEqual;
      this.codeEqual = codeEqual;
      this.codeLike = codeLike;
      this.nameLike = nameLike;
      this.statusEqual = statusEqual;
      this.statusIn = statusIn;
      this.accountIdEqual = accountIdEqual;
      this.roleIdEqual = roleIdEqual;
      this.menuIdEqual = menuIdEqual;
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

   public Integer getStatusEqual() {
      return statusEqual;
   }

   public List<Integer> getStatusIn() {
      return statusIn;
   }

   public Long getAccountIdEqual() {
      return accountIdEqual;
   }

   public Long getRoleIdEqual() {
      return roleIdEqual;
   }

   public Long getMenuIdEqual() {
      return menuIdEqual;
   }

   public static class Builder {
      private Long idEqual;
      private String codeEqual;
      private String codeLike;
      private String nameLike;
      private Integer statusEqual;
      private List<Integer> statusIn;
      private Long accountIdEqual;
      private Long roleIdEqual;
      private Long menuIdEqual;

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

      public Builder withStatusEqual(Integer value) {
         this.statusEqual = value;
         return this;
      }

      public Builder withStatusIn(List<Integer> value) {
         this.statusIn = ArgumentUtility.validateOptionalEnumList(value, "status");
         return this;
      }

      public Builder withAccountIdEqual(Long value) {
         this.accountIdEqual = ArgumentUtility.validateRequiredLong(value, "accountId");
         return this;
      }
   
      public Builder withRoleIdEqual(Long value) {
         this.roleIdEqual = ArgumentUtility.validateRequiredLong(value, "roleId");
         return this;
      }

      public Builder withMenuIdEqual(Long value) {
         this.menuIdEqual = ArgumentUtility.validateRequiredLong(value, "menuId");
         return this;
      }

      public PermissionQueryParam build() {
         return new PermissionQueryParam(
               idEqual, codeEqual, codeLike, 
               nameLike, statusEqual, statusIn, 
               accountIdEqual, roleIdEqual, menuIdEqual);
      }
   }
}
