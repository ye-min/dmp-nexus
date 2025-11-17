package com.wisdomsky.dmp.security.service;

public interface AccountRoleLinkService {
   int bindAccountRole(long accountId, long roleId);
   int unbindAccountRole(long accountId, long roleId);
}