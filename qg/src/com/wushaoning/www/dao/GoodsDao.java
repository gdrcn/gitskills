package com.wushaoning.www.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wushaoning.www.model.Goods;

/**
 * 对商品实体类进行增删查改
 * 
 * @author 10620
 *
 */
public class GoodsDao {

	/**
	 * 添加商品
	 * 
	 * @return 如果成功返回1，否则返回-1
	 * @throws SQLException
	 * @throws IOException
	 */
	public static int addGoods(Goods goods, InputStream in, String discount, Connection con) throws SQLException, IOException {
		String sql = "insert into goods values(null,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, goods.getFunction());
		pstmt.setString(2, goods.getTags());
		pstmt.setInt(3, goods.getPrice());
		pstmt.setBinaryStream(4, in, in.available());
		pstmt.setInt(5, goods.getShopId());
		pstmt.setInt(6, goods.getNum());
		pstmt.setString(7, goods.getGoodsName());
		pstmt.setInt(8, 0);
		pstmt.setString(9, discount); 
		return pstmt.executeUpdate();
	}

	/**
	 * 寻找全部的商品信息
	 * 
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet allGoods(Connection con) throws SQLException {
		String sql = "select * from goods";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}
	
	/**
	 * 查找某个店铺的全部信息
	 * 
	 * @param shopId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet findOneShopGoods(int shopId, Connection con) throws SQLException {
		String sql = "select * from goods where shop_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, shopId);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	/**
	 * 查找某个商品的信息
	 * 
	 * @param goodsId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet findGoodsJpg(int goodsId, Connection con) throws SQLException {
		String sql = "select * from goods where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, goodsId);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	/**
	 * 通过商品id得到该商品的全部评论
	 * @param goodsId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static List<ResultSet> getOntGoodsComment(int goodsId, Connection con) throws SQLException{
		String sql = "SELECT * FROM trade WHERE t_goodsid = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, goodsId);
		ResultSet rs = pstmt.executeQuery();
		List<ResultSet> list = new ArrayList<ResultSet>();
		while(rs.next()) {
			sql = "SELECT * FROM shop WHERE trade_id =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, rs.getInt(1));
			ResultSet res = pstmt.executeQuery();
			list.add(res);
		}
		return list;
	}

	/**
	 * 通过商品id得到商家id
	 * 
	 * @param goodsId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int getShopId(int goodsId, Connection con) throws SQLException {
		String sql = "select * from goods where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, goodsId);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt(6);
		}
		else {
			return -1;
		}
	}
	
	/**
	 * 得到特定商品的具体信息
	 * 
	 * @param goodsId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet oneGoods(String goodsId, Connection con) throws SQLException {
		String sql = "select * from goods where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, goodsId);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}
	
	/**
	 * 查找特定标签的商品
	 * 
	 * @param search
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet someGoods(String search, Connection con) throws SQLException {
		String sql = "select * from goods where tags=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, search);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	/**
	 * 修改商品数据
	 * 
	 * @param num
	 * @param goodsId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int updateNum(int finalNum, int goodsId, Connection con) throws SQLException {
		String sql = "update goods set num=? where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, finalNum);
		pstmt.setInt(2, goodsId);
		return pstmt.executeUpdate();
	}

	/**
	 * 修改商品销售量
	 * 
	 * @param volume
	 * @param goodsId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int updateVolume(int volume, int goodsId, Connection con) throws SQLException {
		String sql = "update goods set sales_volume=? where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, volume);
		pstmt.setInt(2, goodsId);
		return pstmt.executeUpdate();
	}

	/**
	 * 修改商品信息
	 * @param parseInt
	 * @param goods
	 * @param in
	 * @param con
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static int updateGoods(int goodsId, Goods goods, InputStream in, Connection con) throws SQLException, IOException {
		String sql = "update goods set function=?, tags=? ,price=?, picture=?, num=?, goods_name=? where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, goods.getFunction());
		pstmt.setString(2, goods.getTags());
		pstmt.setInt(3, goods.getPrice());
		pstmt.setBlob(4, in, in.available());
		pstmt.setInt(5, goods.getNum());
		pstmt.setString(6, goods.getGoodsName());
		pstmt.setInt(7, goodsId);
		return pstmt.executeUpdate();
	}
	
	/**
	 * 查找模糊查询的商品
	 * 
	 * @param search
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet vagueGoods(String someSearch, Connection con) throws SQLException {
		String sql = "select * from goods where (goods_name LIKE ? or function LIKE ? or shop_id LIKE ? or tags LIKE ?) order by sales_volume ASC";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, "%" + someSearch + "%");
		pstmt.setString(2, "%" + someSearch + "%");
		pstmt.setString(3, "%" + someSearch + "%");
		pstmt.setString(4, "%" + someSearch + "%");
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

}
