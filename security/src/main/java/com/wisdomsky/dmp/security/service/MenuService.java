package com.wisdomsky.dmp.security.service;

import java.util.List;

import com.wisdomsky.dmp.library.model.SortField;
import com.wisdomsky.dmp.security.model.Menu;
import com.wisdomsky.dmp.security.model.MenuCreateParam;
import com.wisdomsky.dmp.security.model.MenuQueryParam;
import com.wisdomsky.dmp.security.model.MenuUpdateParam;

public interface MenuService {
   int createMenu(MenuCreateParam param);
   int updateMenu(MenuUpdateParam param);
   int deleteMenu(MenuQueryParam param);
   long countMenu(MenuQueryParam param);
   List<Menu> retrieveMenu(MenuQueryParam param, List<SortField> sortFieldList, Integer offset, Integer limit);
   int cleanupMenu();

   List<Menu> buildMenuTree(List<Menu> flatList);
   // List<Menu> retrieveTree();
   // List<Menu> retrieveTreeById(int id);
   // List<Menu> retrieveTreeByCode(String code);

}
