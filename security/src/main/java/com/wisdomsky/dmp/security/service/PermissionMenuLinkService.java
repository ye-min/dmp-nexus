package com.wisdomsky.dmp.security.service;

public interface PermissionMenuLinkService {
   int bindMenuPermission(long menuId, long permissionId);
   int unbindMenuPermission(long menuId, long permissionId);
}
