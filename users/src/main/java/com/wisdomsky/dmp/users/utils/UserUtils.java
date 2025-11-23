package com.wisdomsky.dmp.users.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.wisdomsky.dmp.users.common.SignUtil;
import com.wisdomsky.dmp.users.dao.db_users;
import com.wisdomsky.dmp.users.feign.authFeign;
import com.wisdomsky.dmp.users.feign.pojo.reqGetToken;
import com.wisdomsky.dmp.users.feign.pojo.respGetToken;
import com.wisdomsky.dmp.users.pojo.ClassItem;
import com.wisdomsky.dmp.users.pojo.LoginUserInfo;
import com.wisdomsky.dmp.users.pojo.MenuItem;
import com.wisdomsky.dmp.users.pojo.RoleItem;
import com.wisdomsky.dmp.users.pojo.StudentItem;
import com.wisdomsky.dmp.users.pojo.TeacherItem;
import com.wisdomsky.dmp.users.pojo.UserLoginData;
import com.wisdomsky.dmp.users.pojo.reqBaseData;
import com.wisdomsky.dmp.users.pojo.reqCRUDClass;
import com.wisdomsky.dmp.users.pojo.reqCRUDStudent;
import com.wisdomsky.dmp.users.pojo.reqCRUDTeacher;
import com.wisdomsky.dmp.users.pojo.reqQueryClass;
import com.wisdomsky.dmp.users.pojo.reqQueryStudent;
import com.wisdomsky.dmp.users.pojo.reqQueryTeacher;
import com.wisdomsky.dmp.users.pojo.reqUpdateUserPassword;
import com.wisdomsky.dmp.users.pojo.reqUserLogin;
import com.wisdomsky.dmp.users.pojo.respPageData;
import com.wisdomsky.dmp.users.pojo.respT;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j

public class UserUtils {
	@Autowired 
	private db_users db ;
	@Autowired
	private authFeign af ;
	@Autowired
	private CommonUtils common ;
	@Value("${app.user.defaultpwd}")
	private String defaultpasswd ;
	
	public List<MenuItem> deptList2Tree(List<MenuItem> list,int root) {
		List<MenuItem> treeList = new ArrayList<MenuItem>() ;
		for(MenuItem tree: list) {
			if(tree.getParent_id() == root) {
				treeList.add(findChildren(tree, list)) ;
			}
		}
		return treeList ;
	}
	
	public MenuItem findChildren(MenuItem tree ,List<MenuItem> list ) {
		for(int i = 1 ;i< list.size() ;i++) {
			MenuItem item = list.get(i) ;		
		}
		for(MenuItem node : list) {
			if(node.getParent_id() == tree.getMid()) {
				if(tree.getSubList() == null) {
					tree.setSubList(new ArrayList<MenuItem>());
				}
				tree.getSubList().add(findChildren(node,list)) ;
			}
		}		
		return tree ;
	}
	
