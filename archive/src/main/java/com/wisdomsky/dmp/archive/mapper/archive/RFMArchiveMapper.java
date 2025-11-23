package com.wisdomsky.dmp.archive.mapper.archive;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.wisdomsky.dmp.archive.pojo.archive.RFMArchive;
import com.wisdomsky.dmp.archive.pojo.archive.StudentRFMArchive;

@Mapper
public interface RFMArchiveMapper {
   @Select(" select count(*) from wis_archive_rfm ")
   long count();

   @Select(" select count(*) from wis_archive_rfm a " +
           " where a.account_id = #{accountId} " +
           "   and ((#{startTime} <> '' and a.record_time >= to_timestamp(#{startTime}, 'YYYY/MM/DD HH24:MI:SS')) or (#{startTime} = ''))" +
           "   and ((#{endTime} <> '' and a.record_time <= to_timestamp(#{endTime}, 'YYYY/MM/DD HH24:MI:SS')) or (#{endTime} = ''))")
   long personalCount(
      @Param("accountId") int accountId,
      @Param("startTime") String startTime,
      @Param("endTime") String endTime);

   @Select({
      "<script> ",
      " select count(*)",
      " from wis_archive_rfm a",
      " left join wis_users u ON (u.uid = a.account_id)",
      " left join wis_student s ON (s.uid = a.account_id)",
      " left join wis_class c ON (s.class_id = c.class_id)",
      " where ((#{accountType} <![CDATA[ >= ]]> 0 and u.usertype = #{accountType}) or (#{accountType} <![CDATA[ < ]]> 0)) ",
      "   and s.class_id in ",
      "   <choose>",
      "       <when test='groupList != null and groupList.size() > 0'>",
      "           <foreach collection='groupList' open='(' item='id' separator=',' close=')'>",
      "               #{id}",
      "           </foreach>",
      "       </when>",
      "       <otherwise>",
      "           (0) ",
      "       </otherwise>",
      "   </choose>",
      "   and ((#{studentId} <![CDATA[ >= ]]> 0 and s.uid = #{studentId}) or (#{studentId} <![CDATA[ < ]]> 0))",
      "   and ((#{startTime} <![CDATA[ <> ]]> '' and a.record_time <![CDATA[ >= ]]> to_timestamp(#{startTime}, 'YYYY/MM/DD HH24:MI:SS')) or (#{startTime} = ''))",
      "   and ((#{endTime} <![CDATA[ <> ]]> '' and a.record_time <![CDATA[ <= ]]> to_timestamp(#{endTime}, 'YYYY/MM/DD HH24:MI:SS')) or (#{endTime} = ''))",
      "</script>"
   })
   long studentCount(
      @Param("accountType") int accountType,
      @Param("groupList") List<Integer> groupList,
      @Param("studentId") int studentId,
      @Param("startTime") String startTime,
      @Param("endTime") String endTime);

   @Select(" select a.id, a.record_time, a.account_id, u.realname, u.usertype, " +
           " a.result_dataset, a.result_field_list, a.parameter " +
           " from wis_archive_rfm a " +
           " left join wis_users u on (u.uid = a.account_id) " +
           " where id = #{id}")
   @Results({
      @Result(property = "id", column = "id"),
      @Result(property = "recordTime", column = "record_time"),
      @Result(property = "accountId", column = "account_id"),
      @Result(property = "accountName", column = "realname"),
      @Result(property = "accountType", column = "usertype"),
      @Result(property = "resultDataSet", column = "result_dataset"),
      @Result(property = "resultFieldList", column = "result_field_list"),
      @Result(property = "parameter", column = "parameter"),
   })
   RFMArchive findDetail(@Param("id") int id);

   @Select(" select a.id, a.record_time, a.account_id, u.realname, u.usertype, " +
           " '' as result_dataset, a.result_field_list, a.parameter " +
           " from wis_archive_rfm a " +
           " left join wis_users u on (u.uid = a.account_id) " +
           " order by a.record_time desc ")
   @Results({
      @Result(property = "id", column = "id"),
      @Result(property = "recordTime", column = "record_time"),
      @Result(property = "accountId", column = "account_id"),
      @Result(property = "accountName", column = "realname"),
      @Result(property = "accountType", column = "usertype"),
      @Result(property = "resultDataSet", column = "result_dataset"),
      @Result(property = "resultFieldList", column = "result_field_list"),
      @Result(property = "parameter", column = "parameter"),
   })
   List<RFMArchive> findList();

