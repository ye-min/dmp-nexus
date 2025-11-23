package com.wisdomsky.dmp.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSON;
import com.wisdomsky.dmp.users.pojo.reqBaseData;
import com.wisdomsky.dmp.users.pojo.reqCRUDClass;
import com.wisdomsky.dmp.users.pojo.reqCRUDStudent;
import com.wisdomsky.dmp.users.pojo.reqCRUDTeacher;
import com.wisdomsky.dmp.users.pojo.reqQueryClass;
import com.wisdomsky.dmp.users.pojo.reqQueryStudent;
import com.wisdomsky.dmp.users.pojo.reqQueryTeacher;
import com.wisdomsky.dmp.users.pojo.reqUpdateUserPassword;
import com.wisdomsky.dmp.users.pojo.respT;
import com.wisdomsky.dmp.users.utils.UserUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/users")
@Tag(name = "用户数据相关接口")
@CrossOrigin
@Slf4j
public class UserDataController {
	@Autowired
	private UserUtils au;

	
	@Operation(summary = "2.1.6	班级列表")	
	@RequestMapping(value="/class/classList",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT doClassList(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") int userid ,
			@RequestHeader(value="timestamp") long timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody reqQueryClass req

			)
	{
		log.info("[doClassList-recv] is {}.body is :{}","userid:"+ userid + ",sign:" +sign, JSON.toJSONString(req));
		respT resp = new respT();
		try {
			req.setUserid(userid);
			resp = au.getClassList(req);
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		finally {
			log.info("[doClassList-send] is {}", JSON.toJSONString(resp));
			return resp ;			
		}
		
	}
	@Operation(summary = "2.1.7	增加班级")	
	@RequestMapping(value="/class/addClass",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT doaddClass(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") int userid ,
			@RequestHeader(value="timestamp") long timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody reqCRUDClass req

			)
	{
		log.info("[doaddClass-recv] is {}.body is :{}","userid:"+ userid + ",sign:" +sign, JSON.toJSONString(req));
		respT resp = new respT();
		try {
			req.setUserid(userid);
			resp = au.addNewClass(req);
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		finally {
			log.info("[doaddClass-send] is {}", JSON.toJSONString(resp));
			return resp ;			
		}
		
	}
	@Operation(summary = "2.1.8	修改班级")	
	@RequestMapping(value="/class/editClass",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT doeditClass(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") int userid ,
			@RequestHeader(value="timestamp") long timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody reqCRUDClass req

			)
	{
		log.info("[doeditClass-recv] is {}.body is :{}","userid:"+ userid + ",sign:" +sign, JSON.toJSONString(req));
		respT resp = new respT();
		try {
			req.setUserid(userid);
			resp = au.updateClass(req);
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		finally {
			log.info("[doeditClass-send] is {}", JSON.toJSONString(resp));
			return resp ;			
		}
		
	}
	@Operation(summary = "2.1.9	删除班级")	
	@RequestMapping(value="/class/deleteClass",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT deleteClass(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") int userid ,
			@RequestHeader(value="timestamp") long timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody reqCRUDClass req

			)
	{
		log.info("[deleteClass-recv] is {}.body is :{}","userid:"+ userid + ",sign:" +sign, JSON.toJSONString(req));
		respT resp = new respT();
		try {
			req.setUserid(userid);
			resp = au.deleteClass(req);
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		finally {
			log.info("[deleteClass-send] is {}", JSON.toJSONString(resp));
			return resp ;			
		}
		
	}
	@Operation(summary = "2.1.10	学生列表")	
	@RequestMapping(value="/student/studentList",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT dostudentList(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") int userid ,
			@RequestHeader(value="timestamp") long timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody reqQueryStudent req

			)
	{
		log.info("[dostudentList-recv] is {}.body is :{}","userid:"+ userid + ",sign:" +sign, JSON.toJSONString(req));
		respT resp = new respT();
		try {
			req.setUserid(userid);
			resp = au.getStudentList(req);
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		finally {
			log.info("[dostudentList-send] is {}", JSON.toJSONString(resp));
			return resp ;			
		}
		
	}
	@Operation(summary = "2.1.11	增加学生")	
	@RequestMapping(value="/student/addStudent",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT doaddStudent(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") int userid ,
			@RequestHeader(value="timestamp") long timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody reqCRUDStudent req

			)
	{
		log.info("[doaddStudent-recv] is {}.body is :{}","userid:"+ userid + ",sign:" +sign, JSON.toJSONString(req));
		respT resp = new respT();
		try {
			req.setUserid(userid);
			resp = au.addNewStudent(req);
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		finally {
			log.info("[doaddStudent-send] is {}", JSON.toJSONString(resp));
			return resp ;			
		}
		
	}
	@Operation(summary = "2.1.12	修改学生")	
	@RequestMapping(value="/student/editStudent",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT doeditStudent(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") int userid ,
			@RequestHeader(value="timestamp") long timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody reqCRUDStudent req

			)
	{
		log.info("[doeditStudent-recv] is {}.body is :{}","userid:"+ userid + ",sign:" +sign, JSON.toJSONString(req));
		respT resp = new respT();
		try {
			req.setUserid(userid);
			resp = au.updateStudent(req);
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		finally {
			log.info("[doeditStudent-send] is {}", JSON.toJSONString(resp));
			return resp ;			
		}
		
	}
	@Operation(summary = "2.1.13	删除学生")	
	@RequestMapping(value="/student/deleteStudent",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT deleteStudent(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") int userid ,
			@RequestHeader(value="timestamp") long timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody reqCRUDStudent req

			)
	{
		log.info("[deleteStudent-recv] is {}.body is :{}","userid:"+ userid + ",sign:" +sign, JSON.toJSONString(req));
		respT resp = new respT();
		try {
			req.setUserid(userid);
			resp = au.deleteStudent(req);
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		finally {
			log.info("[deleteStudent-send] is {}", JSON.toJSONString(resp));
			return resp ;			
		}
		
	}
	@Operation(summary = "2.1.14	教师列表")	
	@RequestMapping(value="/teacher/teacherList",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT teacherList(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") int userid ,
			@RequestHeader(value="timestamp") long timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody reqQueryTeacher req

			)
	{
		log.info("[teacherList-recv] is {}.body is :{}","userid:"+ userid + ",sign:" +sign, JSON.toJSONString(req));
		respT resp = new respT();
		try {
			req.setUserid(userid);
			resp = au.getTeacherList(req);
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		finally {
			log.info("[teacherList-send] is {}", JSON.toJSONString(resp));
			return resp ;			
		}
		
	}
	@Operation(summary = "2.1.11	增加教师")	
	@RequestMapping(value="/teacher/addTeacher",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT addTeacher(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") int userid ,
			@RequestHeader(value="timestamp") long timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody reqCRUDTeacher req

			)
	{
		log.info("[addTeacher-recv] is {}.body is :{}","userid:"+ userid + ",sign:" +sign, JSON.toJSONString(req));
		respT resp = new respT();
		try {
			req.setUserid(userid);
			resp = au.addNewTeacher(req);
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		finally {
			log.info("[addTeacher-send] is {}", JSON.toJSONString(resp));
			return resp ;			
		}
		
	}
	@Operation(summary = "2.1.12	修改教师")	
	@RequestMapping(value="/teacher/editTeacher",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT editTeacher(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") int userid ,
			@RequestHeader(value="timestamp") long timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody reqCRUDTeacher req

			)
	{
		log.info("[editTeacher-recv] is {}.body is :{}","userid:"+ userid + ",sign:" +sign, JSON.toJSONString(req));
		respT resp = new respT();
		try {
			req.setUserid(userid);
			resp = au.updateTeacher(req);
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		finally {
			log.info("[editTeacher-send] is {}", JSON.toJSONString(resp));
			return resp ;			
		}
		
	}
	@Operation(summary = "2.1.17	删除教师")	
	@RequestMapping(value="/teacher/deleteTeacher",method=RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public respT deleteTeacher(
			@RequestHeader(value="appID") String appid ,
			@RequestHeader(value="userID") int userid ,
			@RequestHeader(value="timestamp") long timestamp ,
			@RequestHeader(value="clientID") String clientid ,
			@RequestHeader(value="Sign") String sign ,
			@RequestBody reqCRUDTeacher req

			)
	{
		log.info("[deleteTeacher-recv] is {}.body is :{}","userid:"+ userid + ",sign:" +sign, JSON.toJSONString(req));
		respT resp = new respT();
		try {
			req.setUserid(userid);
			resp = au.deleteTeacher(req);
		}catch(Exception e) {
			resp.setErrcode(-70002);
			resp.setErrmsg("System error");
			e.printStackTrace();
		}
		finally {
			log.info("[deleteTeacher-send] is {}", JSON.toJSONString(resp));
			return resp ;			
		}
		
	}

}
