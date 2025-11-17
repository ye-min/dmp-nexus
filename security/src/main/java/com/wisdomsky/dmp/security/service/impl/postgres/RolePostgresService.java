package com.wisdomsky.dmp.security.service.impl.postgres;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisdomsky.dmp.library.exception.SQLExecutionException;
import com.wisdomsky.dmp.library.model.SortField;
import com.wisdomsky.dmp.security.mapper.RoleMapper;
import com.wisdomsky.dmp.security.model.Role;
import com.wisdomsky.dmp.security.model.RoleCreateParam;
import com.wisdomsky.dmp.security.model.RoleQueryParam;
import com.wisdomsky.dmp.security.model.RoleUpdateParam;
import com.wisdomsky.dmp.security.service.RoleService;

@Transactional
@Service("postgresRole")
public class RolePostgresService implements RoleService {
   private final Logger logger = LoggerFactory.getLogger(this.getClass());

   @Autowired
   RoleMapper mapper;

   @Override
   public int createRole(RoleCreateParam param) {
      try {
         return mapper.create(param);
      } catch (Exception ex) {
         String errorMessage = "Failed to insert the role record.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int updateRole(RoleUpdateParam param) {
      try {
         return mapper.update(param);
      } catch (Exception ex) {
         String errorMessage = "Failed to update the role record.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int deleteRole(RoleQueryParam param) {
      try {
         return mapper.delete(param);
      } catch (Exception ex) {
         String errorMessage = "Failed to delete the role records.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public long countRole(RoleQueryParam param) {
      try {
         return mapper.count(param);
      } catch (Exception ex) {
         String errorMessage = "Failed to count the role records.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int cleanupRole() {
      try {
         return mapper.cleanup();
      } catch (Exception ex) {
         String errorMessage = "Failed to cleanup the role records.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   /*@Override
   public Role detailRole(int id) {
      try {
         return mapper.retrieveDetail(id);
      } catch (Exception ex) {
         String errorMessage = "Failed to get the detail from the role records according to the id.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }*/
   
   /*@Override
   public List<Role> listRole(RoleQueryParam param, List<SortField> sortFieldList) {
      try {
         return mapper.retrieveList(param, sortFieldList);
      } catch (Exception ex) {
         String errorMessage = "Failed to list the role records.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }*/

   @Override
   public List<Role> retrieveRole(RoleQueryParam param, List<SortField> sortFieldList, Integer offset, Integer limit) {
      try {
         return mapper.retrieve(param, sortFieldList, offset, limit);
      } catch (Exception ex) {
         String errorMessage = "Failed to get the role records.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   /*@Override
   public List<Role> listByAccount(int id) {
      try {
         return mapper.retrieveListByAccount(id);
      } catch (Exception ex) {
         String errorMessage = "Failed to get the role records according to the binding account id.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }*/
}