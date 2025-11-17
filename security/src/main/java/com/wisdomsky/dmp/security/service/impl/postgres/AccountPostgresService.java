package com.wisdomsky.dmp.security.service.impl.postgres;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisdomsky.dmp.library.exception.SQLExecutionException;
import com.wisdomsky.dmp.security.mapper.AccountMapper;
import com.wisdomsky.dmp.library.model.SortField;
import com.wisdomsky.dmp.security.model.Account;
import com.wisdomsky.dmp.security.model.AccountCreateParam;
import com.wisdomsky.dmp.security.model.AccountQueryParam;
import com.wisdomsky.dmp.security.model.AccountUpdateParam;
import com.wisdomsky.dmp.security.service.AccountService;

@Transactional
@Service("postgresAccount")
public class AccountPostgresService implements AccountService {
   private final Logger logger = LoggerFactory.getLogger(this.getClass());

   @Autowired
   AccountMapper mapper;

   @Override
   public int createAccount(AccountCreateParam param) {
      try {
         return mapper.create(param);
      } catch (Exception ex) {
         String errorMessage = "Failed to insert the account record.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int updateAccount(AccountUpdateParam param) {
      try {
         return mapper.update(param);
      } catch (Exception ex) {
         String errorMessage = "Failed to update the account record.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int deleteAccount(AccountQueryParam param) {
      try {
         return mapper.delete(param);
      } catch (Exception ex) {
         String errorMessage = "Failed to delete the account record.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public long countAccount(AccountQueryParam param) {
      try {
         return mapper.count(param);
      } catch (Exception ex) {
         String errorMessage = "Failed to count the account records.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public List<Account> retrieveAccount(AccountQueryParam param, List<SortField> sortFieldList, Integer offset, Integer limit) {
      try {
         return mapper.retrieve(param, sortFieldList, offset, limit);
      } catch (Exception ex) {
         String errorMessage = "Failed to list the account records.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int cleanupAccount() {
      try {
         return mapper.cleanup();
      } catch (Exception ex) {
         String errorMessage = "Failed to cleanup the account records.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   /*@Override
   public List<Permission> retrievePermissionTreeById(int id) {
      try {
         PermissionQueryParam param = new PermissionQueryParam(id, null, null, null, null);
         List<Permission> list = permissionMapper.retrieveList(param);
         return buildTree(list);
      } catch (Exception ex) {
         String errorMessage = "Failed to retrieve the permission tree according to the id.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public List<Permission> retrievePermissionTreeByCode(String code) {
      try {
         PermissionQueryParam param = new PermissionQueryParam(null, code, null, null, null);
         List<Permission> list = permissionMapper.retrieveList(param);
         return buildTree(list);
      } catch (Exception ex) {
         String errorMessage = "Failed to retrieve the permission tree according to the code.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public List<Permission> retrievePermissionListByCode(String code) {
      try {
         PermissionQueryParam param = new PermissionQueryParam(null, code, null, null, null);
         List<Permission> list = permissionMapper.retrieveList(param);
         return buildTree(list);
      } catch (Exception ex) {
         String errorMessage = "Failed to retrieve the permission tree according to the code.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   private List<Permission> buildTree(List<Permission> flatList) {
      List<Permission> tree = new ArrayList<>();
      Map<Integer, Permission> idToPermissionMap = new HashMap<>();

      for (Permission permission : flatList) {
         idToPermissionMap.put(permission.getId(), permission);
      }

      for (Permission permission : flatList) {
         if (permission.getPid() == 0) {
            tree.add(buildTreeRecursive(permission, idToPermissionMap));
         }
      }

      return tree;
   }

   private Permission buildTreeRecursive(Permission parent, Map<Integer, Permission> idToPermissionMap) {
      List<Permission> children = new ArrayList<>();

      for (Permission permission : idToPermissionMap.values()) {
         if (permission.getPid() == parent.getId()) {
            Permission child = buildTreeRecursive(permission, idToPermissionMap);
            children.add(child);
         }
      }

      parent.setChildren(children);
      return parent;
   }*/
}