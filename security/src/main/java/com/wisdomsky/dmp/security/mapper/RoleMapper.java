package com.wisdomsky.dmp.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.wisdomsky.dmp.library.model.SortField;
import com.wisdomsky.dmp.security.model.Role;
import com.wisdomsky.dmp.security.model.RoleCreateParam;
import com.wisdomsky.dmp.security.model.RoleQueryParam;
import com.wisdomsky.dmp.security.model.RoleUpdateParam;

@Mapper
public interface RoleMapper {
   // 1. create
   int create(@Param("param") RoleCreateParam param);
   // 2. update
   int update(@Param("param") RoleUpdateParam param);
   // 3. delete
   int delete(@Param("param") RoleQueryParam param);
   // 4. count
   long count(@Param("param") RoleQueryParam param);
   // 5. retrieve
   List<Role> retrieve(
      @Param("param") RoleQueryParam param,
      @Param("sortFieldList") List<SortField> sortFieldList,
      @Param("offset") Integer offset,
      @Param("limit") Integer limit);

   // 6. cleanup
   int cleanup();
}