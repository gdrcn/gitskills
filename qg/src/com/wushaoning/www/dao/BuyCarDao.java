package com.wushaoning.www.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wushaoning.www.model.BuyCar;

/**
 * 购物车功能
 * 
 * @author 10620
 *
 */
public class BuyCarDao {

	/**
	 * 插入商铺信息
	 * @param shopId
	 * @param tradeId
	 * @param userId
	 * @param goodsImg
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int addAssessShop(int shopId, int tradeId, int userId, Blob goodsImg, Connection con) throws SQLException {
		String sql = "insert into shop values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, shopId);
		pstmt.setInt(2, tradeId);
		pstmt.setInt(3, userId);
		pstmt.setBlob(4, goodsImg);
		pstmt.setString(5, null);
		pstmt.setInt(6, 5);
		pstmt.setString(7, "no");
		pstmt.setString(8, null);
		pstmt.setString(9, "no");
		return pstmt.executeUpdate();
	}
	
	/**
	 * 添加购物车记录
	 * 
	 * @param buycar
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int addBuyCar(BuyCar buycar, Connection con) throws SQLException {
		String sql = "insert into buycar values(null,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, buycar.getMyId());
		pstmt.setString(2, buycar.getMyPhoneNumber());
		pstmt.setString(3, buycar.getMyAddress());
		pstmt.setString(4, buycar.getMyAlipay());
		pstmt.setString(5, buycar.getTheGoodsName());
		pstmt.setInt(6, buycar.getTheGoodsId());
		pstmt.setBlob(7, buycar.getTheGoodsImg());
		pstmt.setInt(8, buycar.getTheShopId());
		pstmt.setInt(9, buycar.getMyBuyNum());
		pstmt.setInt(10, buycar.getTheGoodsPrice());
		pstmt.setString(11, "wait");
		return pstmt.executeUpdate();
	}

	/**
	 * 修改购物车信息
	 * 
	 * @param theUserId
	 * @param theGoodsId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int alterBuyCar(int buyCarId, String sign, Connection con) throws SQLException {
		String sql = "update buycar set buygoods=? where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, sign);
		pstmt.setInt(2, buyCarId);
		return pstmt.executeUpdate();
	}
	
	/**
	 * 插入订单数据
	 * 
	 * @param userId
	 * @param goodsId
	 * @param businessmanId
	 * @param shopId
	 * @param sign
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int addTrade(int userId, int goodsId, int businessmanId, int shopId, int buyCarId, String sign, Connection con)
			throws SQLException {
		String sql = "insert into trade values(null,?,?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, businessmanId);
		pstmt.setInt(2, userId);
		pstmt.setInt(3, shopId);
		pstmt.setInt(4, goodsId);
		pstmt.setString(5, sign);
		pstmt.setInt(6, buyCarId);
		return pstmt.executeUpdate();
	}
	
	/**
	 * 商家回复用户的信息
	 * @param userId
	 * @param comment
	 * @param reply
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int businessmanUpdateAssess(int tradeId, String reply, Connection con) throws SQLException {
		String sql = "update shop set reply=?, existreply=? where trade_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, reply);
		pstmt.setString(2, "yes");
		pstmt.setInt(3, tradeId);
		return pstmt.executeUpdate();
	}
	
	/**
	 * 判断是否存在id的购物车
	 * @param buyCarId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static boolean findExistBuyCar(int buyCarId, Connection con) throws SQLException {
		String sql = "select * from buycar where id=? and buygoods=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, buyCarId);
		pstmt.setString(2, "auditing");
		ResultSet rs = pstmt.executeQuery();
		return rs.next();
	}
	
	/**
	 * 查找以往的购物记录
	 * 
	 * @param id
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet formerBuyCar(int userId, Connection con) throws SQLException {
		String sql = "select * from buycar where user_id=? and (buygoods=? or buygoods=? or buygoods=? or buygoods=? or buygoods=?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, userId);
		pstmt.setString(2, "success");
		pstmt.setString(3, "fail");
		pstmt.setString(4, "sFail");
		pstmt.setString(5, "delivery");
		pstmt.setString(6, "evaluated");
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}
	
	/**
	 * 得到用户的全部宝贝数量
	 * 
	 * @param userId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet getBuyCarNum(int userId, Connection con) throws SQLException {
		String sql = "select * from buycar where user_id=? and buygoods=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, userId);
		pstmt.setString(2, "wait");
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}
	
	/**
	 * 得到购物车中的商品价格
	 * @param buyCarId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int getGoodsPrice(int buyCarId, Connection con) throws SQLException {
		String sql = "select * from buycar where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, buyCarId);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		return rs.getInt(11);
	}
	
	/**
	 * 得到某用户购物车某商品的购买数量
	 * 
	 * @param userId
	 * @param goodsId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int getOneGoodsNum(int userId, int goodsId, Connection con) throws SQLException {
		String sql = "select * from buycar where user_id=? and goods_id=? and buygoods=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, userId);
		pstmt.setInt(2, goodsId);
		pstmt.setString(3, "wait");
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt(10);
		}
		else {
			return 0;
		}	
	}
	
	/**
	 * 通过购物车id得到订单id
	 * @param buyCarId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int getTradeId(int buyCarId, Connection con) throws SQLException{
		String sql = "select * from trade where buycar_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, buyCarId);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		return rs.getInt(1);
	}
	
	/**
	 * 得到某个购物车的全部信息
	 * @return
	 * @throws SQLException 
	 */
	public static ResultSet getUserBuyCar(int buyCarId, Connection con) throws SQLException {
		String sql = "select * from buycar where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, buyCarId);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}
	
	/**
	 * 购物车是否已存在某商品
	 * 
	 * @param userId
	 * @param goodsId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static boolean haveUserGoods(int userId, int goodsId, Connection con) throws SQLException {
		String sql = "select * from buycar where user_id=? and goods_id=? and buygoods=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, userId);
		pstmt.setInt(2, goodsId);
		pstmt.setString(3, "wait");
		ResultSet rs = pstmt.executeQuery();
		return rs.next();
	}
	
	/**
	 * 取消订单后的购物车信息
	 * 
	 * @param theUserId
	 * @param theGoodsId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int missBuyCar(int buyCarId, Connection con) throws SQLException {
		String sql = "update buycar set buygoods=? where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, "fail");
		pstmt.setInt(2, buyCarId);
		return pstmt.executeUpdate();
	}
	
	/**
	 * 查找待定购买购物车记录
	 * 
	 * @param userId
	 * @param goodId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet myBuyCar(int userId, String sign, Connection con) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select * from buycar where user_id=? and buygoods=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, userId);
		pstmt.setString(2, sign);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	/**
	 * 移除购物车订单
	 * @param userId
	 * @param goodsId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int removeBuyCar(int buyCarId, Connection con) throws SQLException {
		String sql = "delete from buycar where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, buyCarId);
		return pstmt.executeUpdate();
	}

	/**
	 * 商家审核后的购物信息
	 * 
	 * @param theUserId
	 * @param theGoodsId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int shopUpdateBuyCar(int buyCarId, String sign, Connection con) throws SQLException {
		String sql = "update buycar set buygoods=? where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, sign);
		pstmt.setInt(2, buyCarId);
		return pstmt.executeUpdate();
	}
	
	/**
	 * 将评论信息插入shop
	 * @param tradeId
	 * @param starLevel
	 * @param assessTxt
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int updateAssess(int tradeId, int starLevel, String assessTxt, Connection con) throws SQLException {
		String sql = "update shop set starlevel=?, evaluate=?, existcomment=? where trade_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, starLevel);
		pstmt.setString(2, assessTxt);
		pstmt.setString(3, "yes");
		pstmt.setInt(4, tradeId);
		return pstmt.executeUpdate();
	}
	
	/**
	 * 商家修改订单信息
	 * @param buyCar
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int updateExistedOrder(BuyCar buyCar, Connection con) throws SQLException {
		String sql = "update buycar set buygoods_num=?, user_address=?, user_phone=?, user_alipay=? where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, buyCar.getMyBuyNum());
		pstmt.setString(2, buyCar.getMyAddress());
		pstmt.setString(3, buyCar.getMyPhoneNumber());
		pstmt.setString(4, buyCar.getMyAlipay());
		pstmt.setInt(5, buyCar.getId());
		return pstmt.executeUpdate();
	}
	
	/**
	 * 更新购买的商品数量
	 * 
	 * @param email
	 * @param rePwd
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int updateGoodsNum(int userId, int goodsId, int num, Connection con) throws SQLException {
		String sql = "update buycar set buygoods_num=? where user_id=? and goods_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, num);
		pstmt.setInt(2, userId);
		pstmt.setInt(3, goodsId);
		return pstmt.executeUpdate();
	}
	
	/**
	 * 更改订单的数据处理
	 * @param userId
	 * @param goodsId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int updateOrder(int buyCarId, String sign, Connection con) throws SQLException {
		String sql = "update trade set situation=? where buycar_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, sign);
		pstmt.setInt(2, buyCarId);
		return pstmt.executeUpdate();
	}
	

	




}
