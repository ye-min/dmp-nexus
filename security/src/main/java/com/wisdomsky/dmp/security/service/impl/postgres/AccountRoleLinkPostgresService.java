package com.wisdomsky.dmp.security.service.impl.postgres;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisdomsky.dmp.library.exception.SQLExecutionException;
import com.wisdomsky.dmp.security.mapper.AccountRoleLinkMapper;
import com.wisdomsky.dmp.security.service.AccountRoleLinkService;

@Transactional
@Service("postgresAccountRoleLink")
public class AccountRoleLinkPostgresService implements AccountRoleLinkService {
   private final Logger logger = LoggerFactory.getLogger(this.getClass());

   @Autowired
   AccountRoleLinkMapper mapper;

   @Override
   public int bindAccountRole(long accountId, long roleId) {
      try {
         return mapper.bindAccountRole(accountId, roleId);
      } catch (Exception ex) {
         String errorMessage = "Failed to bind an accound and a role.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int unbindAccountRole(long accountId, long roleId) {
      try {
         return mapper.unbindAccountRole(accountId, roleId);
      } catch (Exception ex) {
         String errorMessage = "Failed to unbind an accound and a role.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }
}