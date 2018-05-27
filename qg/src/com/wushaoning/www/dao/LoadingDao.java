package com.wushaoning.www.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ���Ӳ������ݿ�
 * @author 10620
 *
 */
public class LoadingDao {

	private static String lgUrl = "jdbc:mysql://localhost:3306/loginpage";

	private static String lgUserName = "root";

	private static String lgPassword = "222555";

	private static String jdbcName = "com.mysql.jdbc.Driver";

	/**
	 * ��ȡ���ݿ�����
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Connection getCon() {
		Connection con = null;
		try {
			Class.forName(jdbcName);
			try {
				con = DriverManager.getConnection(lgUrl, lgUserName, lgPassword);
			} catch (SQLException e) {
				System.out.println("���ݿ����ʧ��");
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			System.out.println("���ݿ����ʧ��");
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * �ر����ݿ�����
	 * 
	 * @param stmt
	 * @param con
	 * @throws Exception
	 */
	public static void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("���ݿ�ر�ʧ��");
			}
		} else {
			return;
		}

	}

	public void close(Statement stmt, Connection con) {
		try {
			if (stmt != null) {
				stmt.close();
			}
			else if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			System.out.println("���ݿ�ر�ʧ��");
			e.printStackTrace();
		}
	}

	public void close(Statement stmt, Connection con, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}

			if (stmt != null) {
				stmt.close();
			}

			if (con != null) {
				con.close();
			}

		} catch (SQLException e) {
			System.out.println("���ݿ�ر�ʧ��");
			e.printStackTrace();
		}
	}
}
