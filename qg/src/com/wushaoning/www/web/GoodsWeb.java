package com.wushaoning.www.web;

import java.io.IOException;
import java.io.InputStream;
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

import com.wushaoning.www.dao.GoodsDao;
import com.wushaoning.www.dao.LoadingDao;
import com.wushaoning.www.model.Businessman;
import com.wushaoning.www.model.Goods;
import com.wushaoning.www.service.GoodsService;

/**
 * �̼�ҳ����Ϣ������
 * 
 * @author 10620
 *
 */
@WebServlet("/GoodsWeb")
public class GoodsWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public final int DIGIT_TWO = 2;
	public final int DIGIT_THREE = 3;
	public final int DIGIT_FOUR = 4;
	
	public GoodsWeb() {
		super();
	}

	/**
	 * ��Դ��Businessman.jsp
	 * ���ã��޸���Ʒ�������Ʒ
	 * ���ù���jar���ϴ��ļ�
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		
		GoodsService goodsService = new GoodsService();
		String goodsTags = null;
		String price = null;
		String num = null;
		String function = null;
		String goodsName = null;
		String rGoodsId = null;
		String rGoodsTags = null;
		String rGoodsName = null;
		String rPrice = null;
		String rNum = null;
		String rFunction = null;
		String discount = null;
		InputStream in = null;

		HttpSession session = request.getSession();
		Businessman businessman = (Businessman) session.getAttribute("businessman");

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
							request.setAttribute("msgfile", "��Ʒ��Ƭ�ϴ�����Ϊ�գ�");
							request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
						} else {
							in = fileItem.getInputStream();
						}
					} else {
						String key = fileItem.getFieldName();
						String value = fileItem.getString("utf-8");
						if ("goodsTags".equals(key)) {
							goodsTags = value;
						}
						if ("price".equals(key)) {
							price = value;
						}
						if ("num".equals(key)) {
							num = value;
						}
						if ("function".equals(key)) {
							function = value;
						}
						if ("goodsName".equals(key)) {
							goodsName = value;
						}
						if ("rGoodsId".equals(key)) {
							rGoodsId = value;
						}
						if ("rGoodsTags".equals(key)) {
							rGoodsTags = value;
						}
						if ("rGoodsName".equals(key)) {
							rGoodsName = value;
						}
						if ("rPrice".equals(key)) {
							rPrice = value;
						}
						if ("rNum".equals(key)) {
							rNum = value;
						}
						if ("rFunction".equals(key)) {
							rFunction = value;
						}
						if("discount".equals(key)) {
							discount = value;
						}
					}
				}
				if (goodsName != null) {
					if (goodsService.goodsLogic(price, num, function, goodsName) == -1) {
						retainData(request, price, num, function, goodsName);
						request.setAttribute("msg", "��Ϣ������Ϊ��");
						request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
					} else if (goodsService.goodsLogic(price, num, function, goodsName) == 1) {
						retainData(request, price, num, function, goodsName);
						request.setAttribute("msgprice", "��Ʒ�۸��ʽ����");
						request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
					} else if (goodsService.goodsLogic(price, num, function, goodsName) == DIGIT_TWO) {
						retainData(request, price, num, function, goodsName);
						request.setAttribute("msgnum", "��Ʒ������ʽ����");
						request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
					} else if (goodsService.goodsLogic(price, num, function, goodsName) == DIGIT_THREE) {
						retainData(request, price, num, function, goodsName);
						request.setAttribute("msgfun", "��Ʒ���ܸ�ʽ����");
						request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
					} else if (goodsService.goodsLogic(price, num, function, goodsName) == DIGIT_FOUR) {
						retainData(request, price, num, function, goodsName);
						request.setAttribute("msgname", "��Ʒ���Ƹ�ʽ����");
						request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
					}
					Goods goods = new Goods(goodsTags, price, num, function, goodsName, businessman.getShop());
					goods.setDiscount(discount);
					GoodsDao.addGoods(goods, in, discount, con);
					request.setAttribute("msg", "��Ʒ��ӳɹ�");
					request.getRequestDispatcher("/ShowGoods?tips=add").forward(request, response);
					return;
				} else {
					if (goodsService.goodsLogic(rPrice, rNum, rFunction, rGoodsName) == -1) {
						retainData(request, rPrice, rNum, rFunction, rGoodsName);
						request.setAttribute("rmsg", "��Ϣ������Ϊ��");
						request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
					} else if (goodsService.goodsLogic(rPrice, rNum, rFunction, rGoodsName) == 1) {
						retainData(request, rPrice, rNum, rFunction, rGoodsName);
						request.setAttribute("rmsgprice", "��Ʒ�۸��ʽ����");
						request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
					} else if (goodsService.goodsLogic(rPrice, rNum, rFunction, rGoodsName) == DIGIT_TWO) {
						retainData(request, rPrice, rNum, rFunction, rGoodsName);
						request.setAttribute("rmsgnum", "��Ʒ������ʽ����");
						request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
					} else if (goodsService.goodsLogic(rPrice, rNum, rFunction, rGoodsName) == DIGIT_THREE) {
						retainData(request, rPrice, rNum, rFunction, rGoodsName);
						request.setAttribute("rFunction", "��Ʒ���ܸ�ʽ����");
						request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
					} else if (goodsService.goodsLogic(rPrice, rNum, rFunction, rGoodsName) == DIGIT_FOUR) {
						retainData(request, rPrice, rNum, rFunction, rGoodsName);
						request.setAttribute("rmsgname", "��Ʒ���Ƹ�ʽ����");
						request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
					}
					Goods goods = new Goods(rGoodsTags, rPrice, rNum, rFunction, rGoodsName, businessman.getShop());
					GoodsDao.updateGoods(Integer.parseInt(rGoodsId), goods, in, con);
					request.setAttribute("rmsg", "��Ʒ�޸ĳɹ�");
					request.getRequestDispatcher("/BusinessmanShop.jsp").forward(request, response);
					return;
				}
			} catch (FileUploadException | SQLException e1) {
				e1.printStackTrace();
			} finally {
				LoadingDao.close(con);
			}
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
	 * @param price
	 * @param num
	 * @param function
	 * @param goodsName
	 */
	public void retainData(HttpServletRequest request, String price, String num, String function, String goodsName) {
		request.setAttribute("price", price);
		request.setAttribute("num", num);
		request.setAttribute("function", function);
		request.setAttribute("goodsName", goodsName);
	}

}
