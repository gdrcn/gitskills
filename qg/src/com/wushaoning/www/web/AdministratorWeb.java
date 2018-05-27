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
import com.wushaoning.www.dao.LoadingDao;
import com.wushaoning.www.dao.UserDao;
import com.wushaoning.www.model.Businessman;
import com.wushaoning.www.model.Transaction;
import com.wushaoning.www.model.User;
import com.wushaoning.www.service.DeleteService;
import com.wushaoning.www.service.IsPassShopService;

/**
 * 管理员页面信息处理类
 * 
 * @author 10620
 *
 */
@WebServlet("/AdministratorWeb")
public class AdministratorWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public final String JUDGE_ALLUSER = "allUser";
	public final String JUDGE_ALLSHOP = "allShop";
	public final String JUDGE_CHECK = "check";
	public final String JUDGE_SUCCESS = "success";
	public final String JUDGE_FAIL = "fail";
	public final String JUDGE_CLOSE = "close";
	public final String JUDGE_DELETE = "delete";
	public final String JUDGE_COMPLETE = "complete";
	public final String JUDGE_UPGRADE = "upgrade";
	
	public AdministratorWeb() {
		super();
	}

	/**
	 * 来源：AdministaratorFunction.jsp 
	 * 作用：管理员页面功能信息处理 
	 * info: 处理功能表执行 
	 * sign: 处理具体小功能
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		
		request.getSession().removeAttribute("notPassList");
		request.getSession().removeAttribute("passList");
		request.getSession().removeAttribute("allShow");
		request.getSession().removeAttribute("allUser");
		request.getSession().removeAttribute("allShop");
		request.getSession().removeAttribute("check");
		request.getSession().removeAttribute("allPermission");

		String info = request.getParameter("info");
		String sign = request.getParameter("sign");
		HttpSession session = request.getSession();
		User admin = (User) session.getAttribute("user");
		Connection con = LoadingDao.getCon();
		try {
			if (sign == null) {
				if (JUDGE_ALLUSER.equals(info)) {
					List<User> list = new ArrayList<User>();
					ResultSet rs = UserDao.allUser(con);
					while (rs.next()) {
						User user = new User(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getString(5),
								rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
						list.add(user);
					}
					request.getSession().setAttribute("allUser", "allUser");
					request.getSession().setAttribute("allShow", list);
					request.getRequestDispatcher("/AdministratorFunction.jsp").forward(request, response);
					return;
				} else if (JUDGE_ALLSHOP.equals(info)) {
					List<Businessman> list = new ArrayList<Businessman>();
					List<Businessman> list1 = new ArrayList<Businessman>();
					ResultSet rs = BusinessDao.allNotShop(con);
					ResultSet rs1 = BusinessDao.allShop(con);
					while (rs.next()) {
						Businessman businessman = new Businessman(rs.getString(3), rs.getString(4), rs.getBlob(5),
								rs.getString(6));
						businessman.setId(rs.getInt(2));
						list.add(businessman);
					}
					while (rs1.next()) {
						Businessman businessman = new Businessman(rs1.getString(3), rs1.getString(4), rs1.getBlob(5),
								rs1.getString(6));
						businessman.setId(rs1.getInt(2));
						list1.add(businessman);
					}
					request.getSession().setAttribute("allShop", "allShop");
					request.getSession().setAttribute("notPassList", list);
					request.getSession().setAttribute("passList", list1);
					request.getRequestDispatcher("/AdministratorFunction.jsp").forward(request, response);
				} else if (JUDGE_CHECK.equals(info)) {
					List<Transaction> list = new ArrayList<Transaction>();
					ResultSet rs = UserDao.getAllComplaint(con);
					while (rs.next()) {
						Transaction transaction = new Transaction(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getBlob(4), rs.getString(5));
						list.add(transaction);
					}
					request.getSession().setAttribute("allComplaint", list);
					request.getSession().setAttribute("check", "check");
					request.getRequestDispatcher("/AdministratorFunction.jsp").forward(request, response);
				}
			} else if (sign != null) {
				IsPassShopService isPassShopService = new IsPassShopService();
				if (JUDGE_SUCCESS.equals(sign)) {
					int theUserId = Integer.parseInt(request.getParameter("userId"));
					isPassShopService.passShop(theUserId, sign);
					response.sendRedirect("/QGaccess/AdministratorWeb?info=allShop");
				} else if (JUDGE_FAIL.equals(sign)) {
					int theUserId = Integer.parseInt(request.getParameter("userId"));
					isPassShopService.passShop(theUserId, sign);
					response.sendRedirect("/QGaccess/AdministratorWeb?info=allShop");
				} else if (JUDGE_CLOSE.equals(sign)) {
					int theUserId = Integer.parseInt(request.getParameter("userId"));
					isPassShopService.closeShop(theUserId);
					response.sendRedirect("/QGaccess/AdministratorWeb?info=allShop");
				} else if (JUDGE_DELETE.equals(sign)) {
					DeleteService deleteService = new DeleteService();
					String hisId = request.getParameter("hisId");
					String myId = String.valueOf(admin.getId());
					if (deleteService.deleteUser(hisId, myId) == -1) {
						request.setAttribute("msg", "权限不足！");
					} else {
						request.setAttribute("msg", "删除成功！");
					}
					request.getRequestDispatcher("/AdministratorFunction.jsp").forward(request, response);
				}else if(JUDGE_COMPLETE.equals(sign)) {
					String complaintId = request.getParameter("complaintId");
					UserDao.upadteComplaint(Integer.parseInt(complaintId), con);
					response.sendRedirect("/QGaccess/AdministratorWeb?info=check");
				}else if(JUDGE_UPGRADE.equals(sign)) {
					String hisId = request.getParameter("hisId");
					UserDao.upadteAdministrators(Integer.parseInt(hisId), con);
					request.getRequestDispatcher("/AdministratorFunction.jsp").forward(request, response);
				}
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
