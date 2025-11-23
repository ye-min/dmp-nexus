package com.wisdomsky.dmp.users.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.util.DateUtils;
import com.wisdomsky.dmp.users.pojo.ClassItem;
import com.wisdomsky.dmp.users.pojo.LoginUserInfo;
import com.wisdomsky.dmp.users.pojo.MenuItem;
import com.wisdomsky.dmp.users.pojo.RoleItem;
import com.wisdomsky.dmp.users.pojo.StudentItem;
import com.wisdomsky.dmp.users.pojo.TeacherItem;
import com.wisdomsky.dmp.users.pojo.reqCRUDClass;
import com.wisdomsky.dmp.users.pojo.reqCRUDStudent;
import com.wisdomsky.dmp.users.pojo.reqCRUDTeacher;
import com.wisdomsky.dmp.users.pojo.reqQueryClass;
import com.wisdomsky.dmp.users.pojo.reqQueryStudent;
import com.wisdomsky.dmp.users.pojo.reqQueryTeacher;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class db_users {
	@Autowired
	private JdbcTemplate jdbc ;
	
	public LoginUserInfo getUserInfoByUserName(String username) {
		LoginUserInfo item = null;
		String sql_s = "select uid as userID,username, realname, usertype, passwd, roles as roles_str, isvalid "
				+ " from wis_users "
				+ " where username = ? and status>0   " ;
		try {
			List<LoginUserInfo> list = jdbc.query(sql_s, new Object[] {
					username
			}, new BeanPropertyRowMapper(LoginUserInfo.class)) ;
			if(list.size()>0) {
				item = list.get(0);
			}		
		}catch(Exception e) {
			log.error("getUserInfoByUserName exception is coming:{},{}",sql_s,e.toString());
	    	e.printStackTrace();
		}
		finally {
			return item ;			
		}
	}
	public LoginUserInfo getUserInfoByUserId(int uid) {
		LoginUserInfo item = null;
		String sql_s = "select uid as userID,username, realname, usertype, passwd, roles as roles_str, isvalid "
				+ " from wis_users "
				+ " where uid = ? and status>0   " ;
		try {
			List<LoginUserInfo> list = jdbc.query(sql_s, new Object[] {
					uid
			}, new BeanPropertyRowMapper(LoginUserInfo.class)) ;
			if(list.size()>0) {
				item = list.get(0);
			}		
		}catch(Exception e) {
			log.error("getUserInfoByUserName exception is coming:{},{}",sql_s,e.toString());
	    	e.printStackTrace();
		}
		finally {
			return item ;			
		}
	}
	public List<RoleItem> getRoleListByInRoleStr(String roles){
		List<RoleItem> list = null ;
		if(roles == null || "".equals(roles)) {
			list = new ArrayList<RoleItem>();
			return list ;
		}
		String sql_s = "select * "
				+ " from  wis_param_roles b"
				+ " where b.rid in ("+ roles+") and b.status>0  " ;
		
		try {
			list = jdbc.query(sql_s, new BeanPropertyRowMapper(RoleItem.class)) ;
		}catch(Exception e) {
			log.error("getRoleListByInRoleStr exception is coming:{},{}",sql_s,e.toString());
	    	e.printStackTrace();
		}
		finally {
			return list ;			
		}
		
	}
	public List<MenuItem> getMenuListByInRoleStr(String roles){
		List<MenuItem> list = null ;
		if(roles == null || "".equals(roles)) {
			list = new ArrayList<MenuItem>();
			return list ;
		}
		String sql_s = "select c.mid,mtitle,mdesc,murl,mpath,mtype,micon ,b.rid as mrole, c.parent_id ,mdispid "
				+ " from wis_param_menu c , wis_param_menu_role b  "
				+ " where   c.mid = b.mid and  b.rid in ("+ roles +") and b.status>0 and c.status>0 "
				+ " order by c.mpath  " ;
		
		try {
			list = jdbc.query(sql_s, new BeanPropertyRowMapper(MenuItem.class)) ;
		}catch(Exception e) {
			log.error("getMenuListByInRoleStr exception is coming:{},{}",sql_s,e.toString());
	    	e.printStackTrace();
		}
		finally {
			return list ;			
		}		
	}
	public int updateUserPasswd(int uid , String oldp ,String newp) {
		int ret = -1 ;
		String sql_s = "update wis_users set passwd = ? where uid = ? and status >0  " ;
		try {
			ret = jdbc.update(sql_s, new Object[] {newp ,uid }) ;
		}catch(Exception  e) {
			log.error("[updateUserPasswd] is exception :{},{}",sql_s ,e.toString());
			e.printStackTrace();
		}
		finally {
			return ret ;
		}		
	}
	
	public List<ClassItem> getClassList(reqQueryClass req){
		List<ClassItem> list = new ArrayList<ClassItem>() ;
		String where_s = "" ;
		if(req.getClass_id()>0) {
			where_s += " and class_id = " + req.getClass_id() ; 
		}
		if(req.getClass_year()>0) {
			where_s += " and class_year = " + req.getClass_year() ;
		}
		if(req.getClass_name() == null || req.getClass_name().trim().equals("")) {
			
		} else {
         where_s += " and class_name like '" + req.getClass_name().trim() + "%' ";
      }
      if (req.getState() >= 0) {
         where_s += " and state = " + req.getState();
      }
      if(req.getTeacher_id()>0) {
			where_s += " and teacher_id = " + req.getTeacher_id() ; 
		}
		String sql_s = "select * ,count(*) over () as total "
				+ " from view_class where (1=1) "
				+ where_s 
				+ " order by class_year desc, class_name "
				+ " limit ? offset ? ";
		try {
			list = jdbc.query(sql_s, new Object[] {
					req.getRows(),req.getRows()*(req.getPageid()>=1?req.getPageid()-1:0)
			}, new BeanPropertyRowMapper(ClassItem.class)) ;
		}catch(Exception e) {
			log.error("[getClassList] is exception :{},{}",sql_s ,e.getMessage());
			e.printStackTrace();			
		}
		finally {
			return list;
		}
	}
	
	public int InsertNewClass(reqCRUDClass req) {
      int ret = -1;
      String sql_s = " insert into wis_class(class_code,class_year,class_name,teacher_id,state,create_time) "
         + " values (?,?,?,?,?,now())" ;
      try {
         ret = jdbc.update(sql_s, new Object[] {
            req.getClass_code(),
            req.getClass_year(),
            req.getClass_name(),
            req.getTeacher_id(),
            req.getState()
         });
      } catch (DuplicateKeyException e) {
         log.error("[InsertNewClass] is exception :{},{}",sql_s ,e.getMessage());
         e.printStackTrace();
         ret = -2;
      } catch(Exception e) {
         log.error("[InsertNewClass] is exception :{},{}",sql_s ,e.getMessage());
         e.printStackTrace();
      }
      finally {
         return ret;
      }
   }

   public int UpdateClass(reqCRUDClass req) {
      int ret = -1;
      String sql_s = " update wis_class set "
            + " class_code=?,class_year=?,class_name=?,teacher_id=?,state=? "
            + " where class_id =? and status>0 ";
      try {
         ret = jdbc.update(sql_s, new Object[] {
               req.getClass_code(),
               req.getClass_year(),
               req.getClass_name(),
               req.getTeacher_id(),
               req.getState(),
               req.getClass_id()
         });
      } catch (DuplicateKeyException e) {
         log.error("[UpdateClass] is exception :{},{}", sql_s, e.getMessage());
         e.printStackTrace();
         ret = -2;
      } catch (Exception e) {
         log.error("[UpdateClass] is exception :{},{}", sql_s, e.getMessage());
         e.printStackTrace();
      } finally {
         return ret;
      }
   }

	public int DeleteClass(reqCRUDClass req) {
      int ret = -1;
		String sql_s = " update wis_class set "
				+ " status = -1, class_code = class_code || '~' || class_id, class_name = class_name || '~' || class_id "
				+ " where class_id = ? and status > 0 " ;
		try {
            ret = jdbc.update(sql_s, new Object[] {
				req.getClass_id()
			}) ;
		}catch(Exception e) {
			log.error("[DeleteClass] is exception :{},{}",sql_s ,e.getMessage());
			e.printStackTrace();						
		}
		finally {
			return ret ;
			
		}
	}
	
	public List<StudentItem> getStudentList(reqQueryStudent req){
		List<StudentItem> list = new ArrayList<StudentItem>() ;
		String where_s = "" ;
		if(req.getStudent_id()>0) {
			where_s += " and student_id = " + req.getStudent_id() ; 
		}
		if(req.getStudent_code() == null || req.getStudent_code().trim().equals("")) {
		}
		else {
			where_s += " and student_code like '" + req.getStudent_code() + "%' " ;
		}
		if(req.getClass_id() > 0) {
			where_s += " and class_id = " + req.getClass_id();
		}
		if(req.getStudent_name() == null || req.getStudent_name().trim().equals("")) {
			
      } else {
         where_s += " and student_name like '" + req.getStudent_name().trim() + "%' ";
      }
      if (req.getState() >= 0) {
         where_s += " and state = " + req.getState();
      }
      if(req.getTeacher_id()>0) {
			where_s += " and teacher_id = " + req.getTeacher_id() ; 
		}
		String sql_s = "select * , count(*) over () as total "
				+ "  from view_student where (1=1) "
				+ where_s 
				+ " order by class_id ,student_code "
				+ " limit ? offset ? ";
		try {
			list = jdbc.query(sql_s, new Object[] {
					req.getRows(),req.getRows()*(req.getPageid()>=1?req.getPageid()-1:0)
			}, new BeanPropertyRowMapper(StudentItem.class)) ;
		}catch(Exception e) {
			log.error("[getStudentList] is exception :{},{}",sql_s ,e.getMessage());
			e.printStackTrace();			
		}
		finally {
			return list;
		}
	}
   
   public int InsertNewStudent(reqCRUDStudent req) {
      int ret = -1 ;
      String sql_s = "insert into wis_users (username,realname,passwd,usertype,isvalid,roles,status) "
            + " values(?,?,?,?,?,?::jsonb,-99) ";
      try {
         GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

         PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement ps = con.prepareStatement(sql_s, new String[] { "uid" });
            ps.setString(1, req.getStudent_code());
            ps.setString(2, req.getStudent_name());
            ps.setString(3, req.getPasswd());
            ps.setInt(4, 2);
            ps.setInt(5, 1);
            ps.setString(6, "[]");
            return ps;
         };
         ret = jdbc.update(preparedStatementCreator, keyHolder);
         int uid = keyHolder.getKey().intValue();
         if (uid > 0) {
            String sql_student = "insert into wis_student(uid,student_name,student_code,phone,contact,e_mail,"
                  + "gender,birth,state,class_id,create_time) "
                  + " values(?,?,?,?,?,?,?,?::Date,?,?,now())";
            ret = jdbc.update(sql_student, new Object[] {
               uid, req.getStudent_name(), req.getStudent_code(),
               req.getPhone(), req.getContact(), req.getE_mail(),
               req.getGender(),
               DateUtils.format(req.getBirth(), "yyyy-MM-dd"),
               req.getState(), req.getClass_id()
            });
            if (ret > 0) {
               String sql_active = "update wis_users set status = 1 where uid = ? ";
               ret = jdbc.update(sql_active, new Object[] { uid });
            } else {
               String sql_failed = "delete from wis_users where uid=? and status = -99 ";
               ret = jdbc.update(sql_failed, new Object[] { uid });
            }
         }
         if (ret > 0) {
            ret = uid;
         }
      } catch (DuplicateKeyException e) {
         log.error("[InsertNewStudent] is exception :{},{}",sql_s ,e.getMessage());
         e.printStackTrace();
         ret = -2;
      } catch(Exception e) {
         log.error("[InsertNewStudent] is exception :{},{}",sql_s ,e.getMessage());
         e.printStackTrace();
      }
      finally {
         return ret;
      }
   }

	public int UpdateStudent(reqCRUDStudent req ,StudentItem src ) {
		int ret = -1 ;
		int upd_users = 1;
		int upd_data = 1;
		if(src.getStudent_name().equals(req.getStudent_name())) {
			upd_users = -1 ;
			ret = -2 ;
		}
		String sql_s1 = "update wis_users set realname=? where uid = ? and usertype = 2 " ;
		String sql_s2 = "update wis_student set student_name=?,phone=?,contact=?,e_mail=?,"
				+ " gender=?,birth=?::Date, state=?,class_id=? "
				+ " where uid=? and status>0 " ;
		try {
			if(upd_users == 1) {
				ret = jdbc.update(sql_s1, new Object[] {
					req.getStudent_name(), req.getStudent_id()	
				}) ;
			}
			if(ret>0 || (ret == -2 && upd_users == -1)){
				ret = jdbc.update(sql_s2, new Object[] {
						req.getStudent_name(),req.getPhone(),req.getContact(),
						req.getE_mail(),req.getGender(),DateUtils.format(req.getBirth(),"yyyy-MM-dd"),
						req.getState(),req.getClass_id(),
						req.getStudent_id()
				}) ;
			}
		}catch(Exception e) {
	    	log.error("[UpdateStudent] is exception :{},{}",sql_s1+";"+sql_s2 ,e.getMessage());
	    	e.printStackTrace();
			
		}
		finally {
			return ret ;
		}
	}
	public int DeleteStudent(reqCRUDStudent req  ) {
       int ret = -1;
		int upd_users = 1;
		int upd_data = 1;
		String sql_s1 = "update wis_users set status = -1, username= username || '~' || uid "
				+ " where uid=? and status>0 " ;
		try {
			ret = jdbc.update(sql_s1, new Object[] {
					req.getStudent_id() 
			});
		}catch(Exception e) {
	    	log.error("[DeleteStudent] is exception :{},{}",sql_s1 ,e.getMessage());
	    	e.printStackTrace();
		}
		finally {
			return ret ;
		}
	}
	public List<TeacherItem> getTeacherList(reqQueryTeacher req){
		List<TeacherItem> list = new ArrayList<TeacherItem>() ;
		String where_s = "" ;
		if(req.getTeacher_id()>0) {
			where_s += " and teacher_id = " + req.getTeacher_id() ; 
		}
		if(req.getTeacher_name() == null || req.getTeacher_name().trim().equals("")) {
			
		} else {
         where_s += " and teacher_name like '" + req.getTeacher_name().trim() + "%' ";
      }
      if(req.getState() >= 0) {
			where_s += " and state = " + req.getState() ; 
		}
		String sql_s = "select * , count(*) over () as total "
				+ "  from view_teacher where (1=1) "
				+ where_s 
				+ " order by teacher_name "
				+ " limit ? offset ? ";
		try {
			list = jdbc.query(sql_s, new Object[] {
					req.getRows(),req.getRows()*(req.getPageid()>=1?req.getPageid()-1:0)
			}, new BeanPropertyRowMapper(TeacherItem.class)) ;
		}catch(Exception e) {
			log.error("[getTeacherList] is exception :{},{}",sql_s ,e.getMessage());
			e.printStackTrace();			
		}
		finally {
			return list;
		}
	}

   public int InsertNewTeacher(reqCRUDTeacher req) {
      int ret = -1 ;
      String sql_s = "insert into wis_users (username,realname,passwd,usertype,isvalid,roles,status) "
            + " values(?,?,?,?,?,?::jsonb,-99) " ;
      try {
         GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();  

         PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement ps = con.prepareStatement(sql_s,new String[] { "uid" });
            ps.setString(1, req.getUsername());  
            ps.setString(2, req.getTeacher_name());  
            ps.setString(3 , req.getPasswd());
            ps.setInt(4, 1);
            ps.setInt(5, 1);
            ps.setString(6, "[]");
            return ps;
         };
         ret = jdbc.update(preparedStatementCreator, keyHolder );
         int uid = keyHolder.getKey().intValue();
         if(uid>0) {
            String sql_student = "insert into wis_teacher(uid,teacher_name,state,phone,contact,e_mail,teacher_intro,create_time) "
                  + " values(?,?,?,?,?,?,?,now())" ;
            ret = jdbc.update(sql_student, new Object[] {
               uid, req.getTeacher_name(), req.getState(), req.getPhone(), req.getContact(),
               req.getE_mail(),req.getTeacher_intro()
            }) ;
            if (ret > 0) {
               String sql_active = "update wis_users set status = 1 where uid = ? ";
               ret = jdbc.update(sql_active , new Object[] {uid});
            } else {
               String sql_failed = "delete from wis_users where uid=? and status = -99 ";
               ret = jdbc.update(sql_failed, new Object[] {uid});
            }
         }
         if (ret > 0) {
            ret = uid;
         }
      } catch (DuplicateKeyException e) {
         log.error("[InsertNewTeacher] is exception :{},{}",sql_s ,e.getMessage());
         e.printStackTrace();
         ret = -2;
      } catch(Exception e) {
         log.error("[InsertNewTeacher] is exception :{},{}",sql_s ,e.getMessage());
         e.printStackTrace();
      }
      finally {
       	return ret;
      }
   }

   public int UpdateTeacher(reqCRUDTeacher req ,TeacherItem src ) {
      int ret = -1;
      int upd_users = 1;
      int upd_data = 1;
      if (src.getTeacher_name().equals(req.getTeacher_name())) {
         upd_users = -1;
         ret = -2;
      }
      String sql_s1 = "update wis_users set realname=? where uid = ? and usertype = 1 ";
      String sql_s2 = "update wis_teacher set teacher_name=?,state=?,phone=?,contact=?,e_mail=?,teacher_intro=? "
            + " where uid=? and status>0 " ;
      try {
         if (upd_users == 1) {
            ret = jdbc.update(sql_s1, new Object[] {
                  req.getTeacher_name(), req.getTeacher_id()
            });
         }
         if (ret > 0 || (ret == -2 && upd_users == -1)) {
            ret = jdbc.update(sql_s2, new Object[] {
                  req.getTeacher_name(), req.getState(), req.getPhone(),
                  req.getContact(), req.getE_mail(), req.getTeacher_intro(),
                  req.getTeacher_id()
            });
         }
      } catch (Exception e) {
         log.error("[UpdateTeacher] is exception :{},{}", sql_s1 + ";" + sql_s2, e.getMessage());
         e.printStackTrace();
      }
      finally {
         return ret;
      }
   }

	public int DeleteTeacher(reqCRUDTeacher req ) {
		int ret = -1 ;
		int upd_users = 1;
		int upd_data = 1;
		String sql_s1 = "update wis_users set status = -1, username= username || '~' || uid "
				+ " where uid=? and status>0 " ;
		try {
			ret = jdbc.update(sql_s1, new Object[] {
					req.getTeacher_id() 
			});
		}catch(Exception e) {
	    	log.error("[DeleteTeacher] is exception :{},{}",sql_s1 ,e.getMessage());
	    	e.printStackTrace();
			
		}
		finally {
			return ret ;
		}
	}
}
