package com.wisdomsky.dmp.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PermissionMenuLinkMapper {

   // 11. bindPermissionMenu
    int bindPermissionMenu(
      @Param("permissionId") long permissionId,
      @Param("menuId") long menuId);
    // 12. unbindPermissionMenu
    int unbindPermissionMenu(
      @Param("permissionId") long permissionId,
      @Param("menuId") long menuId);
}