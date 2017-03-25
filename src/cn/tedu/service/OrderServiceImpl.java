package cn.tedu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import cn.tedu.dao.OrderDao;
import cn.tedu.dao.ProductDao;
import cn.tedu.domain.Order;
import cn.tedu.domain.OrderItem;
import cn.tedu.domain.Product;
import cn.tedu.domain.SaleInfo;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.utils.BeanHandler;
import cn.tedu.utils.DaoUtils;
import cn.tedu.utils.TransactionManager;

public class OrderServiceImpl implements OrderService {
	
	private OrderDao orderDao = BasicFactory.getFactory().
			                  getInstance(OrderDao.class);
	private ProductDao prodDao = BasicFactory.getFactory().
			                  getInstance(ProductDao.class);
	
	@Override
	public void addOrderAndOrderItem(Order order, List<OrderItem> itemList) {
		try {
			
			orderDao.addOrder(order);
			for (OrderItem item : itemList) {
				Product prod = prodDao.findProdById(item.getProduct_id());
				if(prod.getPnum()>=item.getBuynum()){
					prodDao.updatePnum(prod.getId(),
							 prod.getPnum()-item.getBuynum());
					orderDao.addOrderItem(item);
				}else{
					throw new MsgException("商品<"+prod.getName()+">库存不足，请重新下单");
				}
			}
		} catch (MsgException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
	@Override
	public Map<Order,Map<Product,Integer>> findOrderListByUserId(int id) {
		
		Map<Order,Map<Product,Integer>> orderMaps = new HashMap<Order, Map<Product,Integer>>();
		List<Order> orders = orderDao.findAllOrderByUserId(id);
		if(orders.size()!=0){
		for (Order order : orders) {
			Map<Product,Integer> innerMap = new HashMap<Product,Integer>();
			List<OrderItem> items = orderDao.findItemsByOrderId(order.getId());
			for (OrderItem item : items) {
				Product product = prodDao.findProdById(item.getProduct_id());
				innerMap.put(product, item.getBuynum());
			}
			orderMaps.put(order, innerMap);
		}
		return orderMaps;
		}
		return null;
	}
	@Override
	public void delOrderMapByOrderId(String order_id) {
		try {
			Order order = orderDao.findOrderById(order_id);
			if(order.getpaystate()==1){
				throw new MsgException("只有未支付的才能删除");
			}
			orderDao.delOrderById(order_id);
			orderDao.delOrderItemsByOrderId(order_id);
			List<OrderItem> items = orderDao.findItemsByOrderId(order_id);
			for (OrderItem item : items) {
				Product prod = prodDao.findProdById(item.getProduct_id());
				prodDao.updatePnum(item.getProduct_id(), item.getBuynum()+prod.getPnum());
			}
		} catch(MsgException e){
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	@Override
	public Order findOrderById(String id) {
		return orderDao.findOrderById(id);
	}
	
	@Override
	public void updatePaystateByOrderId(String id, int i) {
		orderDao.updatePaystateByOrderId(id, i);
	}
	@Override
	public List<SaleInfo> findSaleList() {
		return orderDao.findSaleList();
	}

}
