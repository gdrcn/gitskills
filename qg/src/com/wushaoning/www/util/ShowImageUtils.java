package com.wushaoning.www.util;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wushaoning.www.dao.BusinessDao;
import com.wushaoning.www.dao.GoodsDao;
import com.wushaoning.www.dao.LoadingDao;
import com.wushaoning.www.dao.UserDao;

/**
 * 利用io流读取存储在数据的图片
 * 
 * @author 10620
 *
 */
@WebServlet("/ShowImageUtils")
public class ShowImageUtils extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShowImageUtils() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = LoadingDao.getCon();
		String goodsId = request.getParameter("goodsId");
		String userId = request.getParameter("userId");
		String complaintId = request.getParameter("complaintId");
		try {
			if (goodsId != null) {
				int id = Integer.parseInt(goodsId);
				ResultSet rs = GoodsDao.findGoodsJpg(id, con);
				if (rs.next()) {
					Blob b = rs.getBlob("picture");
					long size = b.length();
					byte[] bs = b.getBytes(1, (int) size);
					response.setContentType("image/jpeg");
					OutputStream outs = response.getOutputStream();
					outs.write(bs);
					outs.flush();
					rs.close();
				} else {
					rs.close();
				}
			} else if (userId != null) {
				ResultSet rs = null;
				ResultSet rs1 = null;
				rs = BusinessDao.notShop(Integer.parseInt(userId), con);
				rs1 = BusinessDao.alreadyShop(Integer.parseInt(userId), con);
				if (rs.next()) {
					Blob b = rs.getBlob("idphoto");
					long size = b.length();
					byte[] bs = b.getBytes(1, (int) size);
					response.setContentType("image/jpeg");
					OutputStream outs = response.getOutputStream();
					outs.write(bs);
					outs.flush();
					rs.close();
				} else {
					rs.close();
				}
				if (rs1.next()) {
					Blob b = rs1.getBlob("idphoto");
					long size = b.length();
					byte[] bs = b.getBytes(1, (int) size);
					response.setContentType("image/jpeg");
					OutputStream outs = response.getOutputStream();
					outs.write(bs);
					outs.flush();
					rs1.close();
				} else {
					rs1.close();
				}
			}else if(complaintId != null) {
				int id = Integer.parseInt(complaintId);
				ResultSet rs = UserDao.getOneComplaint(id, con);
				if (rs.next()) {
					Blob b = rs.getBlob("complaint_img");
					long size = b.length();
					byte[] bs = b.getBytes(1, (int) size);
					response.setContentType("image/jpeg");
					OutputStream outs = response.getOutputStream();
					outs.write(bs);
					outs.flush();
					rs.close();
				} else {
					rs.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			LoadingDao.close(con);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
