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
 * ��ӹ����¼�����ﳵ����Ϣ��
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
	 * ��Դ��CommodityInformation.jsp/BusinessmanShop.jsps 
	 * ���ã������Ʒ�����ﳵ/�̼��޸Ķ���
	 * info���ж�����һ������ 
	 * result: �ж�����һ����������ʽ����
	 * ��info == Nullʱ��δ�����Ʒ�����ﳵ�Ĳ���
	 * ��������ﳵʱʵ�ֵĹ����У���¼�û�������Ϣ����¼���������Żݺ�ļ۸�
	 * ��info != Nullʱ��Ϊ�̼��޸Ķ����Ĳ���
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
				request.setAttribute("msg", "��Ϣ������Ϊ�գ�");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/CommodityInformation.jsp").forward(request, response);
				return;
			} else if (result == JUDGE_NUM) {
				request.setAttribute("msgNum", "����������ʽ����");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/CommodityInformation.jsp").forward(request, response);
				return;
			} else if (result == JUDGE_PHONE) {
				request.setAttribute("msgphone", "�ֻ������ʽ����");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/CommodityInformation.jsp").forward(request, response);
				return;
			} else if (result == JUDGE_APAY) {
				request.setAttribute("msgapay", "֧������ʽ����(6-15λ����)");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/CommodityInformation.jsp").forward(request, response);
				return;
			} else if (result == JUDGE_ADDRESS) {
				request.setAttribute("msgaddress", "�ջ��ַ��ʽ����");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/CommodityInformation.jsp").forward(request, response);
				return;
			} else if (!(addBuyCarService.isEnough(user.getId(), goods.getGoodsId(), buyNum, goods.getNum()))) {
				request.setAttribute("msgNum", "�̼ұ����������㣡");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/CommodityInformation.jsp").forward(request, response);
				return;
			} else if (addBuyCarService.discountLogic(discount, Integer.parseInt(buyNum), goods.getPrice(),
					goods) == JUDGE_MSG) {
				request.setAttribute("msg", "����ѡ������Ʒ�ܼ۸���200��");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/CommodityInformation.jsp").forward(request, response);
				return;
			}

			if (addBuyCarService.isExistGoods(user.getId(), goods.getGoodsId(), Integer.parseInt(buyNum))) {
				request.setAttribute("msg", "�ɹ������ҵĹ��ﳵ��");
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
					request.setAttribute("msg", "�ɹ�������ҵĹ��ﳵ��");
					request.getRequestDispatcher("/CommodityInformation.jsp").forward(request, response);
					return;
				}
			}
		} else {
			if (result == -1) {
				request.setAttribute("msg", "��Ϣ������Ϊ�գ�");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
				return;
			} else if (result == JUDGE_NUM) {
				request.setAttribute("msgNum", "����������ʽ����");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
				return;
			} else if (result == JUDGE_PHONE) {
				request.setAttribute("msgphone", "�ֻ������ʽ����");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
				return;
			} else if (result == JUDGE_APAY) {
				request.setAttribute("msgapay", "֧������ʽ����(6-15λ����)");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
				return;
			} else if (result == JUDGE_ADDRESS) {
				request.setAttribute("msgaddress", "�ջ��ַ��ʽ����");
				retainData(request, buyNum, phone, apay, address);
				request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
				return;
			}
			Connection con = LoadingDao.getCon();
			try {
				if (!(BuyCarDao.findExistBuyCar(Integer.parseInt(alterGoodsid), con))) {
					request.setAttribute("msgid", "���������ڣ�");
					retainData(request, buyNum, phone, apay, address);
					request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
					return;
				} else {
					BuyCar buyCar = new BuyCar(Integer.parseInt(alterGoodsid), Integer.parseInt(buyNum), phone, apay,
							address);
					BuyCarDao.updateExistedOrder(buyCar, con);
					request.setAttribute("msg", "�޸ĳɹ�!");
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
	 * ���������������Ϣ
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
