package com.wushaoning.www.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.wushaoning.www.dao.BusinessDao;
import com.wushaoning.www.dao.LoadingDao;
import com.wushaoning.www.dao.UserDao;
import com.wushaoning.www.model.User;
import com.wushaoning.www.service.IsPassShopService;

/**
 * ע���̼ҵ���Ϣ������
 * 
 * @author 10620
 *
 */
@WebServlet("/BusinessAuthenticationWeb")
public class BusinessAuthenticationWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public final int DIGIT_TWO = 2;
	public final int DIGIT_THREE = 3;
	public BusinessAuthenticationWeb() {
		super();
	}

	/**
	 * ��Դ��BusinessAuthentication.jsp 
	 * ���ã�ע������
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		
		String realid = null;
		String apay = null;
		String checkcode = null;
		String tradeId = null;
		String theComplaint = null;
		InputStream in = null;
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		PrintWriter out = response.getWriter();
		if (ServletFileUpload.isMultipartContent(request)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			fileUpload.setHeaderEncoding("utf-8");
			Connection con = LoadingDao.getCon();
			String filename = null;
			try {
				@SuppressWarnings("unchecked")
				List<FileItem> list = fileUpload.parseRequest(request);
				for (FileItem fileItem : list) {
					filename = fileItem.getName();
					if (!fileItem.isFormField()) {
						filename = fileItem.getName();
						if (filename == null || filename.length() == 0) {
							request.setAttribute("msgfile", "֤�����ϴ�����Ϊ�գ�");
							request.getRequestDispatcher("/BusinessAuthentication.jsp").forward(request, response);
						} else {
							in = fileItem.getInputStream();
						}
					} else {
						String key = fileItem.getFieldName();
						String value = fileItem.getString("utf-8");
						if ("id".equals(key)) {
							realid = value;
						}
						if ("apay".equals(key)) {
							apay = value;
						}
						if ("checkcode".equals(key)) {
							checkcode = value;
						}if("tradeId".equals(key)) {
							tradeId = value;
						}if("theComplaint".equals(key)) {
							theComplaint = value;
						}
					}
				}
			    if(tradeId == null) {
					String checkcodeSession = (String) request.getSession().getAttribute("checkcode_session");
					request.getSession().removeAttribute("checkcode_session");
					if (checkcodeSession == null || !checkcodeSession.equals(checkcode)) {
						request.setAttribute("yzm", "��֤���������");
						retainData(request, realid, apay);
						request.getRequestDispatcher("/BusinessAuthentication.jsp").forward(request, response);
						return;
					} else if (IsPassShopService.passShopLogic(realid, apay) == -1) {
						request.setAttribute("msgid", "���֤�Ż�֧�����˻�����Ϊ�գ�");
						retainData(request, realid, apay);
						request.getRequestDispatcher("/BusinessAuthentication.jsp").forward(request, response);
						return;
					} else if (IsPassShopService.passShopLogic(realid, apay) == 1) {
						request.setAttribute("msgid", "���֤��Ϊ18λ��");
						retainData(request, realid, apay);
						request.getRequestDispatcher("/BusinessAuthentication.jsp").forward(request, response);
						return;
					} else if (IsPassShopService.passShopLogic(realid, apay) == DIGIT_TWO) {
						request.setAttribute("msgapay", "֧������ʽ����");
						retainData(request, realid, apay);
						request.getRequestDispatcher("/BusinessAuthentication.jsp").forward(request, response);
						return;
					} else if (IsPassShopService.passShopLogic(realid, apay) == DIGIT_THREE) {
						request.setAttribute("msgid", "���֤���Ѵ��ڣ�");
						retainData(request, realid, apay);
						request.getRequestDispatcher("/BusinessAuthentication.jsp").forward(request, response);
						return;
					}

					BusinessDao.insertBusinessman(user.getId(), realid, apay, in, con);
					out.println("����ɹ���3�����ת����½ҳ�棡");
					response.setHeader("refresh", "3;url=/QGaccess/StartLogin.jsp");
					return;
			    }else {
			    	UserDao.addComplaint(user.getId(), Integer.parseInt(tradeId), theComplaint, in, con);
			    	request.getSession().setAttribute("complaint", "complaint");
			    	request.setAttribute("msg", "Ͷ�������ѷ��ͣ�");
			    	request.getRequestDispatcher("/Personal.jsp").forward(request, response);
			    }
			} catch (FileUploadException | SQLException e1) {
				e1.printStackTrace();
			} finally {
				LoadingDao.close(con);
			}
		}
	}

	/**
	 * ������������µ�����
	 * @param request
	 * @param realId
	 * @param apay
	 */
	private void retainData(HttpServletRequest request, String realId, String apay) {
		// TODO Auto-generated method stub
		request.setAttribute("realId", realId);
		request.setAttribute("apay", apay);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
