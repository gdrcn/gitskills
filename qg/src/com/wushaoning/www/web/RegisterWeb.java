package com.wushaoning.www.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wushaoning.www.model.User;
import com.wushaoning.www.service.RegisterService;
import com.wushaoning.www.util.DigestUtils;


@WebServlet("/RegisterWeb")
/**
 * 注册逻辑处理
 * 
 * @author 10620
 *
 */
public class RegisterWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;
	RegisterService registerService = new RegisterService();
	public final int DIGIT_TWO = 2;
	public final int DIGIT_THREE = 3;

	public RegisterWeb() {
		super();
	}
	
	/**
	 * 来源：Register.jsp
	 * 作用：用户进行注册
	 * 同时新增隐性表和密保表
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String userName = request.getParameter("userName");
		String password = request.getParameter("password1");
		String gender = request.getParameter("gender");
		String phoneNumber = request.getParameter("phoneNumber");
		String qq = request.getParameter("QQ");
		String wechat = request.getParameter("wechat");
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		
		String qpwd = request.getParameter("qpwd");
		String qname = request.getParameter("qname");
		
		User user = new User(userName, password, gender, phoneNumber, qq, wechat, address, email);
		User userPwdPtd = new User(email, qpwd, qname);
		RegisterService registerService = new RegisterService();
		String checkcode = request.getParameter("checkcode");
		String checkcodeSession = (String) request.getSession().getAttribute("checkcode_session");
		request.getSession().removeAttribute("checkcode_session");
		if (checkcodeSession == null || !checkcodeSession.equals(checkcode)) {
			request.setAttribute("yzm", "验证码输入错误！");
			retainData(request, user);
			request.getRequestDispatcher("/Register.jsp").forward(request, response);
			return;
		}

		if (registerService.userLogic(user) != 0) {
			if (registerService.userLogic(user) == -1) {
				request.setAttribute("msg", "输入表单不能为空！");
				retainData(request, user);
				request.getRequestDispatcher("/Register.jsp").forward(request, response);
				return;
			} else if (registerService.userLogic(user) == 1) {
				request.setAttribute("msgemail", "邮箱格式出错！");
				retainData(request, user);
				request.getRequestDispatcher("/Register.jsp").forward(request, response);
				return;
			} else if (registerService.userLogic(user) == DIGIT_TWO) {
				request.setAttribute("msgpwd", "密码格式出错！(长度在6-12位)");
				retainData(request, user);
				request.getRequestDispatcher("/Register.jsp").forward(request, response);
				return;
			} else if (registerService.userLogic(user) == DIGIT_THREE) {
				request.setAttribute("msgname", "用户名格式出错！(长度小于20位)");
				retainData(request, user);
				request.getRequestDispatcher("/Register.jsp").forward(request, response);
				return;
			}
		}
		
		if(registerService.userPwdPtd(userPwdPtd) != 0) {
			if(registerService.userLogic(user) == -1) {
				request.setAttribute("qname", "密码保护不能为空！");
				retainData(request, user);
				request.getRequestDispatcher("/Register.jsp").forward(request, response);
				return;
			}else if(registerService.userLogic(user) == 1) {
				request.setAttribute("qname", "密码保护格式错误(不能大于10位)！");
				retainData(request, user);
				request.getRequestDispatcher("/Register.jsp").forward(request, response);
				return;
			}
		}
		
		password = DigestUtils.md5(password);
		user = new User(userName, password, gender, phoneNumber, qq, wechat, address, email);

		if (registerService.enroll(user) == 0) {
			registerService.insertRole(user.getUserName());
			registerService.enrollPwdPtd(userPwdPtd);
			registerService.insertLatentRecord(userName);
			out.println("注册成功，3秒后将跳转至登陆页面！");
			response.setHeader("refresh", "3;url=/QGaccess/StartLogin.jsp");
		} else if (registerService.enroll(user) == 1) {
			request.setAttribute("msgname", "用户名重复！");
			retainData(request, user);
			request.getRequestDispatcher("/Register.jsp").forward(request, response);
			return;
		} else if (registerService.enroll(user) == DIGIT_TWO) {
			request.setAttribute("msgemail", "邮箱重复！");
			retainData(request, user);
			request.getRequestDispatcher("/Register.jsp").forward(request, response);
			return;
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void retainData(HttpServletRequest request, User user) {
		request.setAttribute("userName", user.getUserName());
		request.setAttribute("phoneNumber", user.getPhoneNumber());
		request.setAttribute("qq", user.getqq());
		request.setAttribute("wechat", user.getWechat());
		request.setAttribute("address", user.getAddress());
		request.setAttribute("email", user.getEmail());
	}
}
