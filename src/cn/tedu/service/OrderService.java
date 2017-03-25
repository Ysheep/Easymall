package cn.tedu.service;

import java.util.List;
import java.util.Map;

import cn.tedu.annotation.Transaction;
import cn.tedu.domain.Order;
import cn.tedu.domain.OrderItem;
import cn.tedu.domain.Product;
import cn.tedu.domain.SaleInfo;
import cn.tedu.domain.User;

public interface OrderService extends Service{
	@Transaction
	void addOrderAndOrderItem(Order order, List<OrderItem> itemList);

	Map<Order,Map<Product,Integer>> findOrderListByUserId(int id);
	@Transaction
	void delOrderMapByOrderId(String order_id);

	Order findOrderById(String order_id);

	void updatePaystateByOrderId(String id, int i);

	List<SaleInfo> findSaleList();


}