	public List<MenuItem> sortTree(List<MenuItem> list){
		List<MenuItem> tree = new ArrayList<MenuItem>() ;
		for(MenuItem item : list) {
			if(item.getSubList().size()>0) {
				Collections.sort(item.getSubList() , new Comparator<MenuItem>() {
					@Override
					public int compare(MenuItem m1 , MenuItem m2) {
						return m1.getMdispid() - m2.getMdispid() ;
					}
				});
			}
		}
		Collections.sort(list , new Comparator<MenuItem>() {
			@Override
			public int compare(MenuItem m1 , MenuItem m2) {
				return m1.getMdispid() - m2.getMdispid() ;
			}
		});
		return list ;
	}
	public respT loginByUserName(reqUserLogin req,String base_str,String sign,String appID) {
		respT resp = new respT() ;
		try{
			LoginUserInfo user = db.getUserInfoByUserName(req.getUsername());
//			EmployeeUserInfo user = db.getUserInfoByPhone(req.getPhone());
			if(user == null) {
				resp.setErrcode(-100014);
				resp.setErrmsg("该用户不存在");
			}
			else {
				if(user.getIsvalid() == 0) {
					resp.setErrcode(-100016);
					resp.setErrmsg("该用户已禁用，无法登录");
					
				}
				else {
					String signkey = user.getPasswd();
					if(signkey != null && signkey.length()>5) {
						boolean canLogin = SignUtil.checkSignatureSample(sign, base_str+signkey);
						if(canLogin) {
							UserLoginData data = new UserLoginData() ;
							reqGetToken rt = new reqGetToken() ;
							rt.setUserId(String.valueOf(user.getUserID()));
							rt.setTcode("");
							rt.setQtype(1);
							respGetToken rs = af.doGetToken(appID, String.valueOf(user.getUserID()), 
									String.valueOf(System.currentTimeMillis()), "00000000", "nativecall", rt) ;
							if(rs.getErrcode()>=0) {
								data.setToken(rs.getToken());
								data.setClientID(rs.getTcode());
								user.setLogin_time(new Timestamp(System.currentTimeMillis()));
								data.setUserInfo(user);
								JSONArray role_s = JSON.parseArray(user.getRoles_str()) ;
								String in_role_str = "";
								for(int i = 0 ; i< role_s.size() ;i++) {
//									in_role_str.add(role_s.getJSONObject(i).getInteger("rid")) ;
									if(i>0) {
										in_role_str += "," ;
									}
									in_role_str += role_s.getJSONObject(i).getInteger("rid");
								}
								if(role_s.size()>0) {
									data.setRoles(db.getRoleListByInRoleStr(in_role_str));
									List<MenuItem> menus = db.getMenuListByInRoleStr(in_role_str) ;
									if(menus.size()>0) {
										List<MenuItem> mtree = deptList2Tree(menus, menus.get(0).getParent_id()) ;
										mtree = sortTree(mtree) ;
										data.setMenus(mtree);
									}
									else {
										data.setMenus(menus);
									}
								}
								else {
									data.setRoles(new ArrayList<RoleItem>());
									data.setMenus(new ArrayList<MenuItem>());
								}
								resp.setData(data);
								resp.setErrcode(0);
								resp.setErrmsg("success");
							}
							else {
								resp.setErrcode(rs.getErrcode());
								resp.setErrmsg(rs.getErrmsg());
							}						
						}
						else {
							resp.setErrcode(-70001);
							resp.setErrmsg("无效签名");
						}
					}
					else {
						resp.setErrcode(-100012);
						resp.setErrmsg("密码不正确");
					}
					
				}
				
			}			
		}catch(Exception e) {
			log.error("loginByPhone exception is coming:{}，{}",JSON.toJSONString(req),e.getMessage());
			resp.setErrcode(-70103);
			resp.setErrmsg("service utils error");					
		}
		return resp ;
	}
	

	public respT logout(String appid,String clientid,int uid,long timestamp ) {
		respT resp = new respT() ;
		try {
			resp = af.doRemoveToken(appid,String.valueOf(uid),String.valueOf(timestamp),clientid) ;
		}catch(Exception e) {
			log.error("logout exception is coming:{}，{}",String.valueOf(uid),e.getMessage());
			resp.setErrcode(-70103);
			resp.setErrmsg("service utils error");		
		}
		finally {
			return resp ;
		}
	}
	

	public respT chgUserPassword(int uid ,reqUpdateUserPassword req) {
		respT resp = new respT() ;
		try {
			int ret = db.updateUserPasswd(uid, "",req.getNewpasswd()) ;
			if(ret>0) {
				resp.setErrcode(0);
				resp.setErrmsg("success");
			}
			else {
				resp.setErrcode(-70107);
				resp.setErrmsg("密码更新失败，请检查数据后请稍后重试");
			}
		}catch(Exception e) {
			log.error("chgUserPassword exception is coming:{}，{}",String.valueOf(uid),e.getMessage());
			resp.setErrcode(-70103);
			resp.setErrmsg("service utils error");		
		}
		finally {
			return resp ;
		}
		
	}
	
