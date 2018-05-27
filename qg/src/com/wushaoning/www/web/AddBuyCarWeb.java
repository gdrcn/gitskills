package com.wushaoning.www.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wushaoning.www.dao.BuyCarDao;
import com.wushaoning.www.dao.LoadingDao;
import com.wushaoning.www.model.BuyCar;
import com.wushaoning.www.model.Goods;
import com.wushaoning.www.model.User;
import com.wushaoning.www.service.AddBuyCarService;
import com.wushaoning.www.service.LoginService;

/**
 * 添加购物记录到购物车的信息类
 * 
 * @author 10620
 *
 */
@WebServlet("/AddBuyCar")
public class AddBuyCarWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public final int JUDGE_MSG = -1;
	public final int JUDGE_NUM = -2;
	public final int JUDGE_PHONE = -3;
	public final int JUDGE_APAY = -4;
	public final int JUDGE_ADDRESS = -5;

	public AddBuyCarWeb() {
		super();
	}

	/**
	 * 来源：CommodityInformation.jsp/BusinessmanShop.jsps 
	 * 作用：添加商品至购物车/商家修改订单
	 * info：判断是哪一个功能 
	 * result: 判断是哪一种情况输入格式有误
	 * 当info == Null时即未添加商品至购物车的操作
	 * 添加至购物车时实现的功能有：记录用户隐性信息，记录经过折算优惠后的价格
	 * 当info != Null时即为商家修改订单的操作
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("utf-8");

		String buyNum = request.getParameter("buyNum");
		String discount = request.getParameter("discount");
		String phone = request.getParameter("phone");
		String apay = request.getParameter("apay");
		String address = request.getParameter("address");
		String info = request.getParameter("info");
		String alterGoodsid = request.getParameter("alterGoodsid");
		LoginService loginService = new LoginService();
		AddBuyCarService addBuyCarService = new AddBuyCarService();

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Goods goods = (Goods) session.getAttribute("thisGoods");

		int result = addBuyCarService.addBuyLogic(buyNum, phone, apay, address);
		if (info == null) {
			if (result == JUDGE_MSG) {
				request.setAttribute("msg", "信息栏不能为空！");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/CommodityInformation.jsp").forward(request, response);
				return;
			} else if (result == JUDGE_NUM) {
				request.setAttribute("msgNum", "购买数量格式有误！");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/CommodityInformation.jsp").forward(request, response);
				return;
			} else if (result == JUDGE_PHONE) {
				request.setAttribute("msgphone", "手机号码格式有误！");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/CommodityInformation.jsp").forward(request, response);
				return;
			} else if (result == JUDGE_APAY) {
				request.setAttribute("msgapay", "支付宝格式有误！(6-15位以内)");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/CommodityInformation.jsp").forward(request, response);
				return;
			} else if (result == JUDGE_ADDRESS) {
				request.setAttribute("msgaddress", "收获地址格式有误！");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/CommodityInformation.jsp").forward(request, response);
				return;
			} else if (!(addBuyCarService.isEnough(user.getId(), goods.getGoodsId(), buyNum, goods.getNum()))) {
				request.setAttribute("msgNum", "商家宝贝数量不足！");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/CommodityInformation.jsp").forward(request, response);
				return;
			} else if (addBuyCarService.discountLogic(discount, Integer.parseInt(buyNum), goods.getPrice(),
					goods) == JUDGE_MSG) {
				request.setAttribute("msg", "你所选购的物品总价格不足200！");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/CommodityInformation.jsp").forward(request, response);
				return;
			}

			if (addBuyCarService.isExistGoods(user.getId(), goods.getGoodsId(), Integer.parseInt(buyNum))) {
				request.setAttribute("msg", "成功更新我的购物车！");
				request.getRequestDispatcher("/CommodityInformation.jsp").forward(request, response);
				return;
			} else {
				BuyCar buycar = new BuyCar(user.getId(), goods.getGoodsId(), goods.getPicture(),
						Integer.parseInt(buyNum), goods.getShopId(), goods.getGoodsName(),
						addBuyCarService.discountLogic(discount, Integer.parseInt(buyNum), goods.getPrice(), goods),
						apay, address, phone, "wait");
				if (addBuyCarService.isAdd(buycar)) {
					user.setMyBuyCarNum(loginService.getUserBuyCarNum(user.getId()));
					addBuyCarService.latentFunction(user.getId(), goods.getGoodsId()); 
					request.getSession().removeAttribute("userBuyCarNum");
					request.getSession().setAttribute("userBuyCarNum", user.getMyBuyCarNum());
					request.setAttribute("msg", "成功添加至我的购物车！");
					request.getRequestDispatcher("/CommodityInformation.jsp").forward(request, response);
					return;
				}
			}
		} else {
			if (result == -1) {
				request.setAttribute("msg", "信息栏不能为空！");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
				return;
			} else if (result == JUDGE_NUM) {
				request.setAttribute("msgNum", "购买数量格式有误！");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
				return;
			} else if (result == JUDGE_PHONE) {
				request.setAttribute("msgphone", "手机号码格式有误！");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
				return;
			} else if (result == JUDGE_APAY) {
				request.setAttribute("msgapay", "支付宝格式有误！(6-15位以内)");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
				return;
			} else if (result == JUDGE_ADDRESS) {
				request.setAttribute("msgaddress", "收获地址格式有误！");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
				return;
			}
			Connection con = LoadingDao.getCon();
			try {
				if (!(BuyCarDao.findExistBuyCar(Integer.parseInt(alterGoodsid), con))) {
					request.setAttribute("msgid", "订单不存在！");
					retainData(request, buyNum, phone, apay, address);
					request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
					return;
				} else {
					BuyCar buyCar = new BuyCar(Integer.parseInt(alterGoodsid), Integer.parseInt(buyNum), phone, apay,
							address);
					BuyCarDao.updateExistedOrder(buyCar, con);
					request.setAttribute("msg", "修改成功!");
					request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
					return;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				LoadingDao.close(con);
			}
		}

	}

	/**
	 * 保留输入错误后的信息
	 * 
	 * @param request
	 * @param buyNum
	 * @param phone
	 * @param apay
	 * @param address
	 */
	private void retainData(HttpServletRequest request, String buyNum, String phone, String apay, String address) {
		request.setAttribute("buyNum", buyNum);
		request.setAttribute("phone", phone);
		request.setAttribute("apay", apay);
		request.setAttribute("address", address);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
