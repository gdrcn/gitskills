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
 * ע���߼�����
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
	 * ��Դ��Register.jsp
	 * ���ã��û�����ע��
	 * ͬʱ�������Ա���ܱ���
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
			request.setAttribute("yzm", "��֤���������");
			retainData(request, user);
			request.getRequestDispatcher("/Register.jsp").forward(request, response);
			return;
		}

		if (registerService.userLogic(user) != 0) {
			if (registerService.userLogic(user) == -1) {
				request.setAttribute("msg", "���������Ϊ�գ�");
				retainData(request, user);
				request.getRequestDispatcher("/Register.jsp").forward(request, response);
				return;
			} else if (registerService.userLogic(user) == 1) {
				request.setAttribute("msgemail", "�����ʽ����");
				retainData(request, user);
				request.getRequestDispatcher("/Register.jsp").forward(request, response);
				return;
			} else if (registerService.userLogic(user) == DIGIT_TWO) {
				request.setAttribute("msgpwd", "�����ʽ����(������6-12λ)");
				retainData(request, user);
				request.getRequestDispatcher("/Register.jsp").forward(request, response);
				return;
			} else if (registerService.userLogic(user) == DIGIT_THREE) {
				request.setAttribute("msgname", "�û�����ʽ����(����С��20λ)");
				retainData(request, user);
				request.getRequestDispatcher("/Register.jsp").forward(request, response);
				return;
			}
		}
		
		if(registerService.userPwdPtd(userPwdPtd) != 0) {
			if(registerService.userLogic(user) == -1) {
				request.setAttribute("qname", "���뱣������Ϊ�գ�");
				retainData(request, user);
				request.getRequestDispatcher("/Register.jsp").forward(request, response);
				return;
			}else if(registerService.userLogic(user) == 1) {
				request.setAttribute("qname", "���뱣����ʽ����(���ܴ���10λ)��");
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
			out.println("ע��ɹ���3�����ת����½ҳ�棡");
			response.setHeader("refresh", "3;url=/QGaccess/StartLogin.jsp");
		} else if (registerService.enroll(user) == 1) {
			request.setAttribute("msgname", "�û����ظ���");
			retainData(request, user);
			request.getRequestDispatcher("/Register.jsp").forward(request, response);
			return;
		} else if (registerService.enroll(user) == DIGIT_TWO) {
			request.setAttribute("msgemail", "�����ظ���");
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
