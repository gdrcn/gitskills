package com.wushaoning.www.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 对商家实体类进行增删查改
 * 
 * @author 10620
 *
 */
public class BusinessDao {
	
	/**
	 * 查找全部未审核的用户
	 * 
	 * @throws SQLException
	 */
	public static ResultSet allNotShop(Connection con) throws SQLException {
		String sql = "select * from businessman where promotion=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, "wait");
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	/**
	 * 查找已经开店的用户
	 * 
	 * @throws SQLException
	 */
	public static ResultSet alreadyShop(int userId, Connection con) throws SQLException {
		String sql = "select * from businessman where promotion=? and user_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, "success");
		pstmt.setInt(2, userId);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	/**
	 * 查找全部已开店的用户
	 * 
	 * @throws SQLException
	 */
	public static ResultSet allShop(Connection con) throws SQLException {
		String sql = "select * from businessman where promotion=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, "success");
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	/**
	 * 关闭店铺
	 * 
	 * @param userId
	 * @param con
	 * @return 返回是否关闭成功
	 * @throws SQLException
	 */
	public static int closeShop(int userId, Connection con) throws SQLException {
		String sql = "update businessman set promotion=? where user_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, "fail");
		pstmt.setInt(2, userId);
		return pstmt.executeUpdate();
	}

	/**
	 * 删除某商店的商品
	 * @param shopId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int deleteShop(int shopId, Connection con) throws SQLException {
		String sql = "delete from goods where shop_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, shopId);
		return pstmt.executeUpdate();
	}
	
	/**
	 * 得到商家的信息
	 * 
	 * @param userId
	 * @param con
	 * @return 返回商家信息集
	 * @throws SQLException
	 */
	public static ResultSet findBusinessmanSg(int userId, Connection con) throws SQLException {
		String sql = "select * from businessman where user_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, userId);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	/**
	 * 得到用户是否开店的信息
	 * 
	 * @param userId
	 * @param con
	 * @return 返回商家信息集
	 * @throws SQLException
	 */
	public static ResultSet findShop(int userId, Connection con) throws SQLException {
		String sql = "select * from businessman where user_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, userId);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	/**
	 * 得到用户的全部订单信息
	 * 
	 * @param userId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet getMyComment(int userId, Connection con) throws SQLException {
		String sql = "SELECT * FROM shop WHERE user_id=?";
		PreparedStatement pstmt;
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, userId);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	/**
	 * 
	 * 通过商家id得到商家的购物订单信息
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet getOrder(int shopId, Connection con) throws SQLException {
		String sql = "select * from buycar where goods_shopid=? and buygoods=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, shopId);
		pstmt.setString(2, "auditing");
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	/**
	 * 得到全部用户的全部评论信息
	 * 
	 * @param shopId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet getShopComment(int shopId, Connection con) throws SQLException {
		String sql = "SELECT * FROM shop WHERE id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, shopId);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	/**
	 * 是否存在身份证相同的用户
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static boolean haveRealId(String realId, Connection con) throws SQLException {
		String sql = "select * from businessman where identification=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, realId);
		ResultSet rs = pstmt.executeQuery();
		return rs.next();
	}

	/**
	 * 是否存在店铺
	 * 
	 * @throws SQLException
	 */
	public static boolean haveShop(int userId, Connection con) throws SQLException {
		String sql = "select * from businessman where user_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, userId);
		ResultSet rs = pstmt.executeQuery();
		return rs.next();
	}

	/**
	 * 插入预备商家信息
	 * 
	 * @param userId 
	 * @param identifivation
	 * @param alipay
	 * @param in
	 * @param con
	 * @return 返回的为插入的数据条数
	 * @throws SQLException
	 * @throws IOException
	 */
	public static int insertBusinessman(int userId, String identifivation, String alipay, InputStream in,
			Connection con) throws SQLException, IOException {
		String sql = "insert into businessman value(null, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, userId);
		pstmt.setString(2, identifivation);
		pstmt.setString(3, alipay);
		pstmt.setBinaryStream(4, in, in.available());
		pstmt.setString(5, "wait");
		pstmt.setInt(6, userId);
		return pstmt.executeUpdate();
	}
	
	/**
	 * 查找特定未审核的用户
	 * 
	 * @throws SQLException
	 */
	public static ResultSet notShop(int userId, Connection con) throws SQLException {
		String sql = "select * from businessman where promotion=? and user_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, "wait");
		pstmt.setInt(2, userId);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	/**
	 * 审核是否成功
	 * 
	 * @param userId
	 * @param pass
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int passShop(int userId, String pass, Connection con) throws SQLException {
		String sql = "update businessman set promotion=? where user_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, pass);
		pstmt.setInt(2, userId);
		return pstmt.executeUpdate();
	}

	/**
	 * 商家审核后订单的购物信息
	 * 
	 * @param theUserId
	 * @param theGoodsId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int shopUpdateOrder(int buyCarId, String sign, Connection con) throws SQLException {
		String sql = "update trade set situation=? where buycar_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, sign);
		pstmt.setInt(2, buyCarId);
		return pstmt.executeUpdate();
	}

	/**
	 * 通过商品id得到商家id
	 * 
	 * @param shopId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int theBusinessmanId(int shopId, Connection con) throws SQLException {
		String sql = "select * from businessman where shop_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, shopId);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		return rs.getInt(1);
	}

	/**
	 * 得到店铺id
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static int theShopId(int userId, Connection con) throws SQLException {
		String sql = "select * from businessman where user_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, userId);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		return rs.getInt(7);
	}

}
