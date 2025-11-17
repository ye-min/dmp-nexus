package com.wisdomsky.dmp.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.wisdomsky.dmp.library.model.SortField;
import com.wisdomsky.dmp.security.model.Menu;
import com.wisdomsky.dmp.security.model.MenuCreateParam;
import com.wisdomsky.dmp.security.model.MenuQueryParam;
import com.wisdomsky.dmp.security.model.MenuUpdateParam;

@Mapper
public interface MenuMapper {
   // 1. create
   int create(@Param("param") MenuCreateParam param);
   // 2. update
   int update(@Param("param") MenuUpdateParam param);
   // 3. delete
   int delete(@Param("param") MenuQueryParam param);
   // 4. count
   long count(@Param("param") MenuQueryParam param);
   // 5. retrieve
   List<Menu> retrieve(
      @Param("param") MenuQueryParam param,
      @Param("sortFieldList") List<SortField> sortFieldList,
      @Param("offset") Integer offset,
      @Param("limit") Integer limit);
 
   // 6. cleanup
   int cleanup();
}