package cn.tedu.web;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.domain.Order;
import cn.tedu.domain.OrderItem;
import cn.tedu.domain.Product;
import cn.tedu.domain.User;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;

public class OrderAdd extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1、首先看用户是否登录，如果没有登录直接跳转到登录页面,如果已经登录获取用户的id
		Object userObj = request.getSession().getAttribute("user");
		if(userObj==null){
			request.setAttribute("msg", "请先登录");
			request.getRequestDispatcher(request.getContextPath()+"/login.jsp")
			       .forward(request, response);
		}
		//3、调用service添加订单的方法
		OrderService service = BasicFactory.getFactory()
				              .getInstance(OrderService.class);
		//2、从session中获取购物车中的商品信息，使用这些信息封装Order类对象和N个OrderItem
		//对象。由于OrderItem对象需要订单的id，所以先产生订单的id：UUID产生订单的id
		User user = (User) userObj;
		//2.1、封装Order order
		Order order = new Order();
		order.setId(UUID.randomUUID().toString());
		order.setOrdertime(new Timestamp(new Date().getTime()));
		order.setpaystate(0);
		order.setUser_id(user.getId());
		order.setReceiverAddress(request.getParameter("receiverAddress"));
		//缺少设置订单的金额？？
		//2.2从session获取购物车信息
		Map<Product,Integer> cartMap = (Map<Product, Integer>)
		           request.getSession().getAttribute("cart");
		//2.3定义变量
		double money = 0;
		//定义订单项的集合
		List<OrderItem> itemList = new ArrayList<OrderItem>();
		//订单的总价
		//2.4循环遍历购物车，计算订单金额，创建所有订单项对象，并添加到list集合中
		for (Map.Entry<Product, Integer> entry : cartMap.entrySet()) {
			//将小计累加
			money+=entry.getKey().getPrice()*entry.getValue();
			//创建订单项对象
			OrderItem item = new OrderItem();
			//封装信息到item对象中
			item.setOrder_id(order.getId());
			item.setProduct_id(entry.getKey().getId());
			item.setBuynum(entry.getValue());
			//将item对象添加到list集合中
			itemList.add(item);
		}
		//2.5设置订单的金额
		order.setMoney(money);
		try {
			service.addOrderAndOrderItem(order,itemList);
			//4、清空购物车
			cartMap.clear();
			
			//5、提示添加成功并回到主页
			response.getWriter().write("订单添加成功!");
			response.setHeader("Refresh", "2;url="+request.getContextPath()+"/index.jsp");
		} catch (MsgException e) {
			request.setAttribute("msg",e.getMessage());
			request.getRequestDispatcher("/cart.jsp").forward(request, response);
		} catch (Exception e) {
			request.getRequestDispatcher("/cart.jsp").forward(request, response);
		}
		
		

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
