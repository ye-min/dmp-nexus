package com.wisdomsky.dmp.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.wisdomsky.dmp.library.model.SortField;
import com.wisdomsky.dmp.security.model.Account;
import com.wisdomsky.dmp.security.model.AccountCreateParam;
import com.wisdomsky.dmp.security.model.AccountQueryParam;
import com.wisdomsky.dmp.security.model.AccountUpdateParam;

@Mapper
public interface AccountMapper {
   // 1. create
   int create(@Param("param") AccountCreateParam param);
   // 2. update
   int update(@Param("param") AccountUpdateParam param);
   // 3. delete
   int delete(@Param("param") AccountQueryParam param);
   // 4. count
   long count(@Param("param") AccountQueryParam param);
   // 5. retrieve
   List<Account> retrieve(
      @Param("param") AccountQueryParam param,
      @Param("sortFieldList") List<SortField> sortFieldList,
      @Param("offset") Integer offset,
      @Param("limit") Integer limit);
   
   // 6. cleanup
   int cleanup();
}