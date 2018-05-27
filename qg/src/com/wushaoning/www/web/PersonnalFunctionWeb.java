package com.wushaoning.www.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wushaoning.www.dao.BusinessDao;
import com.wushaoning.www.dao.LoadingDao;
import com.wushaoning.www.dao.UserDao;
import com.wushaoning.www.model.Businessman;
import com.wushaoning.www.model.Transaction;
import com.wushaoning.www.model.User;
import com.wushaoning.www.service.PersonnalService;

/**
 * 用户个人主页信息处理
 * 
 * @author 10620
 *
 */
@WebServlet("/PersonnalFunctionWeb")
public class PersonnalFunctionWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public final int DIGIT_TWO = 2;
	public final int DIGIT_THREE = 3;
	public final int DIGIT_FOUR = 4;
	public final String JUDGE_DOPE = "dope";
	public final String JUDGE_BASIC = "basic";
	public final String JUDGE_MODIFY = "modify";
	public final String JUDGE_COMPLAINT = "complaint";
	public final String JUDGE_CHAT = "chat";
	public final String JUDGE_REALCHAT = "realChat";
	public final String JUDGE_ALTER = "alter";
	public final String JUDGE_MYCOMPLAINT = "myComplaint";
	
	public PersonnalFunctionWeb() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("utf-8");

		request.getSession().removeAttribute("dope");
		request.getSession().removeAttribute("myTrade");
		request.getSession().removeAttribute("dope");
		request.getSession().removeAttribute("basic");
		request.getSession().removeAttribute("businessmanBasic");
		request.getSession().removeAttribute("userBasic");
		request.getSession().removeAttribute("modify");
		request.getSession().removeAttribute("complaint");
		request.getSession().removeAttribute("myComplaint");
		request.getSession().removeAttribute("chat");

		ServletContext application = this.getServletContext();
		String info = request.getParameter("info");
		String sign = request.getParameter("sign");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Businessman businessman = (Businessman) session.getAttribute("businessman");
		PersonnalService personnalService = new PersonnalService();
		Connection con = LoadingDao.getCon();
		try {
			if (sign == null) {
				if (JUDGE_DOPE.equals(info)) {
					List<Transaction> list = new ArrayList<Transaction>();
					ResultSet rs = BusinessDao.getMyComment(user.getId(), con);
					while (rs.next()) {
						Transaction transaction = new Transaction(rs.getInt(2), rs.getInt(3), rs.getString(5),
								rs.getInt(6), rs.getString(8), UserDao.getUserName(rs.getInt(3), con));
						list.add(transaction);
					}
					request.getSession().setAttribute("myTrade", list);
					request.getSession().setAttribute("dope", "dope");
					request.getRequestDispatcher("/Personal.jsp").forward(request, response);
				} else if (JUDGE_BASIC.equals(info)) {
					List<User> list = new ArrayList<User>();
					if (businessman != null) {
						personnalService.supplementInformation(businessman);
						list.add(businessman);
						request.getSession().setAttribute("businessmanBasic", list);
						request.getSession().setAttribute("basic", "basic");
						request.getRequestDispatcher("/Personal.jsp").forward(request, response);
					} else {
						personnalService.supplementInformation(user);
						list.add(user);
						request.getSession().setAttribute("userBasic", list);
						request.getSession().setAttribute("basic", "basic");
						request.getRequestDispatcher("/Personal.jsp").forward(request, response);
					}
				} else if (JUDGE_MODIFY.equals(info)) {
					request.getSession().setAttribute("modify", "modify");
					request.getRequestDispatcher("/Personal.jsp").forward(request, response);
				} else if (JUDGE_COMPLAINT.equals(info)) {
					request.getSession().setAttribute("complaint", "complaint");
					request.getRequestDispatcher("/Personal.jsp").forward(request, response);
				} else if (JUDGE_CHAT.equals(info)) {
					request.getSession().setAttribute("chat", "chat");
					request.getRequestDispatcher("/Personal.jsp").forward(request, response);
				} else if (JUDGE_REALCHAT.equals(info)) {
					List<String> allMessage = (List<String>) application.getAttribute("messages");
					if (allMessage == null) {
						List<String> message = new ArrayList<String>();
						application.setAttribute("messages", message);
						allMessage = (List<String>) application.getAttribute("messages");
					}
					String say = request.getParameter("say");
					if (!("".equals(say))) {
						allMessage.add(say);
					}
					application.setAttribute("messages", allMessage);
					request.getSession().setAttribute("chat", "chat");
					request.getRequestDispatcher("/Personal.jsp").forward(request, response);
				} else if (JUDGE_ALTER.equals(info)) {
					String userName = request.getParameter("userName");
					String phoneNumber = request.getParameter("phoneNumber");
					String qq = request.getParameter("QQ");
					String wechat = request.getParameter("wechat");
					String email = request.getParameter("email");
					PrintWriter out = response.getWriter();
					if (personnalService.alterLogic(userName, phoneNumber, qq, wechat, email) == -1) {
						request.setAttribute("msg", "信息栏不能为空！");
						request.getSession().setAttribute("modify", "modify");
						request.getRequestDispatcher("/Personal.jsp").forward(request, response);
						return;
					} else if (personnalService.alterLogic(userName, phoneNumber, qq, wechat, email) == 1) {
						request.setAttribute("msgemail", "邮箱格式有误！");
						request.getSession().setAttribute("modify", "modify");
						request.getRequestDispatcher("/Personal.jsp").forward(request, response);
						return;
					} else if (personnalService.alterLogic(userName, phoneNumber, qq, wechat, email) == DIGIT_TWO) {
						request.setAttribute("msgusername", "用户名格式有误！");
						request.getSession().setAttribute("modify", "modify");
						request.getRequestDispatcher("/Personal.jsp").forward(request, response);
						return;
					} else if (personnalService.alterLogic(userName, phoneNumber, qq, wechat, email) == DIGIT_THREE) {
						request.setAttribute("msgemail", "用户名已存在！");
						request.getSession().setAttribute("modify", "modify");
						request.getRequestDispatcher("/Personal.jsp").forward(request, response);
						return;
					} else if (personnalService.alterLogic(userName, phoneNumber, qq, wechat, email) == DIGIT_FOUR) {
						request.setAttribute("msgusername", "邮箱已存在！");
						request.getSession().setAttribute("modify", "modify");
						request.getRequestDispatcher("/Personal.jsp").forward(request, response);
						return;
					}
					UserDao.updateUserSug(userName, phoneNumber, qq, wechat, email, user.getId(), con);
					out.println("信息修改成功，3秒后将跳转至登陆页面！");
					response.setHeader("refresh", "3;url=/QGaccess/StartLogin.jsp");
				} else if (JUDGE_MYCOMPLAINT.equals(info)) {
					List<Transaction> list = new ArrayList<Transaction>();
					ResultSet rs = UserDao.getMyComplaint(user.getId(), con);
					while (rs.next()) {
						Transaction transaction = new Transaction(rs.getInt(1), rs.getInt(2), rs.getInt(3),
								rs.getBlob(4), rs.getString(5), rs.getString(6));
						list.add(transaction);
					}
					request.getSession().setAttribute("myComplaint", "myComplaint");
					request.getSession().setAttribute("allMyComplaint", list);
					request.getRequestDispatcher("/Personal.jsp").forward(request, response);
				}
			}
		} catch (

		Exception e) {

		} finally {
			LoadingDao.close(con);
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void retainData(HttpServletRequest request, User user) {
		request.setAttribute("newUserName", user.getUserName());
		request.setAttribute("newPhoneNumber", user.getPhoneNumber());
		request.setAttribute("newQq", user.getqq());
		request.setAttribute("newWechat", user.getWechat());
		request.setAttribute("newEmail", user.getEmail());
	}
}
