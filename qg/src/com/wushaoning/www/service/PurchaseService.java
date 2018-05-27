package com.wushaoning.www.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.wushaoning.www.dao.BusinessDao;
import com.wushaoning.www.dao.BuyCarDao;
import com.wushaoning.www.dao.GoodsDao;
import com.wushaoning.www.dao.LoadingDao;
import com.wushaoning.www.dao.UserDao;
import com.wushaoning.www.util.IsDigitUtils;

/**
 * 处理订单逻辑
 * 
 * @author 10620
 *
 */
public class PurchaseService {
	public final String DISCOUNT_1 = "discount1";
	public final String DISCOUNT_2 = "discount2";
	public final String DISCOUNT_3 = "discount3";
	public final String JUDGE_USER = "user";
	public final String JUDGE_VIP1 = "vip1";
	public final String JUDGE_VIP2 = "vip2";
	public final int DIGIT_10 = 10;
	public final int DIGIT_50 = 50;
	public final int DIGIT_100 = 100;
	public final int DIGIT_1000 = 1000;
	public final int DIGIT_5000 = 5000;
	public final int DIGIT_10000 = 10000;
	
	/**
	 * 确定订单逻辑
	 * 
	 * @param userId
	 * @param goodsId
	 * @param sign
	 * @return
	 */
	public boolean addOrder(String userId, String goodsId, String buyCarId, String sign) {
		Connection con = LoadingDao.getCon();
		try {
			int theUserId = Integer.parseInt(userId);
			int theGoodsId = Integer.parseInt(goodsId);
			int shopId = GoodsDao.getShopId(theGoodsId, con);
			int businessmanId = BusinessDao.theBusinessmanId(shopId, con);
			if (BuyCarDao.addTrade(theUserId, theGoodsId, businessmanId, shopId, Integer.parseInt(buyCarId), sign,
					con) == 1) {
				BuyCarDao.alterBuyCar(Integer.parseInt(buyCarId), sign, con);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			LoadingDao.close(con);
		}
		return false;
	}

	/**
	 * 取消订单逻辑
	 * 
	 * @param userId
	 * @param goodsId
	 * @param sign
	 */
	public boolean missOrder(String buyCarId, String sign) {
		Connection con = LoadingDao.getCon();
		try {
			if (BuyCarDao.updateOrder(Integer.parseInt(buyCarId), sign, con) == 1) {
				BuyCarDao.missBuyCar(Integer.parseInt(buyCarId), con);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			LoadingDao.close(con);
		}
		return false;
	}

	/**
	 * 在商家数据表插入数据
	 * 
	 * @param parseInt
	 * @return
	 */
	public boolean assessShop(int buyCarId) {
		Connection con = LoadingDao.getCon();
		try {
			ResultSet rs = BuyCarDao.getUserBuyCar(buyCarId, con);
			rs.next();
			int tradeId = BuyCarDao.getTradeId(buyCarId, con);
			BuyCarDao.addAssessShop(rs.getInt(9), tradeId, rs.getInt(2), rs.getBlob(8), con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			LoadingDao.close(con);
		}
		return false;
	}

	/**
	 * 处理得到的评论逻辑
	 * 
	 * @param buyCarId
	 * @param star
	 * @param assessTxt
	 * @return
	 */
	public boolean alterAssess(int buyCarId, String star, String assessTxt) {
		Connection con = LoadingDao.getCon();
		ResultSet rs;
		try {
			rs = BuyCarDao.getUserBuyCar(buyCarId, con);
			rs.next();
			int tradeId = BuyCarDao.getTradeId(buyCarId, con);
			int starLevel = IsDigitUtils.getStar(star);
			BuyCarDao.updateAssess(tradeId, starLevel, assessTxt, con);
			BuyCarDao.updateOrder(buyCarId, "evaluated", con);
			BuyCarDao.shopUpdateBuyCar(buyCarId, "evaluated", con);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 当用户购买后添加浏览记录,增加积分,查看是否能升级
	 * 
	 * @param userId
	 * @param goodsId
	 */
	public void addRecord(int userId, int buyCarId, HttpServletRequest request) {
		Connection con = LoadingDao.getCon();
		try {
			ResultSet rs = BuyCarDao.getUserBuyCar(buyCarId, con);
			rs.next();
			int goodsPrice = rs.getInt(11);
			int buyAllNum = rs.getInt(10);
			UserDao.raiseRelevant(userId, goodsPrice * buyAllNum, con);
			rs = UserDao.getAllIntegral(userId, con);
			rs.next();
			int thisUserId = rs.getInt(2);
			int experience = rs.getInt(8);
			String degree = rs.getString(9);
			upgrade(thisUserId, experience, degree);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			LoadingDao.close(con);
		}
	}

	/**
	 * 当经验达到一定程度后升级
	 * 
	 * @param thisUserId
	 * @param experience
	 * @param degree
	 */
	public void upgrade(int thisUserId, int experience, String degree) {
		Connection con = LoadingDao.getCon();
		try {
			if (experience > DIGIT_1000 && JUDGE_USER.equals(degree)) {
				UserDao.updateRelevant(thisUserId, experience, "vip1", con);
				return;
			} else if (experience > DIGIT_5000 && JUDGE_VIP1.equals(degree)) {
				UserDao.updateRelevant(thisUserId, experience, "vip2", con);
				return;
			} else if (experience > DIGIT_10000 && JUDGE_VIP2.equals(degree)) {
				UserDao.updateRelevant(thisUserId, experience, "vip3", con);
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			LoadingDao.close(con);
		}
	}

	/**
	 * 看积分是否足够，足够就减少积分，减少价格。
	 * 
	 * @param discount
	 * @param userId
	 * @param buyCarId
	 * @return
	 */
	public int enoughIntegral(String discount, int userId, String buyCarId) {
		Connection con = LoadingDao.getCon();
		try {
			int totalPrice;
			int buyGoodsNum = 0;
			int integral = UserDao.getIntegral(userId, con);
			int goodsPrice = BuyCarDao.getGoodsPrice(Integer.parseInt(buyCarId), con);
			ResultSet rs = BuyCarDao.getUserBuyCar(Integer.parseInt(buyCarId), con);
			while (rs.next()) {
				buyGoodsNum = rs.getInt(10);
			}
			totalPrice = goodsPrice * buyGoodsNum;

			if (DISCOUNT_1.equals(discount)) {
				if (integral < DIGIT_1000) {
					return -1;
				} else {
					if (goodsPrice - DIGIT_10 < 0) {
						return -2;
					} else {
						UserDao.updateTrade(Integer.parseInt(buyCarId),
								(int) (goodsPrice - (totalPrice - 10) / buyGoodsNum * 1.0), con);
						UserDao.updateRelevant(userId, 1000, con);
						return 1;
					}
				}
			} else if (DISCOUNT_2.equals(discount)) {
				if (integral < DIGIT_5000) {
					return -1;
				} else {
					if (goodsPrice - DIGIT_50 < 0) {
						return -2;
					} else {
						UserDao.updateTrade(Integer.parseInt(buyCarId),
								(int) (goodsPrice - (totalPrice - 50) / buyGoodsNum * 1.0), con);
						UserDao.updateRelevant(userId, 5000, con);
						return 1;
					}
				}
			} else if (DISCOUNT_3.equals(discount)) {
				if (integral < DIGIT_10000) {
					return -1;
				} else {
					if (goodsPrice - DIGIT_100 < 0) {
						return -2;
					} else {
						UserDao.updateTrade(Integer.parseInt(buyCarId),
								(int) (goodsPrice - ((totalPrice - 100) / buyGoodsNum * 1.0)), con);
						UserDao.updateRelevant(userId, 10000, con);
						return 1;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			LoadingDao.getCon();
		}
		return 0;
	}

}
