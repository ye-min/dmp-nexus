package com.wisdomsky.dmp.archive.service.setting.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisdomsky.dmp.archive.exception.SQLExecutionException;
import com.wisdomsky.dmp.archive.mapper.setting.GroupMapper;
import com.wisdomsky.dmp.archive.pojo.setting.Group;
import com.wisdomsky.dmp.archive.service.setting.GroupService;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
public class GroupServiceImpl implements GroupService{
   @Autowired
   GroupMapper mapper;

   @Override
   public List<Group> retrieveListByTeacher(int teacherId) {
      try {
         return mapper.findListByTeacher(teacherId);
      } catch (Exception ex) {
         String errorMessage = "Failed to retrieve list data from group records.";
         log.error(errorMessage, ex);
         throw new SQLExecutionException(errorMessage);
      }
   }
}
