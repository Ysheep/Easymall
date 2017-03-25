package cn.tedu.dao;


import java.util.List;

import cn.tedu.domain.Order;
import cn.tedu.domain.OrderItem;
import cn.tedu.domain.SaleInfo;
import cn.tedu.utils.BeanHandler;
import cn.tedu.utils.BeanListHandler;
import cn.tedu.utils.DaoUtils;
import cn.tedu.utils.TransactionManager;
import cn.tedu.web.back.SaleList;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void addOrder(Order order) {
		String sql = "insert into orders(id,ordertime,receiverAddress," +
				"paystate,user_id,money) values(?,?,?,?,?,?)";
		DaoUtils.Update(TransactionManager.getConn(), sql, order.getId(),
				order.getOrdertime(),order.getReceiverAddress(),
				order.getpaystate(),order.getUser_id(),order.getMoney());
	}

	@Override
	public void addOrderItem(OrderItem item) {
		String sql = "insert into orderItems(order_id,product_id,buynum) values(?,?,?)";
		DaoUtils.Update(TransactionManager.getConn(), sql, item.getOrder_id(),
				item.getProduct_id(),item.getBuynum());
	}

	@Override
	public List<Order> findAllOrderByUserId(int id) {
		String sql = "select * from orders where user_id = ?";
		return DaoUtils.query(TransactionManager.getConn(),sql, new BeanListHandler<Order>(Order.class),id);
	}

	@Override
	public List<OrderItem> findItemsByOrderId(String id) {
		String sql = "select * from orderItems where order_id = ?";
		return DaoUtils.query(TransactionManager.getConn(), sql, new BeanListHandler<OrderItem>(OrderItem.class), id);
	}

	@Override
	public void delOrderById(String order_id) {
		String sql = "delete from orders where id = ?";
		DaoUtils.Update(TransactionManager.getConn(), sql, order_id);
	}

	@Override
	public void delOrderItemsByOrderId(String order_id) {
		String sql = "delete from orderItems where order_id = ?";
		DaoUtils.Update(TransactionManager.getConn(), sql, order_id);
	}

	@Override
	public Order findOrderById(String id) {
		String sql = "select * from orders where id = ?"; 
		return DaoUtils.query(TransactionManager.getConn(), sql, new BeanHandler<Order>(Order.class), id);
	}

	@Override
	public void updatePaystateByOrderId(String id, int i) {
		String sql = "update orders set paystate = ? where id = ?";
		DaoUtils.Update(TransactionManager.getConn(), sql, i,id);
	}

	@Override
	public List<SaleInfo> findSaleList() {//使用别名int类型返回的是BigDecimal类型，会出现type mismatch
		String sql = "select pr.id prod_id,pr.name prod_name,sum(odt.buynum) sale_num"+
			" from products pr,orderitems odt,orders od"+
			" where pr.id=odt.product_id and odt.order_id=od.id and od.paystate=1"+
			" group by prod_id"+
			" order by sale_num desc"+
			" limit 0,100";
		return DaoUtils.query(TransactionManager.getConn(),sql, new BeanListHandler<SaleInfo>(SaleInfo.class));
	}

}
