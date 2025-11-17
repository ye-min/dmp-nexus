package com.wisdomsky.dmp.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountRoleLinkMapper {
   // 11. bindAccountRole
   int bindAccountRole(
      @Param("accountId") long accountId,
      @Param("roleId") long roleId);
   // 12. unbindAccountRole
   int unbindAccountRole(
      @Param("accountId") long accountId,
      @Param("roleId") long roleId);
}