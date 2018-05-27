package com.wushaoning.www.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wushaoning.www.model.User;

/**
 * 进行用户的数据库操作
 * 
 * @author 10620
 *
 */
public class UserDao {
	
	/**
	 * 添加密码保护
	 * 
	 * @param user
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int addPwdPtd(User user, Connection con) throws SQLException {
		String sql = "insert into user_pwdprotected values(null,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, user.getEmail());
		pstmt.setString(2, user.getPwdQue());
		pstmt.setString(3, user.getPwdPtd());
		return pstmt.executeUpdate();
	}
	
	/**
	 * 添加用户权限
	 * 
	 * @return 如果成功返回1，否则返回-1
	 * @throws SQLException
	 */
	public static int addRole(int id, Connection con) throws SQLException {
		String sql = "insert into user_role values(null,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, id);
		pstmt.setString(2, "3");
		return pstmt.executeUpdate();
	}
	
	/**
	 * 添加用户
	 * 
	 * @return 如果成功返回1，否则返回-1
	 * @throws SQLException
	 */
	public static int addUser(User user, Connection con) throws SQLException {
		String sql = "insert into user values(null,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		pstmt.setString(3, user.getGender());
		pstmt.setString(4, user.getPhoneNumber());
		pstmt.setString(5, user.getqq());
		pstmt.setString(6, user.getWechat());
		pstmt.setString(7, user.getAddress());
		pstmt.setString(8, user.getEmail());
		return pstmt.executeUpdate();
	}

	/**
	 * 查找全部用户信息
	 * 
	 * @throws SQLException
	 */
	public static ResultSet allUser(Connection con) throws SQLException {
		String sql = "select * from user";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}
	
	/**
	 * 删除用户
	 * 
	 * @return 如果成功返回true，否则返回flase
	 * @throws SQLException
	 */
	public static int deleteUser(String userName, Connection con) throws SQLException {
		String sql = "delete from user where userName=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userName);
		return pstmt.executeUpdate();
	}

	/**
	 * 删除用户
	 * 
	 * @return 如果成功返回true，否则返回flase
	 * @throws SQLException
	 */
	public static int deleteUser(int userId, Connection con) throws SQLException {
		String sql = "delete from user where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, userId);
		System.out.println(userId);
		return pstmt.executeUpdate();
	}
	
	/**
	 * 判断是否存在用户名为userName用户
	 * 
	 * @param userName
	 * @return 存在返回true
	 * @throws SQLException
	 */
	public static int fineUserId(String userName, Connection con) throws SQLException {
		String sql = "select * from user where userName=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userName);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		return rs.getInt(1);
	}
	
	/**
	 * 判断是否存在权限为用户
	 * 
	 * @param userName
	 * @return 存在返回true
	 * @throws SQLException
	 */
	public static boolean finePermission(String permission, Connection con) throws SQLException {
		String sql = "select * from permission where permission=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, permission);
		ResultSet rs = pstmt.executeQuery();
		return rs.next();
	}
	
	/**
	 * 判断是否存在用户名为userName用户
	 * 
	 * @param userName
	 * @return 存在返回true
	 * @throws SQLException
	 */
	public static boolean fineUsername(String userName, Connection con) throws SQLException {
		String sql = "select * from user where userName=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userName);
		ResultSet rs = pstmt.executeQuery();
		return rs.next();
	}
	
	/**
	 * 判断是否存在角色名为用户
	 * 
	 * @param userName
	 * @return 存在返回true
	 * @throws SQLException
	 */
	public static boolean finedRole(String role, Connection con) throws SQLException {
		String sql = "select * from role where role_name=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, role);
		ResultSet rs = pstmt.executeQuery();
		return rs.next();
	}
	
