package com.wisdomsky.dmp.archive.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisdomsky.dmp.archive.mapper.MessageMapper;
import com.wisdomsky.dmp.archive.model.Message;
import com.wisdomsky.dmp.archive.model.MessageCreateParam;
import com.wisdomsky.dmp.archive.model.MessageQueryParam;
import com.wisdomsky.dmp.archive.service.MessageService;
import com.wisdomsky.dmp.library.exception.SQLExecutionException;
import com.wisdomsky.dmp.library.model.SortField;

@Transactional
@Service("postgresMessage")
public class MessagePostgresService implements MessageService {
   private final Logger logger = LoggerFactory.getLogger(this.getClass());

   @Autowired
   MessageMapper mapper;

   @Override
   public int create(MessageCreateParam param) {
      try {
         return mapper.create(param);
      } catch (Exception ex) {
         String errorMessage = "Failed to insert the message record.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int delete(MessageQueryParam param) {
      try {
         return mapper.delete(param);
      } catch (Exception ex) {
         String errorMessage = "Failed to delete the message record.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public long count(MessageQueryParam param) {
      try {
         return mapper.count(param);
      } catch (Exception ex) {
         String errorMessage = "Failed to count the message records.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public List<Message> retrieve(MessageQueryParam param, List<SortField> sortFieldList, Integer offset, Integer limit) {
      try {
         return mapper.retrieve(param, sortFieldList, offset, limit);
      } catch (Exception ex) {
         String errorMessage = "Failed to list the message records.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }

   @Override
   public int cleanup() {
      try {
         return mapper.cleanup();
      } catch (Exception ex) {
         String errorMessage = "Failed to cleanup the message records.";
         logger.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }
}