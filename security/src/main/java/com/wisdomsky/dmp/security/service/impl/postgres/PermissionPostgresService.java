package com.wisdomsky.dmp.security.service.impl.postgres;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisdomsky.dmp.library.exception.SQLExecutionException;
import com.wisdomsky.dmp.security.mapper.PermissionMapper;
import com.wisdomsky.dmp.library.model.SortField;
import com.wisdomsky.dmp.security.model.Permission;
import com.wisdomsky.dmp.security.model.PermissionCreateParam;
import com.wisdomsky.dmp.security.model.PermissionQueryParam;
import com.wisdomsky.dmp.security.model.PermissionUpdateParam;
import com.wisdomsky.dmp.security.service.PermissionService;

@Transactional
@Service("postgresPermission")
public class PermissionPostgresService implements PermissionService {
   private final Logger logger = LoggerFactory.getLogger(this.getClass());

   @Autowired
   PermissionMapper mapper;

   @Override
   public int createPermission(PermissionCreateParam param) {
      try {
         return mapper.create(param);
      } catch (Exception ex) {
         String errorMessage = "Failed to insert the permission record.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int updatePermission(PermissionUpdateParam param) {
      try {
         return mapper.update(param);
      } catch (Exception ex) {
         String errorMessage = "Failed to update the permission record.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int deletePermission(PermissionQueryParam param) {
      try {
         return mapper.delete(param);
      } catch (Exception ex) {
         String errorMessage = "Failed to delete the permission record.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public long countPermission(PermissionQueryParam param) {
      try {
         return mapper.count(param);
      } catch (Exception ex) {
         String errorMessage = "Failed to count the permission records.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public List<Permission> retrievePermission(PermissionQueryParam param, List<SortField> sortFieldList, Integer offset, Integer limit) {
      try {
         return mapper.retrieve(param, sortFieldList, offset, limit);
      } catch (Exception ex) {
         String errorMessage = "Failed to list the permission records.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int cleanupPermission() {
      try {
         return mapper.cleanup();
      } catch (Exception ex) {
         String errorMessage = "Failed to cleanup the permission records.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   /*@Override
   public List<Permission> listByRole(int id) {
      try {
         return mapper.retrieveListByRole(id);
      } catch (Exception ex) {
         String errorMessage = "Failed to get the permission records according to the binding account id.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }*/

   
   
   /*@Override
   public List<Permission> retrieveTree() {
      try {
         PermissionQueryParam param = new PermissionQueryParam(null, null, null, null, null);
         List<Permission> list = mapper.retrieveList(param);
         return buildTree(list);
      } catch (Exception ex) {
         String errorMessage = "Failed to retrieve the permission tree.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public List<Permission> retrieveTreeById(int id) {
      try {
         PermissionQueryParam param = new PermissionQueryParam(id, null, null, null, null);
         List<Permission> list = mapper.retrieveList(param);
         return buildTree(list);
      } catch (Exception ex) {
         String errorMessage = "Failed to retrieve the permission tree according to the id.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public List<Permission> retrieveTreeByCode(String code) {
      try {
         PermissionQueryParam param = new PermissionQueryParam(null, code, null, null, null);
         List<Permission> list = mapper.retrieveList(param);
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