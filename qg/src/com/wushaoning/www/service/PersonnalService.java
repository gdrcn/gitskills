package com.wushaoning.www.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wushaoning.www.dao.LoadingDao;
import com.wushaoning.www.dao.UserDao;
import com.wushaoning.www.model.User;

/**
 * 个人主页逻辑处理类
 * 
 * @author 10620
 *
 */
public class PersonnalService {

	/**
	 * 补充用户的隐性信息
	 * 
	 * @param user
	 * @return
	 */
	public int supplementInformation(User user) {
		Connection con = LoadingDao.getCon();
		ResultSet rs;
		try {
			rs = UserDao.getAllIntegral(user.getId(), con);
			while (rs.next()) {
				user.setIntegral(rs.getInt(7));
				user.setExperience(rs.getInt(8));
				user.setDegree(rs.getString(9));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			LoadingDao.close(con);
		}
		return 0;
	}

	/**
	 * 对修改的信息进行逻辑处理
	 * 
	 * @param userName
	 * @param phoneNumber
	 * @param qq
	 * @param wechat
	 * @param email
	 * @return
	 */
	public int alterLogic(String userName, String phoneNumber, String qq, String wechat, String email) {
		Connection con = LoadingDao.getCon();
		String expression1 = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
		String expression2 = "^[a-zA-Z0-9\u4E00-\u9FA5]{0,20}$";
		try {
			if ("".equals(userName) || "".equals(phoneNumber) || "".equals(qq) || "".equals(wechat)
					|| "".equals(email)) {
				return -1;
			}
			if (!(email.matches(expression1))) {
				return 1;
			}
			if (!userName.matches(expression2)) {
				return 2;
			}
			if (UserDao.fineUsername(userName, con)) {
				return 3;
			}if(UserDao.fineUseremail(email, con)) {
				return 4;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			LoadingDao.close(con);
		}
		return 0;
	}
}
