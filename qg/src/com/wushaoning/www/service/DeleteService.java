package com.wushaoning.www.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.wushaoning.www.dao.LoadingDao;
import com.wushaoning.www.dao.UserDao;

/**
 * 处理删除用户逻辑
 * 
 * @author 10620
 *
 */
public class DeleteService {

	/**
	 * 管理员删除用户逻辑
	 * @param userId
	 * @param myId
	 * @return
	 */
	public int deleteUser(String userId, String myId) {
		Connection con = LoadingDao.getCon();
		try {
			int userRole = Integer.parseInt(UserDao.getUserRole(Integer.parseInt(userId), con));
			int myRole = Integer.parseInt(UserDao.getUserRole(Integer.parseInt(myId), con));
			if(myRole < userRole) {
				System.out.println(UserDao.deleteUser(Integer.parseInt(userId), con));
				return 1;
			}else {
				return -1;
			}
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}finally {
			LoadingDao.close(con);
		}
		return 0;
	}

	


}
