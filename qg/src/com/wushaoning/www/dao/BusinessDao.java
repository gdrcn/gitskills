package com.wushaoning.www.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ���̼�ʵ���������ɾ���
 * 
 * @author 10620
 *
 */
public class BusinessDao {
	
	/**
	 * ����ȫ��δ��˵��û�
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
	 * �����Ѿ�������û�
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
	 * ����ȫ���ѿ�����û�
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
	 * �رյ���
	 * 
	 * @param userId
	 * @param con
	 * @return �����Ƿ�رճɹ�
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
	 * ɾ��ĳ�̵����Ʒ
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
	 * �õ��̼ҵ���Ϣ
	 * 
	 * @param userId
	 * @param con
	 * @return �����̼���Ϣ��
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
	 * �õ��û��Ƿ񿪵����Ϣ
	 * 
	 * @param userId
	 * @param con
	 * @return �����̼���Ϣ��
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
	 * �õ��û���ȫ��������Ϣ
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
	 * ͨ���̼�id�õ��̼ҵĹ��ﶩ����Ϣ
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
	 * �õ�ȫ���û���ȫ��������Ϣ
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
	 * �Ƿ�������֤��ͬ���û�
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
	 * �Ƿ���ڵ���
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
	 * ����Ԥ���̼���Ϣ
	 * 
	 * @param userId 
	 * @param identifivation
	 * @param alipay
	 * @param in
	 * @param con
	 * @return ���ص�Ϊ�������������
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
	 * �����ض�δ��˵��û�
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
	 * ����Ƿ�ɹ�
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
	 * �̼���˺󶩵��Ĺ�����Ϣ
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
	 * ͨ����Ʒid�õ��̼�id
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
	 * �õ�����id
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
