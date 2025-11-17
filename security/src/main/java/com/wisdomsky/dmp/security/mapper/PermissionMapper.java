package com.wisdomsky.dmp.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.wisdomsky.dmp.library.model.SortField;
import com.wisdomsky.dmp.security.model.Permission;
import com.wisdomsky.dmp.security.model.PermissionCreateParam;
import com.wisdomsky.dmp.security.model.PermissionQueryParam;
import com.wisdomsky.dmp.security.model.PermissionUpdateParam;

@Mapper
public interface PermissionMapper {
   // 1. create
   int create(@Param("param") PermissionCreateParam param);
   // 2. update
   int update(@Param("param") PermissionUpdateParam param);
   // 3. delete
   int delete(@Param("param") PermissionQueryParam param);
   // 4. count
   long count(@Param("param") PermissionQueryParam param);
   // 5. retrieve
   List<Permission> retrieve(
      @Param("param") PermissionQueryParam param,
      @Param("sortFieldList") List<SortField> sortFieldList,
      @Param("offset") int offset,
      @Param("limit") int limit);
   
   // 6. cleanup
   int cleanup();
}