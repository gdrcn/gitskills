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
import javax.servlet.http.HttpSession;

import com.wushaoning.www.dao.GoodsDao;
import com.wushaoning.www.dao.LoadingDao;
import com.wushaoning.www.model.Goods;
import com.wushaoning.www.model.User;
import com.wushaoning.www.service.GoodsService;

/**
 * 商城首页信息
 * 
 * @author 10620
 *
 */
@WebServlet("/FindGoods")
public class FindGoodsWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public final String JUDGE_ALL = "all";
	public final String JUDGE_CLOTHING = "clothing";
	public final String JUDGE_DRINKS = "drinks";
	public final String JUDGE_FRUITS = "fruits";
	public final String JUDGE_BOOKS = "books";
	public final String JUDGE_GUESS = "guess";
	public FindGoodsWeb() {
		super();
	}

	/**
	 * 来源：shoppingMall.jsp 
	 * 作用：显示商城首页的信息 
	 * someSearch： 模糊查询查找特定商品 
	 * searchGoods：通过左栏查找商品
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		
		request.getSession().removeAttribute("all");
		request.getSession().removeAttribute("clothing");
		request.getSession().removeAttribute("drinks");
		request.getSession().removeAttribute("fruits");
		request.getSession().removeAttribute("books");
		request.getSession().removeAttribute("goods");
		request.getSession().removeAttribute("guess");
		
		String searchGoods = request.getParameter("search");
		String someSearch = request.getParameter("someSearch");
		String page = request.getParameter("page");
		String page1 = request.getParameter("page1");
		String page2 = request.getParameter("page2");
		String page3 = request.getParameter("page3");
		String page4 = request.getParameter("page4");
		String page5 = request.getParameter("page5");
		
		Connection con = LoadingDao.getCon();
		GoodsService goodsService = new GoodsService();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		List<Goods> list = new ArrayList<Goods>();
		try {
			if (someSearch == null) {
				if (JUDGE_ALL.equals(searchGoods)) {
					ResultSet rs = GoodsDao.allGoods(con);
					while (rs.next()) {
						Goods goods = new Goods(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
								rs.getBlob(5), rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getInt(9));
						list.add(goods);
					}
					if(page == null || page=="") {
						 page = "1";		
					}
					int pageNum = goodsService.getPageNum(list);
					List<Goods> goodsList = goodsService.getPaging(list, Integer.parseInt(page));
					request.setAttribute("page", page);
					request.getSession().setAttribute("pagenum", pageNum);
					request.getSession().setAttribute("all", "all");
					request.getSession().setAttribute("goods", goodsList);
					request.getRequestDispatcher("/ShoppingMall.jsp").forward(request, response);
				} else if (JUDGE_CLOTHING.equals(searchGoods)) {
					ResultSet rs = GoodsDao.someGoods("服装配饰", con);
					while (rs.next()) {
						Goods goods = new Goods(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
								rs.getBlob(5), rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getInt(9));
						list.add(goods);
					}
					if(page1 == null || page1=="") {
						page1 = "1";
					}            
					int pageNum1 = goodsService.getPageNum(list);
					List<Goods> goodsList1 = goodsService.getPaging(list, Integer.parseInt(page1));
					request.setAttribute("page1", page1);
					request.getSession().setAttribute("pagenum1", pageNum1);
					request.getSession().setAttribute("goods", goodsList1);
					request.getSession().setAttribute("clothing", "clothing");
					request.getRequestDispatcher("/ShoppingMall.jsp").forward(request, response);
				} else if (JUDGE_DRINKS.equals(searchGoods)) {
					ResultSet rs = GoodsDao.someGoods("零食饮料", con);
					while (rs.next()) {
						Goods goods = new Goods(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
								rs.getBlob(5), rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getInt(9));
						list.add(goods);
					}
					if(page2 == null || page2 =="") {
						page2 = "1";
					}        
					int pageNum2 = goodsService.getPageNum(list);
					List<Goods> goodsList2 = goodsService.getPaging(list, Integer.parseInt(page2));
					request.setAttribute("page2", page2);
					request.getSession().setAttribute("pagenum2", pageNum2);
					request.getSession().setAttribute("goods", goodsList2);
					request.getSession().setAttribute("drinks", "drinks");
					request.getRequestDispatcher("/ShoppingMall.jsp").forward(request, response);
				} else if (JUDGE_FRUITS.equals(searchGoods)) {
					ResultSet rs = GoodsDao.someGoods("生鲜水果", con);
					while (rs.next()) {
						Goods goods = new Goods(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
								rs.getBlob(5), rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getInt(9));
						list.add(goods);
					}
					if(page3 == null || page3 =="") {
						 page3 = "1";
					}    
					int pageNum3 = goodsService.getPageNum(list);
					List<Goods> goodsList3 = goodsService.getPaging(list, Integer.parseInt(page3));
					request.setAttribute("page3", page3);
					request.getSession().setAttribute("pagenum3", pageNum3);
					request.getSession().setAttribute("goods", goodsList3);
					request.getSession().setAttribute("fruits", "fruits");
					request.getRequestDispatcher("/ShoppingMall.jsp").forward(request, response);
				} else if (JUDGE_BOOKS.equals(searchGoods)) {
					ResultSet rs = GoodsDao.someGoods("书籍玩具", con);
					while (rs.next()) {
						Goods goods = new Goods(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
								rs.getBlob(5), rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getInt(9));
						list.add(goods);
					}
					if(page4 == null || page4 =="") {
						 page4 = "1";
					}
					int pageNum4 = goodsService.getPageNum(list);
					List<Goods> goodsList4 = goodsService.getPaging(list, Integer.parseInt(page4));
					request.setAttribute("page4", page4);
					request.getSession().setAttribute("pagenum4", pageNum4);
					request.getSession().setAttribute("goods", goodsList4);
					request.getSession().setAttribute("books", "books");
					request.getRequestDispatcher("/ShoppingMall.jsp").forward(request, response);
				}else if(JUDGE_GUESS.equals(searchGoods)) {
					list = goodsService.getUserLike(user.getId());
					if(page5 == null || page5 =="") {
						 page5 = "1";
					}	           
					int pageNum5 = goodsService.getPageNum(list);
					List<Goods> goodsList5 = goodsService.getPaging(list, Integer.parseInt(page5));
					request.setAttribute("page5", page5);
					request.getSession().setAttribute("pagenum5", pageNum5);
					request.getSession().setAttribute("goods", goodsList5);
					request.getSession().setAttribute("guess", "guess");
					request.getRequestDispatcher("/ShoppingMall.jsp").forward(request, response);
				}
			} else {
				ResultSet rs = GoodsDao.vagueGoods(someSearch, con);
				while (rs.next()) {
					Goods goods = new Goods(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getBlob(5),
							rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getInt(9));
					     goods.setStarLevel(goodsService.starLevelResult(goods.getGoodsId()));
					list.add(goods);
				}
				list = goodsService.priorityDegree(list);
				request.getSession().setAttribute("goods", list);
				request.getRequestDispatcher("/ShoppingMall.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			LoadingDao.close(con);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
