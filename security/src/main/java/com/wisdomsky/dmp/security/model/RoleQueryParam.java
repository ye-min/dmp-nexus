package com.wisdomsky.dmp.security.model;

import java.util.List;

import com.wisdomsky.dmp.library.common.ArgumentUtility;

public class RoleQueryParam {
   private Long idEqual;
   private String codeEqual;
   private String codeLike;
   private String nameLike;
   private Integer statusEqual;
   private List<Integer> statusIn;
   private Long accountIdEqual;
   private Long permissionIdEqual;

   public RoleQueryParam(
         Long idEqual,
         String codeEqual,
         String codeLike,
         String nameLike,
         Integer statusEqual,
         List<Integer> statusIn,
         Long accountIdEqual, 
         Long permissionIdEqual) {
      this.idEqual = idEqual;
      this.codeEqual = codeEqual;
      this.codeLike = codeLike;
      this.nameLike = nameLike;
      this.statusEqual = statusEqual;
      this.statusIn = statusIn;
      this.accountIdEqual = accountIdEqual;
      this.permissionIdEqual = permissionIdEqual;
   }

   public Long getIdEqual() {
      return idEqual;
   }

   public String getRodeEqual() {
      return codeEqual;
   }

   public String getRodeLike() {
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

   public Long getPermissionIdEqual() {
      return permissionIdEqual;
   }

   public static class Builder {
      private Long idEqual;
      private String codeEqual;
      private String codeLike;
      private String nameLike;
      private Integer statusEqual;
      private List<Integer> statusIn;
      private Long accountIdEqual;
      private Long permissionIdEqual;

      public Builder() {
      }

      public Builder withIdEqual(Long value) {
         this.idEqual = ArgumentUtility.validateRequiredLong(value, "id");
         return this;
      }

      public Builder withRodeEqual(String value) {
         this.codeEqual = value;
         return this;
      }

      public Builder withRodeLike(String value) {
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

      public Builder withPermissionIdEqual(Long value) {
         this.permissionIdEqual = ArgumentUtility.validateRequiredLong(value, "permissionId");
         return this;
      }

      public RoleQueryParam build() {
         return new RoleQueryParam(
            idEqual, codeEqual, codeLike,
            nameLike, statusEqual, statusIn,
            accountIdEqual, permissionIdEqual);
      }
   }
}