	public respT getMyUserInfo(reqBaseData req) {
		respT resp = new respT();
		LoginUserInfo user = db.getUserInfoByUserId(req.getUserid());
		if(user == null) {
			resp.setErrcode(-70140);
			resp.setErrmsg("无法获取此用户的相关信息");
		}
		else {
			switch(user.getUsertype()) {
			case 2:
				reqQueryTeacher rt = new reqQueryTeacher();
				rt.setTeacher_id(req.getUserid());
				List<TeacherItem>  tlist =db.getTeacherList(rt);
				if(tlist.size()>0) {
					resp.setData(tlist.get(0));
				}
				else {
					resp.setData(user);
				}
				break;
			case 4:
				reqQueryStudent rs = new reqQueryStudent();
				rs.setStudent_id(req.getUserid());
				List<StudentItem>  slist =db.getStudentList(rs);
				if(slist.size()>0) {
					resp.setData(slist.get(0));
				}
				else {
					resp.setData(user);
				}
				break;
			default:
				resp.setData(user);
				break;
			}
			resp.setErrcode(0);
			resp.setErrmsg("success");
		}
		return resp ;
	}
	
	public respT resetUserPassword(int uid, reqUpdateUserPassword req) {
		respT resp = new respT() ;
		try {
			LoginUserInfo oper = db.getUserInfoByUserId(req.getUserid());
			if(oper == null || oper.getUsertype() != 3) {
				resp.setErrcode(-70117);
				resp.setErrmsg("无权操作");
			}
			else {
				LoginUserInfo uinfo = db.getUserInfoByUserId(req.getUid()) ;
				if(uinfo == null  ) {
					resp.setErrcode(-70120);
					resp.setErrmsg("无法获取该用户信息，请检查数据后重试");			
				}
				else {
					int ret = db.updateUserPasswd(req.getUid(), "",defaultpasswd) ;
					if(ret>0) {
						resp.setErrcode(0);
						resp.setErrmsg("success");
					}
					else {
						resp.setErrcode(-70107);
						resp.setErrmsg("密码更新失败，请检查数据后请稍后重试");
					}				
				}
				
			}
		}catch(Exception e) {
			log.error("resetUserPassword exception is coming:{}，{}",String.valueOf(uid),e.getMessage());
			resp.setErrcode(-70103);
			resp.setErrmsg("service utils error");		
		}
		finally {
			return resp ;
		}		
	}
	
	public respT addNewStudent(reqCRUDStudent req) {
		respT resp = new respT() ;
		try {
			req.setPasswd(defaultpasswd);
			req.setRoles("[]");
			int ret = db.InsertNewStudent(req);
			if(ret>0) {
				resp.setErrcode(0);
				resp.setErrmsg("success");
			}
			else {
				resp.setErrcode(-70121);
				resp.setErrmsg("新增学生操作失败，请检查数据后重试");
			}
		}catch(Exception e) {
			log.error("addNewClass exception is coming:{}，{}",JSON.toJSONString(req),e.getMessage());
			resp.setErrcode(-70103);
			resp.setErrmsg("service utils error");		
		}
		finally {
			return resp ;
		}
	}
	
