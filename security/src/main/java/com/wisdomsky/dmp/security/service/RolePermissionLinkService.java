package com.wisdomsky.dmp.security.service;

public interface RolePermissionLinkService {   
   int bindRolePermission(long roleId, long permissionId);
   int unbindRolePermission(long roleId, long permissionId);
}
