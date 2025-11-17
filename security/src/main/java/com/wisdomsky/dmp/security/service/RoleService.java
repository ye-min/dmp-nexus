package com.wisdomsky.dmp.security.service;

import java.util.List;

import com.wisdomsky.dmp.library.model.SortField;
import com.wisdomsky.dmp.security.model.Role;
import com.wisdomsky.dmp.security.model.RoleCreateParam;
import com.wisdomsky.dmp.security.model.RoleQueryParam;
import com.wisdomsky.dmp.security.model.RoleUpdateParam;

public interface RoleService {
   int createRole(RoleCreateParam param);
   int updateRole(RoleUpdateParam param);
   int deleteRole(RoleQueryParam param);
   long countRole(RoleQueryParam param);
   List<Role> retrieveRole(RoleQueryParam param, List<SortField> sortFieldList, Integer offset, Integer limit);
   int cleanupRole();
}
