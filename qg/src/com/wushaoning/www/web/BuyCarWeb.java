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

import com.wushaoning.www.dao.BuyCarDao;
import com.wushaoning.www.dao.LoadingDao;
import com.wushaoning.www.dao.UserDao;
import com.wushaoning.www.model.BuyCar;
import com.wushaoning.www.model.User;
import com.wushaoning.www.service.LoginService;
import com.wushaoning.www.service.PurchaseService;

/**
 * ���ﳵҳ����Ϣ������
 * 
 * @author 10620
 *
 */
@WebServlet("/BuyCarWeb")
public class BuyCarWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public final String JUDGE_AUDITING = "auditing";
	public final String JUDGE_FAIL = "fail";
	public final String JUDGE_SUCCESS = "success";
	public final String JUDGE_REMOVE = "remove";
	public final String JUDGE_ALTER = "alter";
	public final String JUDGE_MYBUYCAR = "myBuyCar";
	public final String JUDGE_PURCHASED = "purchased";
	public final String JUDGE_CARRY = "carry";
	public final String JUDGE_ASSESS = "assess";
	public final int DIGIT_TWO = -2;
	public BuyCarWeb() {
		super();
	}

	/**
	 * ��Դ��BuyCar.jsp 
	 * ���ã������ﳵ����Ϣ���� 
	 * info���ж��û�ѡ�������һ������ 
	 * sign: �ж��û�ѡ�������һ������
	 * ÿ��ȷ���µ�����ı乺�ﳵͼ������������ۻ��֣����۾���
	 * 
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("utf-8");

		request.getSession().removeAttribute("myGoodsCar");
		request.getSession().removeAttribute("myGoods");
		request.getSession().removeAttribute("carry");
		request.getSession().removeAttribute("purchased");
		request.getSession().removeAttribute("assess");
		String sign = request.getParameter("sign");
		String info = request.getParameter("info");
		String userId = request.getParameter("myId");
		String goodsId = request.getParameter("goodsId");
		String buyCarId = request.getParameter("buyCarId");
		String discount = request.getParameter("discount");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		PurchaseService purchaseService = new PurchaseService();
		LoginService loginService = new LoginService();
		Connection con = LoadingDao.getCon();
		List<BuyCar> list = new ArrayList<BuyCar>();
		try {
			if (sign != null) {
				if (JUDGE_AUDITING.equals(sign)) {
					if (purchaseService.enoughIntegral(discount, user.getId(), buyCarId) == -1) {
						request.setAttribute("msg", "���ֲ���");

						ResultSet rs = BuyCarDao.myBuyCar(user.getId(), "wait", con);
						while (rs.next()) {
							BuyCar buycar = new BuyCar(rs.getInt(2), rs.getInt(7), rs.getBlob(8), rs.getInt(10),
									rs.getInt(9), rs.getString(6), rs.getInt(11), rs.getString(5), rs.getString(4),
									rs.getString(3), rs.getString(12), rs.getInt(1));
							buycar.setIntegral(UserDao.getIntegral(user.getId(), con));
							list.add(buycar);
						}
						request.getSession().setAttribute("myGoods", "myGoods");
						request.getSession().setAttribute("myGoodsCar", list);
						request.getRequestDispatcher("/BuyCar.jsp").forward(request, response);
						return;
					} else if (purchaseService.enoughIntegral(discount, user.getId(), buyCarId) == DIGIT_TWO) {
						request.setAttribute("msg", "ֻ�ɽ���Ʒ����10�����ڣ�");

						ResultSet rs = BuyCarDao.myBuyCar(user.getId(), "wait", con);
						while (rs.next()) {
							BuyCar buycar = new BuyCar(rs.getInt(2), rs.getInt(7), rs.getBlob(8), rs.getInt(10),
									rs.getInt(9), rs.getString(6), rs.getInt(11), rs.getString(5), rs.getString(4),
									rs.getString(3), rs.getString(12), rs.getInt(1));
							buycar.setIntegral(UserDao.getIntegral(user.getId(), con));
							list.add(buycar);
						}
						request.getSession().setAttribute("myGoods", "myGoods");
						request.getSession().setAttribute("myGoodsCar", list);
						request.getRequestDispatcher("/BuyCar.jsp").forward(request, response);
						return;
					}
					purchaseService.addOrder(userId, goodsId, buyCarId, sign);
					purchaseService.addRecord(Integer.parseInt(userId), Integer.parseInt(buyCarId), request); 
					ResultSet rs = BuyCarDao.myBuyCar(user.getId(), "wait", con);

					while (rs.next()) {
						BuyCar buycar = new BuyCar(rs.getInt(2), rs.getInt(7), rs.getBlob(8), rs.getInt(10),
								rs.getInt(9), rs.getString(6), rs.getInt(11), rs.getString(5), rs.getString(4),
								rs.getString(3), rs.getString(12), rs.getInt(1));
						list.add(buycar);
					}
					user.setMyBuyCarNum(loginService.getUserBuyCarNum(user.getId())); 
					request.setAttribute("msg1", "�����ύ�ɹ���");
					request.getSession().setAttribute("myGoods", "myGoods");
					request.getSession().setAttribute("myGoodsCar", list);
					request.getSession().setAttribute("userBuyCarNum", user.getMyBuyCarNum());
					request.getRequestDispatcher("/BuyCar.jsp").forward(request, response);
					return;
				} else if (JUDGE_FAIL.equals(sign)) {
					purchaseService.missOrder(buyCarId, sign);
					ResultSet rs = BuyCarDao.myBuyCar(user.getId(), "auditing", con);
					while (rs.next()) {
						BuyCar buycar = new BuyCar(rs.getInt(2), rs.getInt(7), rs.getBlob(8), rs.getInt(10),
								rs.getInt(9), rs.getString(6), rs.getInt(11), rs.getString(5), rs.getString(4),
								rs.getString(3), rs.getString(12), rs.getInt(1));
						list.add(buycar);
					}
					request.setAttribute("msg", "����ȡ���ɹ���");
					request.getSession().setAttribute("carry", "carry");
					request.getSession().setAttribute("myGoodsCar", list);
					request.getRequestDispatcher("/BuyCar.jsp").forward(request, response);
					return;
				} else if (JUDGE_SUCCESS.equals(sign)) {
					BuyCarDao.updateOrder(Integer.parseInt(buyCarId), sign, con);
					BuyCarDao.alterBuyCar(Integer.parseInt(buyCarId), sign, con);
					purchaseService.assessShop(Integer.parseInt(buyCarId)); 
					ResultSet rs = BuyCarDao.myBuyCar(user.getId(), "delivery", con);
					while (rs.next()) {
						BuyCar buycar = new BuyCar(rs.getInt(2), rs.getInt(7), rs.getBlob(8), rs.getInt(10),
								rs.getInt(9), rs.getString(6), rs.getInt(11), rs.getString(5), rs.getString(4),
								rs.getString(3), rs.getString(12), rs.getInt(1));
						list.add(buycar);
					}
					request.setAttribute("msg", "�ռ��ɹ���");
					request.getSession().setAttribute("purchased", "purchased");
					request.getSession().setAttribute("myGoodsCar", list);
					request.getRequestDispatcher("/BuyCar.jsp").forward(request, response);
					return;
				} else if (JUDGE_REMOVE.equals(sign)) {
					BuyCarDao.removeBuyCar(Integer.parseInt(buyCarId), con);
					ResultSet rs = BuyCarDao.myBuyCar(user.getId(), "wait", con);
					while (rs.next()) {
						BuyCar buycar = new BuyCar(rs.getInt(2), rs.getInt(7), rs.getBlob(8), rs.getInt(10),
								rs.getInt(9), rs.getString(6), rs.getInt(11), rs.getString(5), rs.getString(4),
								rs.getString(3), rs.getString(12), rs.getInt(1));
						list.add(buycar);
					}
					request.setAttribute("msg", "�Ƴ��ɹ���");
					request.getSession().setAttribute("myGoods", "myGoods");
					request.getSession().setAttribute("myGoodsCar", list);
					request.getRequestDispatcher("/BuyCar.jsp").forward(request, response);
					return;
				} else if (JUDGE_ALTER.equals(sign)) {
					String star = request.getParameter("star");
					String assessTxt = request.getParameter("assessTxt");
					purchaseService.alterAssess(Integer.parseInt(buyCarId), star, assessTxt);
					ResultSet rs = BuyCarDao.myBuyCar(user.getId(), "success", con);
					while (rs.next()) {
						BuyCar buycar = new BuyCar(rs.getInt(2), rs.getInt(7), rs.getBlob(8), rs.getInt(10),
								rs.getInt(9), rs.getString(6), rs.getInt(11), rs.getString(5), rs.getString(4),
								rs.getString(3), rs.getString(12), rs.getInt(1));
						list.add(buycar);
					}
					request.getSession().setAttribute("assess", "assess");
					request.getSession().setAttribute("myGoodsCar", list);

					request.setAttribute("msg", "���۳ɹ���");
					request.getRequestDispatcher("/BuyCar.jsp").forward(request, response);
					return;
				}
			} else {
				if (JUDGE_MYBUYCAR.equals(info)) {
					ResultSet rs = BuyCarDao.myBuyCar(user.getId(), "wait", con);
					while (rs.next()) {
						BuyCar buycar = new BuyCar(rs.getInt(2), rs.getInt(7), rs.getBlob(8), rs.getInt(10),
								rs.getInt(9), rs.getString(6), rs.getInt(11), rs.getString(5), rs.getString(4),
								rs.getString(3), rs.getString(12), rs.getInt(1));
						buycar.setIntegral(UserDao.getIntegral(user.getId(), con));
						list.add(buycar);
					}
					request.getSession().setAttribute("myGoods", "myGoods");
					request.getSession().setAttribute("myGoodsCar", list);
					request.getRequestDispatcher("/BuyCar.jsp").forward(request, response);
					return;
				} else if (JUDGE_CARRY.equals(info)) {
					ResultSet rs = BuyCarDao.myBuyCar(user.getId(), "auditing", con);
					while (rs.next()) {
						BuyCar buycar = new BuyCar(rs.getInt(2), rs.getInt(7), rs.getBlob(8), rs.getInt(10),
								rs.getInt(9), rs.getString(6), rs.getInt(11), rs.getString(5), rs.getString(4),
								rs.getString(3), rs.getString(12), rs.getInt(1));
						list.add(buycar);
					}
					request.getSession().setAttribute("carry", "carry");
					request.getSession().setAttribute("myGoodsCar", list);
					request.getRequestDispatcher("/BuyCar.jsp").forward(request, response);
					return;
				} else if (JUDGE_PURCHASED.equals(info)) {
					ResultSet rs = BuyCarDao.formerBuyCar(user.getId(), con);
					while (rs.next()) {
						BuyCar buycar = new BuyCar(rs.getInt(2), rs.getInt(7), rs.getBlob(8), rs.getInt(10),
								rs.getInt(9), rs.getString(6), rs.getInt(11), rs.getString(5), rs.getString(4),
								rs.getString(3), rs.getString(12), rs.getInt(1));
						list.add(buycar);
					}
					request.getSession().setAttribute("purchased", "purchased");
					request.getSession().setAttribute("myGoodsCar", list);
					request.getRequestDispatcher("/BuyCar.jsp").forward(request, response);
					return;
				} else if (JUDGE_ASSESS.equals(info)) {
					ResultSet rs = BuyCarDao.myBuyCar(user.getId(), "success", con);
					while (rs.next()) {
						BuyCar buycar = new BuyCar(rs.getInt(2), rs.getInt(7), rs.getBlob(8), rs.getInt(10),
								rs.getInt(9), rs.getString(6), rs.getInt(11), rs.getString(5), rs.getString(4),
								rs.getString(3), rs.getString(12), rs.getInt(1));
						list.add(buycar);
					}
					request.getSession().setAttribute("assess", "assess");
					request.getSession().setAttribute("myGoodsCar", list);
					request.getRequestDispatcher("/BuyCar.jsp").forward(request, response);
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
