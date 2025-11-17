package com.wisdomsky.dmp.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.wisdomsky.dmp.admin.model.Message;
import com.wisdomsky.dmp.admin.model.MessageCreateParam;
import com.wisdomsky.dmp.admin.model.MessageQueryParam;
import com.wisdomsky.dmp.library.model.SortField;

@Mapper
public interface MessageMapper {
   // 1. create
   int create(@Param("param") MessageCreateParam param);
   // 2. update
   // 3. delete
   int delete(@Param("param") MessageQueryParam param);
   // 4. count
   long count(@Param("param") MessageQueryParam param);
   // 5. retrieve
   List<Message> retrieve(
      @Param("param") MessageQueryParam param,
      @Param("sortFieldList") List<SortField> sortFieldList,
      @Param("offset") Integer offset,
      @Param("limit") Integer limit);
 
   // 6. cleanup
   int cleanup();
}