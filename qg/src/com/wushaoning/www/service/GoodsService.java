package com.wushaoning.www.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.wushaoning.www.dao.BusinessDao;
import com.wushaoning.www.dao.GoodsDao;
import com.wushaoning.www.dao.LoadingDao;
import com.wushaoning.www.dao.UserDao;
import com.wushaoning.www.model.Goods;

/**
 * 商品逻辑处理类
 * 
 * @author 10620
 *
 */
public class GoodsService {
	public final int COMPARE_ZORE = 0;
	public final int COMPARE_ONE = 1;
	public final int COMPARE_TWO = 2;
	public final int COMPARE_THREE = 3;
	public final int COMPARE_FOUR = 4;
	
	/**
	 * 判断商品格式是否正确
	 * 
	 * @param price
	 * @param num
	 * @param function
	 * @param goodsName
	 * @return
	 */
	public int goodsLogic(String price, String num, String function, String goodsName) {
		String expression1 = "^[0-9]{0,12}$";
		String expression2 = "^[0-9]{0,12}$";
		String expression3 = "^[a-zA-Z0-9\u4E00-\u9FA5]{0,100}$";
		if ("".equals(price) || "".equals(function) || "".equals(num)) {
			return -1;
		}
		if (!(price.matches(expression1))) {
			return 1;
		}
		if (!num.matches(expression2)) {
			return 2;
		}
		if (!function.matches(expression3)) {
			return 3;
		}
		if (!goodsName.matches(expression3)) {
			return 4;
		}

		return 0;
	}

	/**
	 * 
	 * 推送用户经常浏览爱好的商品
	 * 
	 * @param userId
	 * @return
	 */
	public List<Goods> getUserLike(int userId) {
		List<Goods> list = new ArrayList<Goods>();
		Connection con = LoadingDao.getCon();
		ResultSet rs;
		int maxindex = 0;
		try {
			rs = UserDao.getAllIntegral(userId, con);
			while (rs.next()) {
				int[] goodsLike = { rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6) };
				for (int k = 1; k < goodsLike.length; k++) {
					if (goodsLike[k] > goodsLike[maxindex]) {
						maxindex = k;
					}
				}
			}
			if (maxindex == 0) {
				ResultSet rst = GoodsDao.someGoods("服装配饰", con);
				while (rst.next()) {
					Goods goods = new Goods(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getInt(4),
							rst.getBlob(5), rst.getInt(6), rst.getInt(7), rst.getString(8), rst.getInt(9));
					list.add(goods);
				}
				return list;
			} else if (maxindex == COMPARE_ONE) {
				ResultSet rst = GoodsDao.someGoods("零食饮料", con);
				while (rst.next()) {
					Goods goods = new Goods(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getInt(4),
							rst.getBlob(5), rst.getInt(6), rst.getInt(7), rst.getString(8), rst.getInt(9));
					list.add(goods);
				}
				return list;
			} else if (maxindex == COMPARE_TWO) {
				ResultSet rst = GoodsDao.someGoods("生鲜水果", con);
				while (rst.next()) {
					Goods goods = new Goods(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getInt(4),
							rst.getBlob(5), rst.getInt(6), rst.getInt(7), rst.getString(8), rst.getInt(9));
					list.add(goods);
				}
				return list;
			} else if (maxindex == COMPARE_THREE) {
				ResultSet rst = GoodsDao.someGoods("书籍玩具", con);
				while (rst.next()) {
					Goods goods = new Goods(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getInt(4),
							rst.getBlob(5), rst.getInt(6), rst.getInt(7), rst.getString(8), rst.getInt(9));
					list.add(goods);
				}
				return list;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			LoadingDao.close(con);
		}
		return null;
	}

	/**
	 * 排序商品优先度
	 * 
	 * @param list
	 * @return
	 */
	public List<Goods> priorityDegree(List<Goods> list) {

		Collections.sort(list, new Comparator<Goods>() {
			@Override
			public int compare(Goods goods1, Goods goods2) {
				if (goods1.getStarLevel() > goods2.getStarLevel()) {
					return 1;
				}
				if (goods1.getStarLevel() > goods2.getStarLevel()) {
					return 0;
				}
				return -1;
			}
		});
		return list;
	}

	/**
	 * 计算商家星级度
	 * 
	 * @param goodsId
	 * @return
	 */
	public float starLevelResult(int goodsId) {
		Connection con = LoadingDao.getCon();
		int shopId = 0;
		int allMyStar = 0;
		int all = 0;
		try {
			ResultSet rs = GoodsDao.findGoodsJpg(goodsId, con);
			while (rs.next()) {
				shopId = rs.getInt(6);
			}
			rs = BusinessDao.getShopComment(shopId, con);
			while (rs.next()) {
				allMyStar += rs.getInt(6);
				all += 1;
			}
			float result = (allMyStar / (float) all);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			LoadingDao.close(con);
		}
		return (float) 0.0;
	}

	/**
	 * 得到集合数据的页数
	 * 
	 * @param list
	 * @return
	 */
	public int getPageNum(List<Goods> list) {
		int allNum = list.size();
		int page;
		if (allNum < COMPARE_FOUR) {
			page = 1;
		} else {
			if (allNum % COMPARE_FOUR == 0 && allNum != 0) {
				page = allNum / COMPARE_FOUR;
			} else {
				page = (allNum / COMPARE_FOUR) + 1;
			}
		}
		return page;
	}

	/**
	 * 实现分页
	 * 
	 * @param list
	 * @return
	 */
	public List<Goods> getPaging(List<Goods> list, int page) {
		List<Goods> resultList = new ArrayList<>();
		int allGoodsNum = list.size();
		if(allGoodsNum < COMPARE_FOUR * page) {
			for (int i = COMPARE_FOUR*page-COMPARE_FOUR; i < allGoodsNum; i++) {
				resultList.add(list.get(i));
			}
		}else {
			resultList.add(list.get(4*page-4));
			resultList.add(list.get(4*page-3));
			resultList.add(list.get(4*page-2));
			resultList.add(list.get(4*page-1));
		}
		return resultList;
	}
}
