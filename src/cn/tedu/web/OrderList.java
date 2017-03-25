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

public class OrderList extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Object userObj =  request.getSession().getAttribute("user");
		if(userObj==null){
			request.setAttribute("msg", "请先登录");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		User user = (User) userObj;
		OrderService service = BasicFactory.getFactory()
				.getInstance(OrderService.class);
		//也可以用Javabean的方式设计
		Map<Order,Map<Product,Integer>> orderMaps = service.findOrderListByUserId(user.getId());
		if(orderMaps!=null){
			request.setAttribute("orderMaps", orderMaps);
			request.getRequestDispatcher("/orderList.jsp")
			.forward(request, response);
		}else{
			request.setAttribute("msg", "空空如也，赶快下单吧~~~~~~~~~~~~~~~~~~");
			request.getRequestDispatcher("/orderList.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
