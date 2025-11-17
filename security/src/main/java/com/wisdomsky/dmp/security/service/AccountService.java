package com.wisdomsky.dmp.security.service;

import java.util.List;

import com.wisdomsky.dmp.library.model.SortField;
import com.wisdomsky.dmp.security.model.Account;
import com.wisdomsky.dmp.security.model.AccountCreateParam;
import com.wisdomsky.dmp.security.model.AccountQueryParam;
import com.wisdomsky.dmp.security.model.AccountUpdateParam;

public interface AccountService {
   int createAccount(AccountCreateParam param);
   int updateAccount(AccountUpdateParam param);
   int deleteAccount(AccountQueryParam param);
   long countAccount(AccountQueryParam param);
   List<Account> retrieveAccount(AccountQueryParam param, List<SortField> sortFieldList, Integer offset, Integer limit);
   int cleanupAccount();
}