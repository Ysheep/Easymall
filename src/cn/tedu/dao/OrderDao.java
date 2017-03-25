package cn.tedu.dao;

import java.util.List;

import cn.tedu.domain.Order;
import cn.tedu.domain.OrderItem;
import cn.tedu.domain.SaleInfo;

public interface OrderDao extends Dao{
	
	
	void addOrder(Order order);
	
	void addOrderItem(OrderItem item);

	List<Order> findAllOrderByUserId(int id);

	List<OrderItem> findItemsByOrderId(String id);

	void delOrderById(String order_id);

	void delOrderItemsByOrderId(String order_id);

	Order findOrderById(String id);

	void updatePaystateByOrderId(String id, int i);

	List<SaleInfo> findSaleList();

}
