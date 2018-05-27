package com.wushaoning.www.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wushaoning.www.dao.GoodsDao;
import com.wushaoning.www.dao.LoadingDao;
import com.wushaoning.www.dao.UserDao;
import com.wushaoning.www.model.Goods;
import com.wushaoning.www.model.Transaction;
import com.wushaoning.www.service.GoodsService;

/**
 * 商城具体商品页面信息处理类
 * 
 * @author 10620
 *
 */
@WebServlet("/BuyGoodsWeb")
public class BuyGoodsWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BuyGoodsWeb() {
		super();
	}

	/**
	 * 作用：通过商城首页可以跳转到某个特定商品的具体信息栏
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		
		Connection con = LoadingDao.getCon();
		request.getSession().removeAttribute("thisGoods");
		request.getSession().removeAttribute("allComment");
		String goodsId = request.getParameter("goodsId");
		GoodsService goodsService = new GoodsService();
	
		List<Transaction> list = new ArrayList<Transaction>();
		Goods goods = null;
		try {
			ResultSet rs = GoodsDao.oneGoods(goodsId, con);
			while (rs.next()) {
				goods = new Goods(rs.getString(8), rs.getInt(1), rs.getString(2), rs.getInt(4), rs.getBlob(5),
						rs.getString(3), rs.getInt(6), rs.getInt(7), rs.getInt(9));
				goods.setDiscount(rs.getString(10));
				goods.setStarLevel(goodsService.starLevelResult(goods.getGoodsId()));
			}
			List<ResultSet> rList = GoodsDao.getOntGoodsComment(Integer.parseInt(goodsId), con);
			for (int i = 0; i < rList.size(); i++) {
				rs = (ResultSet) rList.get(i);
				while (rs.next()) {
					Transaction transaction = new Transaction(rs.getInt(2), rs.getInt(3), rs.getString(5), rs.getInt(6),
							rs.getString(8), UserDao.getUserName(rs.getInt(3), con));
					list.add(transaction);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			LoadingDao.close(con);
		}
		request.getSession().setAttribute("allComment", list);
		request.getSession().setAttribute("thisGoods", goods);
		request.getRequestDispatcher("/CommodityInformation.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
