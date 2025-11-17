package com.wisdomsky.dmp.security.service.impl.postgres;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisdomsky.dmp.library.exception.SQLExecutionException;
import com.wisdomsky.dmp.security.mapper.MenuMapper;
import com.wisdomsky.dmp.library.model.SortField;
import com.wisdomsky.dmp.security.model.Menu;
import com.wisdomsky.dmp.security.model.MenuCreateParam;
import com.wisdomsky.dmp.security.model.MenuQueryParam;
import com.wisdomsky.dmp.security.model.MenuUpdateParam;
import com.wisdomsky.dmp.security.service.MenuService;

@Transactional
@Service("postgresMenu")
public class MenuPostgresService implements MenuService {
   private final Logger logger = LoggerFactory.getLogger(this.getClass());

   @Autowired
   MenuMapper mapper;

   @Override
   public int createMenu(MenuCreateParam param) {
      try {
         return mapper.create(param);
      } catch (Exception ex) {
         String errorMessage = "Failed to insert the menu record.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int updateMenu(MenuUpdateParam param) {
      try {
         return mapper.update(param);
      } catch (Exception ex) {
         String errorMessage = "Failed to update the menu record.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int deleteMenu(MenuQueryParam param) {
      try {
         return mapper.delete(param);
      } catch (Exception ex) {
         String errorMessage = "Failed to delete the menu record.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public long countMenu(MenuQueryParam param) {
      try {
         return mapper.count(param);
      } catch (Exception ex) {
         String errorMessage = "Failed to count the menu records.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public List<Menu> retrieveMenu(MenuQueryParam param, List<SortField> sortFieldList, Integer offset, Integer limit) {
      try {
         return mapper.retrieve(param, sortFieldList, offset, limit);
      } catch (Exception ex) {
         String errorMessage = "Failed to list the menu records.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int cleanupMenu() {
      try {
         return mapper.cleanup();
      } catch (Exception ex) {
         String errorMessage = "Failed to cleanup the menu records.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public List<Menu> buildMenuTree(List<Menu> menuList) {
      List<Menu> treeList = new ArrayList<>();
      Map<Long, Menu> menuMap = new HashMap<>();

      // 将所有菜单放入map中
      for (Menu menu : menuList) {
         menuMap.put(menu.getId(), menu);
         menu.setChildren(new ArrayList<>());
      }

      // 构建树形结构
      for (Menu menu : menuList) {
         long parentId = menu.getParentId();
         if (parentId == 0) {
            // 顶层菜单
            treeList.add(menu);
         } else {
            // 将当前菜单添加到父菜单的children列表中
            Menu parentMenu = menuMap.get(parentId);
            if (parentMenu != null) {
               parentMenu.getChildren().add(menu);
            }
         }
      }

      // 可选：对每层菜单按sequence排序
      sortMenuTree(treeList);

      return treeList;
   }

   // 递归排序
   private void sortMenuTree(List<Menu> menuList) {
      // 按sequence排序
      menuList.sort(Comparator.comparingInt(Menu::getSequence));

      // 递归排序子菜单
      for (Menu menu : menuList) {
         if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
            sortMenuTree(menu.getChildren());
         }
      }
   }

   /*
    * public List<Menu> buildTree(List<Menu> flatList) {
    * List<Menu> tree = new ArrayList<>();
    * 
    * // Create a mapping of id to Menu for efficient parent lookup
    * // Assuming the id field uniquely identifies each Menu
    * // Adjust accordingly if this is not the case
    * // Alternatively, you can use a Map<Integer, Menu> for faster lookup
    * Map<Long, Menu> idToMenuMap = new HashMap<>();
    * for (Menu menu : flatList) {
    * idToMenuMap.put(menu.getId(), menu);
    * }
    * 
    * // Populate the tree
    * for (Menu menu : flatList) {
    * if (menu.getParentId() == 0) {
    * tree.add(buildTreeRecursive(menu, idToMenuMap));
    * }
    * }
    * 
    * return tree;
    * }
    * 
    * private Menu buildTreeRecursive(Menu parent, Map<Long, Menu> idToMenuMap) {
    * List<Menu> children = new ArrayList<>();
    * 
    * for (Menu item : idToMenuMap.values()) {
    * if (item.getParentId() == parent.getId()) {
    * Menu child = buildTreeRecursive(item, idToMenuMap);
    * children.add(child);
    * }
    * }
    * 
    * parent.setChildren(children);
    * return parent;
    * }
    */

   /*
    * @Override
    * public List<Menu> retrieveTree() {
    * try {
    * MenuQueryParam param = new MenuQueryParam(null, null, null, null, null);
    * List<Menu> list = mapper.retrieveList(param);
    * return buildTree(list);
    * } catch (Exception ex) {
    * String errorMessage = "Failed to retrieve the menu tree.";
    * logger.error(errorMessage, ex);
    * throw new SQLExecutionException(errorMessage);
    * }
    * }
    * 
    * @Override
    * public List<Menu> retrieveTreeById(int id) {
    * try {
    * MenuQueryParam param = new MenuQueryParam(id, null, null, null, null);
    * List<Menu> list = mapper.retrieveList(param);
    * return buildTree(list);
    * } catch (Exception ex) {
    * String errorMessage =
    * "Failed to retrieve the menu tree according to the id.";
    * logger.error(errorMessage, ex);
    * throw new SQLExecutionException(errorMessage);
    * }
    * }
    * 
    * @Override
    * public List<Menu> retrieveTreeByCode(String code) {
    * try {
    * MenuQueryParam param = new MenuQueryParam(null, code, null, null,
    * null);
    * List<Menu> list = mapper.retrieveList(param);
    * return buildTree(list);
    * } catch (Exception ex) {
    * String errorMessage =
    * "Failed to retrieve the menu tree according to the code.";
    * logger.error(errorMessage, ex);
    * throw new SQLExecutionException(errorMessage);
    * }
    * }
    * 
    * private List<Menu> buildTree(List<Menu> flatList) {
    * List<Menu> tree = new ArrayList<>();
    * Map<Integer, Menu> idToMenuMap = new HashMap<>();
    * 
    * for (Menu menu : flatList) {
    * idToMenuMap.put(menu.getId(), menu);
    * }
    * 
    * for (Menu menu : flatList) {
    * if (menu.getPid() == 0) {
    * tree.add(buildTreeRecursive(menu, idToMenuMap));
    * }
    * }
    * 
    * return tree;
    * }
    * 
    * private Menu buildTreeRecursive(Menu parent, Map<Integer, Menu> idToMenuMap)
    * {
    * List<Menu> children = new ArrayList<>();
    * 
    * for (Menu menu : idToMenuMap.values()) {
    * if (menu.getPid() == parent.getId()) {
    * Menu child = buildTreeRecursive(menu, idToMenuMap);
    * children.add(child);
    * }
    * }
    * 
    * parent.setChildren(children);
    * return parent;
    * }
    */
}