package com.wisdomsky.dmp.library.model;

import java.util.List;

public class PageRequest<T> {
   private T query;
   private List<SortField> sort;
   private PageParam page;

   public T getQuery() {
      return query;
   }

   public void setQuery(T query) {
      this.query = query;
   }

   public List<SortField> getSort() {
      return sort;
   }

   public void setSortF(List<SortField> sort) {
      this.sort = sort;
   }
   
   public PageParam getPage() {
      return page;
   }

   public void setPage(PageParam page) {
      this.page = page;
   }
}
