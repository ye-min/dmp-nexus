package com.wisdomsky.dmp.library.model;

import org.apache.commons.lang3.StringUtils;

import com.wisdomsky.dmp.library.exception.InvalidArgumentException;

public class LoginParam {
   private String code ;

	public String getCode() {
		return code;
  }

  public void setCode(String code) {
		this.code = code;
  }

  public void validate() {
      if (StringUtils.isBlank(code)) {
         throw new InvalidArgumentException("The value of the code cannot be empty.");
      }
      if (code.length() > 32) {
         throw new InvalidArgumentException("The length of the code cannot exceed 32 characters.");
      }
   }
}
