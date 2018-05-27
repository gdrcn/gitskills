package com.wushaoning.www.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * �ǳ�ҳ��ҵ������
 * @author 10620
 *
 */
@WebServlet("/LoginOutWeb")
public class LoginOutWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public LoginOutWeb() {
		super();
	}

	/**
	 * ���ã������˺�
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
        session.invalidate();
        request.getRequestDispatcher("/StartLogin.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
