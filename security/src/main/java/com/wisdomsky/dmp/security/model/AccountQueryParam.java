package com.wisdomsky.dmp.security.model;

import java.util.List;

import com.wisdomsky.dmp.library.common.ArgumentUtility;
import com.wisdomsky.dmp.library.model.RequestParam;

public class AccountQueryParam extends RequestParam {
   private Long idEqual;
   private String codeEqual;
   private String codeLike;
   private String nameLike;
   private Integer typeEqual;
   private List<Integer> typeIn;
   private Integer statusEqual;
   private List<Integer> statusIn;
   private Long roleIdEqual;

   public AccountQueryParam() {}

   public AccountQueryParam(Long idEqual, String codeEqual, String codeLike, String nameLike, Integer typeEqual, List<Integer> typeIn, Integer statusEqual, List<Integer> statusIn, Long roleIdEqual) {
      this.idEqual = idEqual;
      this.codeEqual = codeEqual;
      this.codeLike = codeLike;
      this.nameLike = nameLike;
      this.typeEqual = typeEqual;
      this.typeIn = typeIn;
      this.statusEqual = statusEqual;
      this.statusIn = statusIn;
      this.roleIdEqual = roleIdEqual;
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

   public Long getRoleIdEqual() {
      return roleIdEqual;
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
      private Long roleIdEqual;

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

      public Builder withRoleIdEqual(Long value) {
         this.roleIdEqual = ArgumentUtility.validateRequiredLong(value, "roleId");
         return this;
      }

      public AccountQueryParam build() {
         return new AccountQueryParam(
            idEqual,
            codeEqual,
            codeLike,
            nameLike,
            typeEqual,
            typeIn,
            statusEqual,
            statusIn,
            roleIdEqual);
      }
   }
}
