package com.wisdomsky.dmp.security.service.impl.postgres;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisdomsky.dmp.library.exception.SQLExecutionException;
import com.wisdomsky.dmp.security.mapper.PermissionMenuLinkMapper;
import com.wisdomsky.dmp.security.service.PermissionMenuLinkService;

@Transactional
@Service("postgresPermissionMenuLink")
public class PermissionMenuLinkPostgresService implements PermissionMenuLinkService {
   private final Logger logger = LoggerFactory.getLogger(this.getClass());

   @Autowired
   PermissionMenuLinkMapper mapper;

   @Override
   public int bindMenuPermission(long menuId, long permissionId) {
      try {
         return mapper.bindPermissionMenu(menuId, permissionId);
      } catch (Exception ex) {
         String errorMessage = "Failed to bind a permission and a menu.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int unbindMenuPermission(long menuId, long permissionId) {
      try {
         return mapper.unbindPermissionMenu(menuId, permissionId);
      } catch (Exception ex) {
         String errorMessage = "Failed to unbind a permission and a menu.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }
}