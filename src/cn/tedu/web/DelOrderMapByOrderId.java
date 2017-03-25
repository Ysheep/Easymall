package cn.tedu.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.domain.Order;
import cn.tedu.domain.Product;
import cn.tedu.domain.User;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;

public class DelOrderMapByOrderId extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String order_id = request.getParameter("id");
		OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
		User user = (User) request.getSession().getAttribute("user");
		
		try {
			service.delOrderMapByOrderId(order_id);
			Map<Order, Map<Product, Integer>> orderMaps = service.findOrderListByUserId(user.getId());
			request.setAttribute("orderMaps", orderMaps);
			request.getRequestDispatcher("/orderList.jsp").forward(request, response);
		} catch (MsgException e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/orderList.jsp").forward(request, response);
		} catch (Exception e){
			response.getWriter().write("É¾³ýÊ§°Ü");
			response.setHeader("Refresh", "2;url="+request.getContextPath()+"/orderList.jsp");
		
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
