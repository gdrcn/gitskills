package com.wushaoning.www.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wushaoning.www.dao.LoadingDao;
import com.wushaoning.www.dao.UserDao;
import com.wushaoning.www.model.User;
import com.wushaoning.www.util.EmailUtils;

/**
 * ����������ҳ��Ϣ������
 * @author 10620
 *
 */
@WebServlet("/ForgetPasswordWeb")
public class ForgetPasswordWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ForgetPasswordWeb() {
		super();
	}
	/**
	 * ���ã��һ�����
	 * ��Դ��ForgetPassword.jsp
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		String qpwd = request.getParameter("qpwd");
		String qname = request.getParameter("qname");

		User userPwdPtd = new User(email, qpwd, qname);
		String checkcode = request.getParameter("checkcode");
		String checkcodeSession = (String) request.getSession().getAttribute("checkcode_session");
		request.getSession().removeAttribute("checkcode_session");
		if (checkcodeSession == null || !checkcodeSession.equals(checkcode)) {
			
			request.setAttribute("yzm", "��֤���������");
			retainData(request, userPwdPtd);
			request.getRequestDispatcher("/ForgetPassword.jsp").forward(request, response);
			return;
		}

		if (qpwd == "") {
			request.setAttribute("msgqname", "�ܱ�����Ϊ�գ�");
			retainData(request, userPwdPtd);
			request.getRequestDispatcher("/ForgetPassword.jsp").forward(request, response);
		} else if (email == "") {
			request.setAttribute("msgemail", "���䲻��Ϊ�գ�");
			retainData(request, userPwdPtd);
			request.getRequestDispatcher("/ForgetPassword.jsp").forward(request, response);
		}

		Connection con = LoadingDao.getCon();
		try {
			if (UserDao.findPwdPtd(userPwdPtd, con)) {
				EmailUtils.sendemail(email);
				out.println("�����޸ĳɹ���3�����ת����½ҳ�棡");
				response.setHeader("refresh", "3;url=/QGaccess/StartLogin.jsp");
			}else {
				request.setAttribute("msg", "���������֤�����");
				retainData(request, userPwdPtd);
				request.getRequestDispatcher("/ForgetPassword.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
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
	/**
	 * ��������
	 * @param request
	 * @param user
	 */
	public void retainData(HttpServletRequest request, User user) {
		request.setAttribute("qpwd", user.getPwdPtd());
		request.setAttribute("email", user.getEmail());
	}
}
