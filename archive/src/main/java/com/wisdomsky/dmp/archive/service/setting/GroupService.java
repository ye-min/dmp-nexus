package com.wisdomsky.dmp.archive.service.setting;

import java.util.List;

import com.wisdomsky.dmp.archive.pojo.setting.Group;

public interface GroupService {
   List<Group> retrieveListByTeacher(int teacherId);
}