	public respT updateStudent(reqCRUDStudent req) {
		respT resp = new respT() ;
		try {
			reqQueryStudent r = new reqQueryStudent();
			r.setStudent_id(req.getStudent_id());
			List<StudentItem> srclist = db.getStudentList(r);
			if(r == null ) {
				resp.setErrcode(-70122);
				resp.setErrmsg("被更新的学生不存在，请检查数据后重试");
				
			}
			else {
				int ret = db.UpdateStudent(req,srclist.get(0));
				if(ret>0) {
					resp.setErrcode(0);
					resp.setErrmsg("success");
				}
				else {
					resp.setErrcode(-70123);
					resp.setErrmsg("更新学生信息失败，请检查数据后重试");
				}				
			}
		}catch(Exception e) {
			log.error("updateStudent exception is coming:{}，{}",JSON.toJSONString(req),e.getMessage());
			resp.setErrcode(-70103);
			resp.setErrmsg("service utils error");		
		}
		finally {
			return resp ;
		}
		
	}
	public respT deleteStudent(reqCRUDStudent req) {
		respT resp = new respT() ;
		try {
			int ret = db.DeleteStudent(req);
			if(ret>0) {
				resp.setErrcode(0);
				resp.setErrmsg("success");
			}
			else {
				resp.setErrcode(-70124);
				resp.setErrmsg("删除学生信息失败，请检查数据后重试");
			}
		}catch(Exception e) {
			log.error("deleteStudent exception is coming:{}，{}",JSON.toJSONString(req),e.getMessage());
			resp.setErrcode(-70103);
			resp.setErrmsg("service utils error");		
		}
		finally {
			return resp ;
		}
		
	}
	public respT getStudentList(reqQueryStudent req) {
		respT resp = new respT() ;
		try {
			List<StudentItem> list = db.getStudentList(req);
			respPageData data = new respPageData();
			if(list.size()>0) {
				data.setTotal(list.get(0).getTotal());
			}
			data.setPageid(req.getPageid());
			data.setPageData(list);
			resp.setErrcode(0);
			resp.setErrmsg("success");
			resp.setData(data);
		}catch(Exception e) {
			log.error("getStudentList exception is coming:{}，{}",JSON.toJSONString(req),e.getMessage());
			resp.setErrcode(-70103);
			resp.setErrmsg("service utils error");		
		}
		finally {
			return resp ;
		}
		
	}

   public respT addNewClass(reqCRUDClass req) {
      respT resp = new respT() ;
      try {
         int ret = db.InsertNewClass(req);
         if (ret > 0) {
            resp.setErrcode(0);
            resp.setErrmsg("success");
         } else if (ret == -2) {
            resp.setErrcode(-70136);
            resp.setErrmsg("新增班级操作失败，班级代码重复");
         } else {
            resp.setErrcode(-70108);
            resp.setErrmsg("新增班级操作失败，请检查数据后重试");
         }
      } catch (Exception e) {
         log.error("addNewClass exception is coming:{}，{}", JSON.toJSONString(req), e.getMessage());
         resp.setErrcode(-70103);
         resp.setErrmsg("service utils error");
      }
      finally {
         return resp;
      }
   }

   public respT updateClass(reqCRUDClass req) {
      respT resp = new respT();
      try {
         int ret = db.UpdateClass(req);
         if (ret > 0) {
            resp.setErrcode(0);
            resp.setErrmsg("success");
         } else if (ret == -2) {
            resp.setErrcode(-70139);
            resp.setErrmsg("更新班级信息失败，班级代码重复");
         } else {
            resp.setErrcode(-70109);
            resp.setErrmsg("更新班级信息失败，请检查数据后重试");
         }
      } catch (Exception e) {
         log.error("updateClass exception is coming:{}，{}", JSON.toJSONString(req), e.getMessage());
         resp.setErrcode(-70103);
         resp.setErrmsg("service utils error");
      } finally {
         return resp;
      }
   }

	public respT deleteClass(reqCRUDClass req) {
		respT resp = new respT() ;
      try {
         reqQueryStudent query = new reqQueryStudent();
         query.setClass_id(req.getClass_id());
         query.setPageid(1);
         query.setRows(1);
         int count = db.getStudentList(query).size();
         if (count > 0) {
            resp.setErrcode(-70137);
				resp.setErrmsg("删除班级信息失败，班级存在学生记录");
         } else {
            int ret = db.DeleteClass(req);
            if (ret > 0) {
               resp.setErrcode(0);
               resp.setErrmsg("success");
            } else {
               resp.setErrcode(-70110);
               resp.setErrmsg("删除班级信息失败，请检查数据后重试");
            }
         }
		} catch(Exception e) {
			log.error("deleteClass exception is coming:{}，{}",JSON.toJSONString(req),e.getMessage());
			resp.setErrcode(-70103);
			resp.setErrmsg("service utils error");		
		}
		finally {
			return resp ;
		}
		
	}
	public respT getClassList(reqQueryClass req) {
		respT resp = new respT() ;
		try {
			List<ClassItem> list = db.getClassList(req);
			respPageData data = new respPageData();
			if(list.size()>0) {
				data.setTotal(list.get(0).getTotal());
			}
			data.setPageid(req.getPageid());
			data.setPageData(list);
			resp.setErrcode(0);
			resp.setErrmsg("success");
			resp.setData(data);
		}catch(Exception e) {
			log.error("getClassList exception is coming:{}，{}",JSON.toJSONString(req),e.getMessage());
			resp.setErrcode(-70103);
			resp.setErrmsg("service utils error");		
		}
		finally {
			return resp ;
		}
		
	}
	public respT addNewTeacher(reqCRUDTeacher req) {
      respT resp = new respT() ;
      try {
         req.setPasswd(defaultpasswd);
         req.setRoles("[]");
         int ret = db.InsertNewTeacher(req);
         if (ret > 0) {
            resp.setErrcode(0);
            resp.setErrmsg("success");
         } else if (ret == -2) {
            resp.setErrcode(-70135);
            resp.setErrmsg("新增教师操作失败，用户名重复");
         } else {
            resp.setErrcode(-70131);
            resp.setErrmsg("新增教师操作失败，请检查数据后重试");
         }
      } catch(Exception e) {
         log.error("addNewTeacher exception is coming:{}，{}",JSON.toJSONString(req),e.getMessage());
         resp.setErrcode(-70103);
         resp.setErrmsg("service utils error");		
      }
      finally {
         return resp ;
      }
   }

