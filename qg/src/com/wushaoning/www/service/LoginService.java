package com.wushaoning.www.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wushaoning.www.dao.BusinessDao;
import com.wushaoning.www.dao.BuyCarDao;
import com.wushaoning.www.dao.LoadingDao;
import com.wushaoning.www.dao.UserDao;
import com.wushaoning.www.model.Businessman;
import com.wushaoning.www.model.User;

/**
 * 登陆逻辑处理
 * 
 * @author 10620
 *
 */
public class LoginService {
	public final String JUDGE_SUCCESS = "success";
	public final String JUDGE_FAIL = "fail";
	public final String JUDGE_WAIT = "wait";
	/**
	 * 判断用户名与密码是否正确
	 */
	public boolean isExistence(String userName, String password) {
		Connection con = LoadingDao.getCon();
		try {
			if (UserDao.fineUser(userName, password, con)) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			LoadingDao.close(con);
		}
		return false;
	}

	/**
	 * 判断用户是否有shop,有则返回
	 * 
	 * @param userId
	 * @return
	 */
	public String existShop(int userId) {
		String sug;
		Connection con = LoadingDao.getCon();
		try {
			if (BusinessDao.haveShop(userId, con)) {
				ResultSet rs = BusinessDao.findShop(userId, con);
				if (rs.next()) {
					sug = rs.getString(6);
					if(JUDGE_SUCCESS.equals(sug)) {
						return sug;
					}else if(JUDGE_FAIL.equals(sug)) {
						return sug;
					}else if(JUDGE_WAIT.equals(sug)) {
						return sug;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			LoadingDao.close(con);
		}
		return null;
	}
	
	/**
	 * 将用户实例的数据传递给商人实例
	 * @param businessman
	 * @param user
	 */
	public Businessman changeBusinessman(Businessman businessman, User user) {
		businessman.setId(user.getId());
		businessman.setAddress(user.getAddress());
		businessman.setEmail(user.getEmail());
		businessman.setUserName(user.getUserName());
		businessman.setPassword(user.getPassword());
		businessman.setGender(user.getGender());
		businessman.setPhoneNumber(user.getPhoneNumber());
		businessman.setqq(user.getqq());
		businessman.setWechat(user.getWechat());
		businessman.setRole(user.getRole());
		Connection con = LoadingDao.getCon();
		try {
			businessman.setShop(BusinessDao.theShopId(user.getId(), con));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			LoadingDao.close(con);
		}
		return businessman;
	}

	/**
	 * 得到购物车的宝贝件数
	 * @param userId
	 * @return
	 */
	public int getUserBuyCarNum(int userId) {
		Connection con = LoadingDao.getCon();
		int result = 0;
		try {
			ResultSet rs = BuyCarDao.getBuyCarNum(userId, con);
			while(rs.next()) {
				result++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
