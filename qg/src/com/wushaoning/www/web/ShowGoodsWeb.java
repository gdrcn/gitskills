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

import com.wushaoning.www.dao.BusinessDao;
import com.wushaoning.www.dao.BuyCarDao;
import com.wushaoning.www.dao.GoodsDao;
import com.wushaoning.www.dao.LoadingDao;
import com.wushaoning.www.dao.UserDao;
import com.wushaoning.www.model.Businessman;
import com.wushaoning.www.model.BuyCar;
import com.wushaoning.www.model.Goods;
import com.wushaoning.www.model.Transaction;
import com.wushaoning.www.service.LoginService;
import com.wushaoning.www.service.ShowGoodsService;

/**
 * 商家展示商品的信息处理类
 * 
 * @author 10620
 *
 */
@WebServlet("/ShowGoods")
public class ShowGoodsWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public final String JUDGE_SUCCESS = "success";
	public final String JUDGE_SHOW = "show";
	public final String JUDGE_ADD = "add";
	public final String JUDGE_MODIFY = "modify";
	public final String JUDGE_MANAGE = "manage";
	public final String JUDGE_PASS = "pass";
	public final String JUDGE_REFUSE = "refuse";
	public final String JUDGE_ALTER = "alter";
	public final String JUDGE_USERREVIEWS = "userReviews";
	public final String JUDGE_REPLY = "reply";
	public final String JUDGE_FAIL = "fail";
	public final String JUDGE_WAIT = "wait";
	
	public ShowGoodsWeb() {
		super();
	}

	/**
	 * 来源：BusinessmanShop.jsp 作用：管理商家页面
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("utf-8");

		request.getSession().removeAttribute("show");
		request.getSession().removeAttribute("add");
		request.getSession().removeAttribute("manage");
		request.getSession().removeAttribute("modify");
		request.getSession().removeAttribute("alter");
		request.getSession().removeAttribute("myGoods");
		request.getSession().removeAttribute("userReviews");
		request.getSession().removeAttribute("commentMap");

		HttpSession session = request.getSession();
		ShowGoodsService showGoodsService = new ShowGoodsService();
		Businessman businessman = (Businessman) session.getAttribute("businessman");
		LoginService loginService = new LoginService();
		String shopInfo = loginService.existShop(businessman.getId());
		Connection con = LoadingDao.getCon();

		String tips = request.getParameter("tips");
		String goodsId = request.getParameter("goodsId");
		String buyNum = request.getParameter("buyNum");
		String buyCarId = request.getParameter("buyCarId");
		try {
			if (JUDGE_SUCCESS.equals(shopInfo)) {
				if (JUDGE_SHOW.equals(tips)) {
					List<Goods> list = new ArrayList<Goods>();
					ResultSet rs = GoodsDao.findOneShopGoods(businessman.getShop(), con);
					while (rs.next()) {
						Goods goods = new Goods(rs.getString(8), rs.getInt(1), rs.getString(2), rs.getInt(4),
								rs.getBlob(5), rs.getString(3), rs.getInt(6), rs.getInt(7));
						list.add(goods);
					}
					request.getSession().setAttribute("myGoods", list);
					request.getSession().setAttribute("show", "show");
					request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
				} else if (JUDGE_ADD.equals(tips)) {
					request.getSession().setAttribute("add", "add");
					request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
				} else if (JUDGE_MODIFY.equals(tips)) {
					request.getSession().setAttribute("modify", "modify");
					request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
				} else if (JUDGE_MANAGE.equals(tips)) {
					List<BuyCar> list = new ArrayList<BuyCar>();
					ResultSet rs = BusinessDao.getOrder(businessman.getShop(), con);
					while (rs.next()) {
						BuyCar buycar = new BuyCar(rs.getInt(2), rs.getInt(7), rs.getBlob(8), rs.getInt(10),
								rs.getInt(9), rs.getString(6), rs.getInt(11), rs.getString(5), rs.getString(4),
								rs.getString(3), rs.getString(12), rs.getInt(1));
						list.add(buycar);
					}
					request.getSession().setAttribute("manage", "manage");
					request.getSession().setAttribute("myGoods", list);
					request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
				} else if (JUDGE_PASS.equals(tips) || JUDGE_REFUSE.equals(tips)) {
					if (JUDGE_PASS.equals(tips)) {
						BuyCarDao.shopUpdateBuyCar(Integer.parseInt(buyCarId), "delivery", con);
						BusinessDao.shopUpdateOrder(Integer.parseInt(buyCarId), "delivery", con);
						showGoodsService.addSalesVolume(Integer.parseInt(goodsId), Integer.parseInt(buyNum));
						showGoodsService.reduceNum(Integer.parseInt(goodsId), Integer.parseInt(buyNum));
					} else {
						BuyCarDao.shopUpdateBuyCar(Integer.parseInt(buyCarId), "sFail", con);
						BusinessDao.shopUpdateOrder(Integer.parseInt(buyCarId), "sFail", con);
					}
					response.sendRedirect("/QGaccess/ShowGoods?tips=manage");
				} else if (JUDGE_ALTER.equals(tips)) {
					request.getSession().setAttribute("alter", "alter");
					request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
				} else if (JUDGE_USERREVIEWS.equals(tips)) {
					List<Transaction> list = new ArrayList<Transaction>();
					ResultSet rs = BusinessDao.getShopComment(businessman.getShop(), con);
					while (rs.next()) {
						Transaction transaction = new Transaction(rs.getInt(2), rs.getInt(3), rs.getString(5),
								rs.getInt(6), rs.getString(8), UserDao.getUserName(rs.getInt(3), con));
						list.add(transaction);
					}
					request.getSession().setAttribute("allComment", list);
					request.getSession().setAttribute("userReviews", "userReviews");
					request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
				} else if (JUDGE_REPLY.equals(tips)) {
					String reply = request.getParameter("reply");
					String theTradeId = request.getParameter("theTradeId");
					BuyCarDao.businessmanUpdateAssess(Integer.parseInt(theTradeId), reply, con);
					request.setAttribute("msg", "回复成功");

					List<Transaction> list = new ArrayList<Transaction>();
					ResultSet rs = BusinessDao.getShopComment(businessman.getShop(), con);
					while (rs.next()) {
						Transaction transaction = new Transaction(rs.getInt(2), rs.getInt(3), rs.getString(5),
								rs.getInt(6), rs.getString(8), UserDao.getUserName(rs.getInt(3), con));
						list.add(transaction);
					}
					request.getSession().setAttribute("allComment", list);
					request.getSession().setAttribute("userReviews", "userReviews");
					request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
				}
			}else if(JUDGE_FAIL.equals(shopInfo)) {
				request.setAttribute("msg", "审核失败！");
				request.getRequestDispatcher("/ShoppingMall.jsp").forward(request, response);
			}else if(JUDGE_WAIT.equals(shopInfo)) {
				request.setAttribute("msg", "管理员正在审核中！");
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
