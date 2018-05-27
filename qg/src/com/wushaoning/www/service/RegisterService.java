package com.wushaoning.www.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.wushaoning.www.dao.LoadingDao;
import com.wushaoning.www.dao.UserDao;
import com.wushaoning.www.model.User;

/**
 * 注册逻辑处理
 * 
 * @author 10620
 *
 */
public class RegisterService {

	public int enroll(User user) {
		Connection con = LoadingDao.getCon();
		try {
			if (UserDao.fineUsername(user.getUserName(), con)) {
				return 1;
			} else if (UserDao.fineUseremail(user.getEmail(), con)) {
				return 2;
			} else {
				UserDao.addUser(user, con);
				return 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} finally {
			LoadingDao.close(con);
		}
	}

	public int userLogic(User user) {
		String expression1 = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
		String expression2 = "^[a-zA-Z0-9]{6,12}$";
		String expression3 = "^[a-zA-Z0-9\u4E00-\u9FA5]{0,20}$";
		if (user.getAddress() == "" || user.getEmail() == "" || user.getUserName() == "" || user.getPassword() == ""
				|| user.getPhoneNumber() == "" || user.getWechat() == "" || user.getqq() == "") {
			return -1;
		}
		if (!(user.getEmail().matches(expression1))) {
			return 1;
		}
		if (!user.getPassword().matches(expression2)) {
			return 2;
		}
		if (!user.getUserName().matches(expression3)) {
			return 3;
		}
		return 0;
	}

	/**
	 * 判断密码保护信息
	 * 
	 * @return
	 */
	public int userPwdPtd(User userPwdPtd) {
		// TODO Auto-generated method stub
		String expression = "^[a-zA-Z0-9\u4E00-\u9FA5]{0,20}$";
		if (userPwdPtd.getPwdPtd() == "") {
			return -1;
		}
		if (userPwdPtd.getPwdPtd().matches(expression)) {
			return 1;
		}

		return 0;
	}

	/**
	 * 密码保护信息插入
	 * 
	 * @param user
	 * @return
	 */
	public int enrollPwdPtd(User user) {
		Connection con = LoadingDao.getCon();
		try {
			UserDao.addPwdPtd(user, con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			LoadingDao.close(con);
		}
		return 0;
	}

	/**
	 * 权限逻辑处理
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean insertRole(String userName) {
		Connection con = LoadingDao.getCon();
		try {
			User user = UserDao.findUserSg(userName, con);
			int id = user.getId();
			UserDao.addRole(id, con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			LoadingDao.close(con);
		}
		return true;
	}

	/**
	 * 插入隐性数据
	 * @param userName
	 * @return
	 */
	public boolean insertLatentRecord(String userName) {
		Connection con = LoadingDao.getCon();
		try {
			int userId = UserDao.fineUserId(userName, con);
			UserDao.insertLatentFunction(userId, con);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			LoadingDao.close(con);
		}
		return false;
	}

}
