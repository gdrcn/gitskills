package com.wushaoning.www.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wushaoning.www.dao.BuyCarDao;
import com.wushaoning.www.dao.GoodsDao;
import com.wushaoning.www.dao.LoadingDao;
import com.wushaoning.www.dao.UserDao;
import com.wushaoning.www.model.BuyCar;
import com.wushaoning.www.model.Goods;
import com.wushaoning.www.util.IsDigitUtils;

/**
 * 对于添加进购物车进行逻辑判断
 * 
 * @author 10620
 *
 */
public class AddBuyCarService {
	public final String DISCOUNT_ONE = "none";
	public final String DISCOUNT_TWO = "discount1";
	public final String DISCOUNT_THREE = "discount2";
	public final int COMPARE_ONE = 200;
	
	/**
	 * 判断添加到购物的逻辑信息是否正确
	 * 
	 * @param buyNum
	 * @param phone
	 * @param apay
	 * @param address
	 * @return
	 */
	public int addBuyLogic(String buyNum, String phone, String apay, String address) {
		String expression1 = "^[0-9]{0,9}$";
		String expression2 = "^[a-zA-Z0-9\u4E00-\u9FA5]{0,40}$";
		String expression3 = "^[a-zA-Z0-9]{6,15}$";
		if ("".equals(buyNum) || "".equals(phone) || "".equals(apay) || "".equals(address)) {
			return -1;
		} else if (!IsDigitUtils.isDigits(buyNum) || !(buyNum.matches(expression1))) {
			return -2;
		} else if (!(phone.matches(expression3))) {
			return -3;
		} else if (!(apay.matches(expression3))) {
			return -4;
		} else if (!(address.matches(expression2))) {
			return -5;
		}
		return 1;
	}

	/**
	 * 判断商家仓库的件数是否足够
	 * 
	 * @param num
	 * @return
	 */
	public boolean isEnough(int userId, int goodsId, String num, int goodsNum) {
		Connection con = LoadingDao.getCon();
		try {
			if (BuyCarDao.haveUserGoods(userId, goodsId, con)) {
				int buyedNum = BuyCarDao.getOneGoodsNum(userId, goodsId, con);
				if (buyedNum + Integer.parseInt(num) > goodsNum) {
					return false;
				} else {
					return true;
				}
			}
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		} finally {
			LoadingDao.close(con);
		}
		if (Integer.parseInt(num) > goodsNum) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 
	 * 是否添加成功
	 */
	public boolean isAdd(BuyCar buyCar) {
		Connection con = LoadingDao.getCon();
		try {
			BuyCarDao.addBuyCar(buyCar, con);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			LoadingDao.close(con);
		}
		return false;
	}

	/**
	 * 对于购物已存在的商品进行逻辑处理
	 * 
	 * @param userId
	 * @param goodsId
	 * @param num
	 * @return
	 */
	public boolean isExistGoods(int userId, int goodsId, int num) {
		Connection con = LoadingDao.getCon();
		try {
			if (BuyCarDao.haveUserGoods(userId, goodsId, con)) {
				int buyedNum = BuyCarDao.getOneGoodsNum(userId, goodsId, con);
				BuyCarDao.updateGoodsNum(userId, goodsId, buyedNum + num, con);
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
	 * 增加用户浏览商品的记录
	 * @param userId
	 * @param goodsId
	 * @return
	 */
	public boolean latentFunction(int userId, int goodsId) {
		Connection con =LoadingDao.getCon();
		ResultSet rs;
		try {
			rs = GoodsDao.findGoodsJpg(goodsId, con);
			rs.next();
			String goodsTags = rs.getString(3);
			UserDao.upadteUserRelevant(userId, goodsTags, con);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			LoadingDao.close(con);
		}
		return false;
	}

	/**
	 * 商品折扣逻辑
	 * @param discount
	 * @param buyNum
	 * @param price
	 * @param goods
	 * @return
	 */
	public int discountLogic(String discount, int buyNum, int price, Goods goods) {
		int totalPrice = price * buyNum;
		
		if(DISCOUNT_ONE.equals(discount)) {
			return price;
		}else if(DISCOUNT_TWO.equals(discount)) {
			return (int)(totalPrice * 0.8/buyNum);
		}else if(DISCOUNT_THREE.equals(discount)) {
			if(totalPrice < COMPARE_ONE) {
				return -1;
			}
			return (int)((totalPrice-20)/buyNum * 1.0);
		}
		return 0;
	}
}
