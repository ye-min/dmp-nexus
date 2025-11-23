package com.wisdomsky.dmp.archive.mapper.data;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wisdomsky.dmp.archive.pojo.data.DataSet;

public interface DataSetMapper {
   @Select(" select count(*) from wis_dataset ")
   long count();

   @Select(" select id, upload_time, name, content, size, comment from wis_dataset where id = #{id}")
   @Results({
         @Result(property = "id", column = "id"),
         @Result(property = "uploadTime", column = "upload_time"),
         @Result(property = "name", column = "name"),
         @Result(property = "content", column = "content"),
         @Result(property = "size", column = "size"),
         @Result(property = "comment", column = "comment"),
   })
   DataSet findDetail(@Param("id") int id);

   @Select(" select id, upload_time, name, '' as content, size, comment from wis_dataset order by upload_time desc")
   @Results({
         @Result(property = "id", column = "id"),
         @Result(property = "uploadTime", column = "upload_time"),
         @Result(property = "name", column = "name"),
         @Result(property = "content", column = "content"),
         @Result(property = "size", column = "size"),
         @Result(property = "comment", column = "comment"),
   })
   List<DataSet> findList();

   @Select(" select id, upload_time, name, '' as content, size, comment from wis_dataset order by upload_time desc " +
           " limit #{count} offset #{offset}")
   @Results({
         @Result(property = "id", column = "id"),
         @Result(property = "uploadTime", column = "upload_time"),
         @Result(property = "name", column = "name"),
         @Result(property = "content", column = "content"),
         @Result(property = "size", column = "size"),
         @Result(property = "comment", column = "comment"),
   })
   List<DataSet> findPageList(@Param("count") int count, @Param("offset") int offset);

   @Insert(" insert into wis_dataset (upload_time, name, content, size, comment) " +
           " values (#{record.uploadTime}, #{record.name}, #{record.content}, #{record.size}, '')")
   @Options(useGeneratedKeys = true, keyProperty = "record.id")
   int insert(@Param("record") DataSet record);

   @Delete(" delete from wis_dataset where id = #{id}")
   int delete(@Param("id") int id);

   @Update(" update wis_dataset set comment = #{comment} where id = #{id}")
   int update(@Param("id") int id, @Param("comment") String comment);
}
