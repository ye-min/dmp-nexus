package com.wisdomsky.dmp.security.service;

import java.util.List;

import com.wisdomsky.dmp.library.model.SortField;
import com.wisdomsky.dmp.security.model.Permission;
import com.wisdomsky.dmp.security.model.PermissionCreateParam;
import com.wisdomsky.dmp.security.model.PermissionQueryParam;
import com.wisdomsky.dmp.security.model.PermissionUpdateParam;

public interface PermissionService {
   int createPermission(PermissionCreateParam param);
   int updatePermission(PermissionUpdateParam param);
   int deletePermission(PermissionQueryParam param);
   long countPermission(PermissionQueryParam param);
   List<Permission> retrievePermission(PermissionQueryParam param, List<SortField> sortFieldList, Integer offset, Integer limit);
   int cleanupPermission();
}
