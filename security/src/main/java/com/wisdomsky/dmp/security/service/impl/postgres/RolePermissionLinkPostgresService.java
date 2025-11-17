package com.wisdomsky.dmp.security.service.impl.postgres;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisdomsky.dmp.library.exception.SQLExecutionException;
import com.wisdomsky.dmp.security.mapper.RolePermissionLinkMapper;
import com.wisdomsky.dmp.security.service.RolePermissionLinkService;

@Transactional
@Service("postgresRolePermissionLink")
public class RolePermissionLinkPostgresService implements RolePermissionLinkService {
   private final Logger logger = LoggerFactory.getLogger(this.getClass());

   @Autowired
   RolePermissionLinkMapper mapper;

   @Override
   public int bindRolePermission(long roleId, long permissionId) {
      try {
         return mapper.bindRolePermission(roleId, permissionId);
      } catch (Exception ex) {
         String errorMessage = "Failed to bind an role and a permission.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int unbindRolePermission(long roleId, long permissionId) {
      try {
         return mapper.unbindRolePermission(roleId, permissionId);
      } catch (Exception ex) {
         String errorMessage = "Failed to unbind an role and a permission.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }
}