	public respT updateTeacher(reqCRUDTeacher req) {
		respT resp = new respT() ;
		try {
			reqQueryTeacher r = new reqQueryTeacher();
			r.setTeacher_id(req.getTeacher_id());
			List<TeacherItem> srclist = db.getTeacherList(r);
			if(r == null ) {
				resp.setErrcode(-70132);
				resp.setErrmsg("被更新的教师不存在，请检查数据后重试");
				
			}
			else {
				int ret = db.UpdateTeacher(req,srclist.get(0));
				if(ret>0) {
					resp.setErrcode(0);
					resp.setErrmsg("success");
				}
				else {
					resp.setErrcode(-70133);
					resp.setErrmsg("更新教师信息失败，请检查数据后重试");
				}				
			}
		}catch(Exception e) {
			log.error("updateTeacher exception is coming:{}，{}",JSON.toJSONString(req),e.getMessage());
			resp.setErrcode(-70103);
			resp.setErrmsg("service utils error");		
		}
		finally {
			return resp ;
		}
		
	}
	public respT deleteTeacher(reqCRUDTeacher req) {
		respT resp = new respT() ;
		try {
         reqQueryClass query = new reqQueryClass();
         query.setTeacher_id(req.getTeacher_id());
         query.setPageid(1);
         query.setRows(1);
         int count = db.getClassList(query).size();
         if (count > 0) {
            resp.setErrcode(-70138);
				resp.setErrmsg("删除教师信息失败，教师存在负责班级");
         } else {
			int ret = db.DeleteTeacher(req);
         
			if(ret>0) {
				resp.setErrcode(0);
				resp.setErrmsg("success");
			}
			else {
				resp.setErrcode(-70134);
				resp.setErrmsg("删除教师信息失败，请检查数据后重试");
			}
      }
		}catch(Exception e) {
			log.error("deleteStudent exception is coming:{}，{}",JSON.toJSONString(req),e.getMessage());
			resp.setErrcode(-70103);
			resp.setErrmsg("service utils error");		
		}
		finally {
			return resp ;
		}
		
	}
	public respT getTeacherList(reqQueryTeacher req) {
		respT resp = new respT() ;
		try {
			List<TeacherItem> list = db.getTeacherList(req);
			respPageData data = new respPageData();
			if(list.size()>0) {
				data.setTotal(list.get(0).getTotal());
			}
			data.setPageid(req.getPageid());
			data.setPageData(list);
			resp.setErrcode(0);
			resp.setErrmsg("success");
			resp.setData(data);
		}catch(Exception e) {
			log.error("getTeacherList exception is coming:{}，{}",JSON.toJSONString(req),e.getMessage());
			resp.setErrcode(-70103);
			resp.setErrmsg("service utils error");		
		}
		finally {
			return resp ;
		}
		
	}
}
