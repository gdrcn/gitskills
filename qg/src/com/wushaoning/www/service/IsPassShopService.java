package com.wushaoning.www.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wushaoning.www.dao.BusinessDao;
import com.wushaoning.www.dao.LoadingDao;

/**
 * 商家审核逻辑处理类
 * @author 10620
 *
 */
public class IsPassShopService {


	/**
	 * 通过审核
	 * 
	 * @param userId
	 * @param pass
	 */
	public void passShop(int userId, String pass) {
		Connection con = LoadingDao.getCon();
		try {
			BusinessDao.passShop(userId, pass, con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoadingDao.close(con);
	}

	/**
	 * 关闭店铺并且删除商品
	 * 
	 * @param userId
	 * @param pass
	 */
	public void closeShop(int userId) {
		Connection con = LoadingDao.getCon();
		try {
			BusinessDao.closeShop(userId, con);
			ResultSet rs = BusinessDao.findBusinessmanSg(userId, con);
			rs.next();
			int shopId = rs.getInt(7);
			BusinessDao.deleteShop(shopId, con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoadingDao.close(con);
	}
	
	public static int passShopLogic(String realId, String apay) {
		String expression1 = "^[a-zA-Z0-9]{18}$";
		String expression2 = "^[a-zA-Z0-9\u4E00-\u9FA5]{0,18}$";
		Connection con = LoadingDao.getCon(); 
		if (realId == "" || apay == "") {
			return -1;
		}
		if (!(realId.matches(expression1))) {
			return 1;
		}
		if (!apay.matches(expression2)) {
			return 2;
		}try {
			if(BusinessDao.haveRealId(realId, con)) {
				return 3;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			LoadingDao.close(con);
		}
		return 0;
	}
}
