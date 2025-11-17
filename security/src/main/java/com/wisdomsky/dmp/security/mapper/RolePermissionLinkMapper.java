package com.wisdomsky.dmp.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RolePermissionLinkMapper {

   // 11. bindRolePermission
   int bindRolePermission(
      @Param("roleId") long roleId,
      @Param("permissionId") long permissionId);
   // 12. unbindRolePermission
   int unbindRolePermission(
      @Param("roleId") long roleId,
      @Param("permissionId") long permissionId
   );
}