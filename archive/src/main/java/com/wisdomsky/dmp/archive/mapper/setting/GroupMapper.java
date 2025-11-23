package com.wisdomsky.dmp.archive.mapper.setting;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.wisdomsky.dmp.archive.pojo.setting.Group;

@Mapper
public interface GroupMapper {
    @Select ( " select "
           + " b.class_id as id "
           + ",b.class_code as code "
           + ",b.class_name as name "
           + ",b.state as status"
           + ",b.class_year as grade_year "
           + ",(select count(*) from wis_student s where s.class_id = b.class_id) as student_amount "
           + ",b.teacher_id as teacher_id"
           + ",t.teacher_name as teacher_name "
           + " from wis_class b "
           + " left join wis_teacher t on (t.uid = b.teacher_id) "
           + " where b.teacher_id = #{teacherId} "
           + " order by b.class_code")
    @Results({
         @Result(property = "id", column = "id"),
         @Result(property = "code", column = "code"),
         @Result(property = "name", column = "name"),
         @Result(property = "status", column = "status"),
         @Result(property = "gradeYear", column = "grade_year"),
         @Result(property = "studentAmount", column = "student_amount"),
         @Result(property = "teacherId", column = "teacher_id"),
         @Result(property = "teacherName", column = "teacher_name")
    })
    List<Group> findListByTeacher(@Param("teacherId") int teacherId);
}
