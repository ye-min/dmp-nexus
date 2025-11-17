package com.wisdomsky.dmp.archive.service;

import java.util.List;

import com.wisdomsky.dmp.archive.model.Message;
import com.wisdomsky.dmp.archive.model.MessageCreateParam;
import com.wisdomsky.dmp.archive.model.MessageQueryParam;
import com.wisdomsky.dmp.library.model.SortField;

public interface MessageService {
   int create(MessageCreateParam param);
   int delete(MessageQueryParam param);
   long count(MessageQueryParam param);
   List<Message> retrieve(MessageQueryParam param, List<SortField> sortFieldList, Integer offset, Integer limit);
   int cleanup();
}
