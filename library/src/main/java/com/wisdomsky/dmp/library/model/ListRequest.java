package com.wisdomsky.dmp.library.model;

import java.util.List;

public class ListRequest<T> {
   private T query;
   private List<SortField> sort;

   public T getQuery() {
      return query;
   }

   public void setQuery(T query) {
      this.query = query;
   }

   public List<SortField> getSort() {
      return sort;
   }

   public void setSort(List<SortField> sort) {
      this.sort = sort;
   }
}