   @Select({
      "<script> ",
      "select a.id, a.record_time, c.class_id, c.class_name, a.account_id, u.realname, u.usertype,",
      " '' as result_dataset, a.result_field_list, a.parameter",
      " from wis_archive_rfm a",
      " left join wis_users u ON (u.uid = a.account_id)",
      " left join wis_student s ON (s.uid = a.account_id)",
      " left join wis_class c ON (s.class_id = c.class_id)",
      " where ((#{accountType} <![CDATA[ >= ]]> 0 and u.usertype = #{accountType}) or (#{accountType} <![CDATA[ < ]]> 0)) ",
      "   and s.class_id in ",
      "   <choose>",
      "       <when test='groupList != null and groupList.size() > 0'>",
      "           <foreach collection='groupList' open='(' item='id' separator=',' close=')'>",
      "               #{id}",
      "           </foreach>",
      "       </when>",
      "       <otherwise>",
      "           (0) ",
      "       </otherwise>",
      "   </choose>",
      "   and ((#{studentId} <![CDATA[ >= ]]> 0 and s.uid = #{studentId}) or (#{studentId} <![CDATA[ < ]]> 0))",
      "   and ((#{startTime} <![CDATA[ <> ]]> '' and a.record_time <![CDATA[ >= ]]> to_timestamp(#{startTime}, 'YYYY/MM/DD HH24:MI:SS')) or (#{startTime} = ''))",
      "   and ((#{endTime} <![CDATA[ <> ]]> '' and a.record_time <![CDATA[ <= ]]> to_timestamp(#{endTime}, 'YYYY/MM/DD HH24:MI:SS')) or (#{endTime} = ''))",
      " order by a.record_time desc ",
      " limit #{count} offset #{offset}",
      "</script>"
  })
  @Results({
      @Result(property = "id", column = "id"),
      @Result(property = "recordTime", column = "record_time"),
      @Result(property = "groupId", column = "class_id"),
      @Result(property = "groupName", column = "class_name"),
      @Result(property = "accountId", column = "account_id"),
      @Result(property = "accountName", column = "realname"),
      @Result(property = "accountType", column = "usertype"),
      @Result(property = "resultDataSet", column = "result_dataset"),
      @Result(property = "resultFieldList", column = "result_field_list"),
      @Result(property = "parameter", column = "parameter"),
  })
  List<StudentRFMArchive> findStudentPageList(
      @Param("count") int count,
      @Param("offset") int offset,
      @Param("accountType") int accountType,
      @Param("groupList") List<Integer> groupList,
      @Param("studentId") int studentId,
      @Param("startTime") String startTime,
      @Param("endTime") String endTime);

   @Select(" select a.id, a.record_time, a.account_id, u.realname, u.usertype, " +
           " '' as result_dataset, a.result_field_list, a.parameter " +
           " from wis_archive_rfm a " +
           " left join wis_users u on (u.uid = a.account_id) " +
           " where a.account_id = #{accountId} " +
           "   and ((#{startTime} <> '' and a.record_time >= to_timestamp(#{startTime}, 'YYYY/MM/DD HH24:MI:SS')) or (#{startTime} = ''))" +
           "   and ((#{endTime} <> '' and a.record_time <= to_timestamp(#{endTime}, 'YYYY/MM/DD HH24:MI:SS')) or (#{endTime} = ''))" +
           " order by a.record_time desc " +
           " limit #{count} offset #{offset}")
   @Results({
      @Result(property = "id", column = "id"),
      @Result(property = "recordTime", column = "record_time"),
      @Result(property = "accountId", column = "account_id"),
      @Result(property = "accountName", column = "realname"),
      @Result(property = "accountType", column = "usertype"),
      @Result(property = "resultDataSet", column = "result_dataset"),
      @Result(property = "resultFieldList", column = "result_field_list"),
      @Result(property = "parameter", column = "parameter"),
   })
   List<RFMArchive> findPersonalList(
         @Param("count") int count,
         @Param("offset") int offset,
         @Param("accountId") int accountId,
         @Param("startTime") String startTime,
         @Param("endTime") String endTime);

   @Insert(" insert into wis_archive_rfm (record_time, account_id, result_dataset, " +
           " result_field_list, parameter) " +
           " values (#{record.recordTime}, #{record.accountId}, #{record.resultDataSet}, " +
           " #{record.resultFieldList}, #{record.parameter})")
   @Options(useGeneratedKeys = true, keyProperty = "record.id")
   int insert(@Param("record") RFMArchive record);

   @Delete(" delete from wis_archive_rfm where id = #{id}")
   int delete(@Param("id") int id);
}
