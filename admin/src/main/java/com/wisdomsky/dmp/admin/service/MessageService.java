package com.wisdomsky.dmp.admin.service;

import java.util.List;

import com.wisdomsky.dmp.admin.model.Message;
import com.wisdomsky.dmp.admin.model.MessageCreateParam;
import com.wisdomsky.dmp.admin.model.MessageQueryParam;
import com.wisdomsky.dmp.library.model.SortField;

public interface MessageService {
   int create(MessageCreateParam param);
   int delete(MessageQueryParam param);
   long count(MessageQueryParam param);
   List<Message> retrieve(MessageQueryParam param, List<SortField> sortFieldList, Integer offset, Integer limit);
   int cleanup();
}
