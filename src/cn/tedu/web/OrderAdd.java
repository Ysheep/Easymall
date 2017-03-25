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
		//1�����ȿ��û��Ƿ��¼�����û�е�¼ֱ����ת����¼ҳ��,����Ѿ���¼��ȡ�û���id
		Object userObj = request.getSession().getAttribute("user");
		if(userObj==null){
			request.setAttribute("msg", "���ȵ�¼");
			request.getRequestDispatcher(request.getContextPath()+"/login.jsp")
			       .forward(request, response);
		}
		//3������service��Ӷ����ķ���
		OrderService service = BasicFactory.getFactory()
				              .getInstance(OrderService.class);
		//2����session�л�ȡ���ﳵ�е���Ʒ��Ϣ��ʹ����Щ��Ϣ��װOrder������N��OrderItem
		//��������OrderItem������Ҫ������id�������Ȳ���������id��UUID����������id
		User user = (User) userObj;
		//2.1����װOrder order
		Order order = new Order();
		order.setId(UUID.randomUUID().toString());
		order.setOrdertime(new Timestamp(new Date().getTime()));
		order.setpaystate(0);
		order.setUser_id(user.getId());
		order.setReceiverAddress(request.getParameter("receiverAddress"));
		//ȱ�����ö����Ľ���
		//2.2��session��ȡ���ﳵ��Ϣ
		Map<Product,Integer> cartMap = (Map<Product, Integer>)
		           request.getSession().getAttribute("cart");
		//2.3�������
		double money = 0;
		//���嶩����ļ���
		List<OrderItem> itemList = new ArrayList<OrderItem>();
		//�������ܼ�
		//2.4ѭ���������ﳵ�����㶩�����������ж�������󣬲���ӵ�list������
		for (Map.Entry<Product, Integer> entry : cartMap.entrySet()) {
			//��С���ۼ�
			money+=entry.getKey().getPrice()*entry.getValue();
			//�������������
			OrderItem item = new OrderItem();
			//��װ��Ϣ��item������
			item.setOrder_id(order.getId());
			item.setProduct_id(entry.getKey().getId());
			item.setBuynum(entry.getValue());
			//��item������ӵ�list������
			itemList.add(item);
		}
		//2.5���ö����Ľ��
		order.setMoney(money);
		try {
			service.addOrderAndOrderItem(order,itemList);
			//4����չ��ﳵ
			cartMap.clear();
			
			//5����ʾ��ӳɹ����ص���ҳ
			response.getWriter().write("������ӳɹ�!");
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