	/**
	 * 
	 * @param user
	 * @param con
	 * @return 若用户存在，则返回用户信息
	 * @throws Exception
	 */
	public static User findUserSg(String userName, Connection con) throws Exception {
		User resultUser = null;
		String sql = "select * from user where userName=? ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userName);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			resultUser = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
		}
		return resultUser;
	}
	
	/**
	 * 验证密保以及邮箱问题
	 * 
	 * @param user
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static boolean findPwdPtd(User user, Connection con) throws SQLException {
		String sql = "select * from user_pwdprotected where user_email=? and user_pwdquestion=? and user_pwd=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, user.getEmail());
		pstmt.setString(2, user.getPwdQue());
		pstmt.setString(3, user.getPwdPtd());
		ResultSet rs = pstmt.executeQuery();
		return rs.next();
	}
	
	/**
	 * 判断是否存在邮箱为email用户
	 * 
	 * @param userName
	 * @return 存在返回true
	 * @throws SQLException
	 */
	public static boolean fineUseremail(String email, Connection con) throws SQLException {
		String sql = "select * from user where email=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, email);
		ResultSet rs = pstmt.executeQuery();
		return rs.next();
	}
	
	/**
	 * 判断是否存在用户
	 * 
	 * @param userName
	 * @param password
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static boolean fineUser(String userName, String password, Connection con) throws SQLException {
		String sql = "select * from user where userName=? and password=? ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userName);
		pstmt.setString(2, password);
		ResultSet rs = pstmt.executeQuery();
		return rs.next();
	}
	
	/**
	 * 插入隐形信息，例如浏览记录, 积分， 经验，身份
	 * @param userId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int insertLatentFunction(int userId, Connection con) throws SQLException {
		String sql = "insert into user_relevant values(null,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, userId);
		pstmt.setInt(2, 0);
		pstmt.setInt(3, 0);
		pstmt.setInt(4, 0);
		pstmt.setInt(5, 0);
		pstmt.setInt(6, 0);
		pstmt.setInt(7, 0);
		pstmt.setString(8, "ordinaryUser");
		return pstmt.executeUpdate(); 
	}
	
	/**
	 * 找到全部申述
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet getAllComplaint(Connection con) throws SQLException {
		String sql = "select * from complaint where situation=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, "no");
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}
	
	/**
	 * 查找某个用户的全部申诉
	 * @param userId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet getMyComplaint(int userId, Connection con) throws SQLException {
		String sql = "select * from complaint where user_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, userId);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}
	
	/**
	 * 找到全部申述
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet getOneComplaint(int complaintId, Connection con) throws SQLException {
		String sql = "select * from complaint where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, complaintId);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}
	
	/**
	 * 判断是否存在用户名为userName, 密码为password的用户
	 * 
	 * @param userName
	 *            ， password
	 * @return 存在返回true
	 * @throws SQLException
	 */
	public static String getUser(String userName, String password, Connection con) throws SQLException {
		String sql = "select * from user where userName=? AND password=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userName);
		pstmt.setString(2, password);
		ResultSet rs = pstmt.executeQuery();
		String role = rs.getString(3);
		return role;
	}

	/**
	 * 判断是否存在角色
	 * 
	 * @param userName
	 * @return 存在返回true
	 * @throws SQLException
	 */
	public static int getRole(String role, Connection con) throws SQLException {
		String sql = "select * from role where role_name=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, role);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		int result = rs.getInt(1);
		return result;
	}

	/**
	 * 
	 * @return 通过用户名获得角色等级
	 * @throws SQLException
	 */
	public static String getUserRole(String userName, Connection con) throws SQLException {
		String sql = "SELECT * FROM (SELECT user.*, user_role.* FROM USER INNER JOIN user_role ON (user.id = user_role.user_id))result WHERE username=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userName);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		String role = rs.getString(12);
		return role;
	}

	/**
	 * 通过用户id得到其角色
	 * 
	 * @param userId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static String getUserRole(int userId, Connection con) throws SQLException {
		String sql = "SELECT * FROM (SELECT user.*, user_role.* FROM USER INNER JOIN user_role ON (user.id = user_role.user_id))result WHERE id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, userId);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		String role = rs.getString(12);
		return role;
	}

	/**
	 * 得到用户的隐性信息
	 * @param userId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet getAllIntegral(int userId, Connection con) throws SQLException {
		String sql = "select * from user_relevant where userid=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, userId);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	/**
	 * 通过用户id得到游湖名字
	 * 
	 * @param userId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static String getUserName(int userId, Connection con) throws SQLException {
		String sql = "select * from user where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, userId);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		return rs.getString(2);
	}

	/**
	 * 得到用户的积分
	 * @param userId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int getIntegral(int userId, Connection con) throws SQLException {
		String sql = "select * from user_relevant where userid=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, userId);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		return rs.getInt(7);
	}
	
	/**
	 * 插入投诉信息
	 * @param userId
	 * @param tradeId
	 * @param theComplaint
	 * @param in
	 * @param con
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public static int addComplaint(int userId, int tradeId, String theComplaint, InputStream in, Connection con) throws SQLException, IOException {
		String sql = "insert into complaint value(null, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, tradeId);
		pstmt.setInt(2, userId);
		pstmt.setBinaryStream(3, in, in.available());
		pstmt.setString(4, theComplaint);
		pstmt.setString(5, "no");
		return pstmt.executeUpdate();
	}

	/**
	 * 增加用户积分
	 * @param userId
	 * @param reduceIntegral
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int raiseRelevant(int userId, int raiseIntegral, Connection con) throws SQLException {
		String sql = "update user_relevant set integral = integral + ? , experience = experience + ? where userid=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, raiseIntegral);
		pstmt.setInt(2, raiseIntegral);
		pstmt.setInt(3, userId);
		return pstmt.executeUpdate();
	}

	/**
	 * 将普通用户升级为管理员
	 * @param userId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int upadteAdministrators(int userId, Connection con) throws SQLException {
		String sql = "update user_role set role_id=? where user_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, "1");
		pstmt.setInt(2, userId);
		return pstmt.executeUpdate();
	}
	
	/**
	 * 申诉完成后
	 * @param complaintId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int upadteComplaint(int complaintId, Connection con) throws SQLException {
		String sql = "update complaint set situation=? where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, "yes");
		pstmt.setInt(2, complaintId);
		return pstmt.executeUpdate();
	}

	/**
	 * 修改密码
	 * 
	 * @param email
	 * @param rePwd
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int updatePwd(String email, String rePwd, Connection con) throws SQLException {
		String sql = "update user set password=? where email=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, rePwd);
		pstmt.setString(2, email);
		return pstmt.executeUpdate();
	}

	/**
	 * 用户修改信息
	 * @param userName
	 * @param phoneNumber
	 * @param qq
	 * @param wechat
	 * @param email
	 * @param userId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int updateUserSug(String userName, String phoneNumber, String qq, String wechat, String email,
			int userId, Connection con) throws SQLException {
		String sql = "update user set username=?, phoneNumber=?, qq=?, wechat=?, email=? where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userName);
		pstmt.setString(2, phoneNumber);
		pstmt.setString(3, qq);
		pstmt.setString(4, wechat);
		pstmt.setString(5, email);
		pstmt.setInt(6, userId);
		return pstmt.executeUpdate();
	}

	/**
	 * 更新用户浏览商品的记录
	 * @param userId
	 * @param num
	 * @param goodsTags
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int upadteUserRelevant(int userId, String goodsTags, Connection con) throws SQLException {
		String tags = null;
		if ("服装配饰".equals(goodsTags)) {
			tags = "goods_1";
		} else if ("零食饮料".equals(goodsTags)) {
			tags = "goods_2";
		} else if ("生鲜水果".equals(goodsTags)) {
			tags = "goods_3";
		} else if ("书籍玩具".equals(goodsTags)) {
			tags = "goods_4";
		}
		String sql = "update user_relevant set " + tags + " = " + tags + " + 1 where userid=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, userId);
		return pstmt.executeUpdate();
	}

	/**
	 * 当用户下单后为与用户的隐性记录加分
	 * @param userId
	 * @param goodsPrice
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int upadteUserRelevant(int userId, int goodsPrice, Connection con) throws SQLException {
		String sql = "update user_relevant set integral = integral + ?,  experience = experience + ? where userid=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, goodsPrice);
		pstmt.setInt(2, goodsPrice);
		pstmt.setInt(3, userId);
		return pstmt.executeUpdate();
	}
	
	/**
	 * 减少订单的价格
	 * @param buyCarId
	 * @param reduceIntegral
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int updateTrade(int buyCarId, int reducePrice, Connection con) throws SQLException {
		String sql = "update buycar set buygoods_price = buygoods_price - ? where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, reducePrice);
		pstmt.setInt(2, buyCarId);
		return pstmt.executeUpdate();
	}
	
	/**
	 * 减少用户积分
	 * @param userId
	 * @param reduceIntegral
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int updateRelevant(int userId, int reduceIntegral, Connection con) throws SQLException {
		String sql = "update user_relevant set integral = integral - ? where userid=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, reduceIntegral);
		pstmt.setInt(2, userId);
		return pstmt.executeUpdate();
	}
	
	/**
	 * 当用户经验达到一定程度便升级
	 * @param userId
	 * @param reduceIntegral
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static int updateRelevant(int userId, int experience, String degree, Connection con) throws SQLException {
		String sql = "update user_relevant set experience = experience - ?, degree=? where userid=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, experience);
		pstmt.setString(2, degree);
		pstmt.setInt(3, userId);
		return pstmt.executeUpdate();
	}

}
