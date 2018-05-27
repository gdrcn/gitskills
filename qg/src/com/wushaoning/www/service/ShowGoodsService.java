package com.wushaoning.www.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wushaoning.www.dao.GoodsDao;
import com.wushaoning.www.dao.LoadingDao;

/**
 * 商家管理功能逻辑处理
 * 
 * @author 10620
 *
 */
public class ShowGoodsService {

	/**
	 * 修改购买后商品的数据
	 * 
	 * @param goodsId
	 * @param num
	 * @return
	 */
	public boolean reduceNum(int goodsId, int num) {
		Connection con = LoadingDao.getCon();
		try {
			ResultSet rs = GoodsDao.oneGoods(String.valueOf(goodsId), con);
			rs.next();
			int realNum = rs.getInt(7);
			if (realNum < num) {
				return false;
			} else {
				int fianlNum = realNum - num;
				GoodsDao.updateNum(fianlNum, goodsId, con);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			LoadingDao.close(con);
		}
		return true;
	}

	/**
	 * 增加销售量
	 * 
	 * @param goodsId
	 * @param num
	 * @return
	 */
	public boolean addSalesVolume(int goodsId, int volume) {
		Connection con = LoadingDao.getCon();
		try {
			ResultSet rs = GoodsDao.oneGoods(String.valueOf(goodsId), con);
			rs.next();
			int beforeVolume = rs.getInt(9);
			GoodsDao.updateVolume(volume + beforeVolume, goodsId, con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			LoadingDao.close(con);
		}
		return true;
	}

}
