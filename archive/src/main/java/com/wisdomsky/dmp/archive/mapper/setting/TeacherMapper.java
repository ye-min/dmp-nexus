package com.wisdomsky.dmp.archive.mapper.setting;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wisdomsky.dmp.archive.pojo.setting.Teacher;

public interface TeacherMapper {

   @Select("SELECT id, code, name, phone_number, email, brief, status FROM dmp_teacher WHERE id = #{teacherId}")
   @Results({
         @Result(property = "id", column = "id"),
         @Result(property = "code", column = "code"),
         @Result(property = "name", column = "name"),
         @Result(property = "phoneNumber", column = "phone_number"),
         @Result(property = "email", column = "email"),
         @Result(property = "brief", column = "brief"),
         @Result(property = "status", column = "status")
   })
   Teacher findTeacherById(@Param("teacherId") int teacherId);

   @Select(" select "
         + " t.id "
         + ",t.code "
         + ",t.name "
         + ",t.phone_number "
         + ",t.email "
         + ",t.brief "
         + ",t.status "
         + " from dmp_teacher t "
         + " order by t.code ")
   @Results({
         @Result(property = "id", column = "id"),
         @Result(property = "code", column = "code"),
         @Result(property = "name", column = "name"),
         @Result(property = "phoneNumber", column = "phone_number"),
         @Result(property = "email", column = "email"),
         @Result(property = "brief", column = "brief"),
         @Result(property = "status", column = "status"),
   })
   List<Teacher> findList();

   @Insert("insert into dmp_teacher (code, name, phone_number, email, brief, status) " +
         "values (#{teacher.code}, #{teacher.name}, #{teacher.phoneNumber}, " +
         "#{teacher.email}, #{teacher.brief}, #{teacher.status})")
   @Options(useGeneratedKeys = true, keyProperty = "teacher.id")
   int insertTeacher(@Param("teacher") Teacher teacher);

   @Update("UPDATE dmp_teacher SET " +
         "code = #{teacher.code}, " +
         "name = #{teacher.name}, " +
         "phone_number = #{teacher.phoneNumber}, " +
         "email = #{teacher.email}, " +
         "brief = #{teacher.brief}, " +
         "status = #{teacher.status} " +
         "WHERE id = #{teacher.id}")
   int updateTeacher(@Param("teacher") Teacher teacher);

   @Delete("DELETE FROM dmp_teacher WHERE id = #{teacherId}")
   int deleteTeacherById(@Param("teacherId") int teacherId);
}
