package com.wushaoning.www.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wushaoning.www.dao.LoadingDao;
import com.wushaoning.www.dao.UserDao;
import com.wushaoning.www.model.Businessman;
import com.wushaoning.www.model.User;
import com.wushaoning.www.service.LoginService;
import com.wushaoning.www.util.DigestUtils;

/**
 * 登陆逻辑处理类
 * 
 * @author 10620
 *
 */
@WebServlet("/LoginWeb")
public class LoginWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;
	LoginService loginService = new LoginService();

	public LoginWeb() {
		super();
	}

	/**
	 * 来源：StartLogin.jsp
	 * 作用：处理登陆时的购物车单数逻辑与当前用户信息
	 * 登陆后保存用户的session以及相关内容
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		System.out.println(userName);
		password = DigestUtils.md5(password);

		if (loginService.isExistence(userName, password)) {
			User user = new User();
			Connection con = LoadingDao.getCon();
			try {
				user = UserDao.findUserSg(userName, con);
				user.setRole(UserDao.getUserRole(userName, con));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				LoadingDao.close(con);
			}
			String shopSug = loginService.existShop(user.getId());
			user.setMyBuyCarNum(loginService.getUserBuyCarNum(user.getId()));
			request.getSession().setAttribute("shopInfo", shopSug);
			if (shopSug != null) {
				Businessman businessman = new Businessman();
				businessman = loginService.changeBusinessman(businessman, user);
				businessman.setPromotion(shopSug);
				request.getSession().setAttribute("userBuyCarNum", user.getMyBuyCarNum());
				request.getSession().setAttribute("user", user);
				request.getSession().setAttribute("businessman", businessman);
				request.getRequestDispatcher("/FindGoods?search=all").forward(request, response);
				return;
			}
			request.getSession().setAttribute("userBuyCarNum", user.getMyBuyCarNum());
			request.getSession().setAttribute("user", user);
			request.getRequestDispatcher("/FindGoods?search=all").forward(request, response);
		} else {
			request.setAttribute("msgpwd", "用户名或密码错误");
			request.getRequestDispatcher("/StartLogin.jsp").forward(request, response);
		}
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doGet(request, response);
	}

}
