package cn.tedu.dao;

import java.sql.ResultSet;
import java.util.List;

import cn.tedu.annotation.Transaction;
import cn.tedu.domain.Product;
import cn.tedu.utils.BeanHandler;
import cn.tedu.utils.BeanListHandler;
import cn.tedu.utils.DaoUtils;
import cn.tedu.utils.ResultSetHandler;
import cn.tedu.utils.TransactionManager;

public class ProductDaoImpl implements ProductDao{

	@Override
	public void addProd(Product prod) {
		String sql = "insert into products(id,name,price,category,pnum,imgurl,description) " +
				"values(?,?,?,?,?,?,?)";
		DaoUtils.Update(TransactionManager.getConn(),sql, prod.getId(),prod.getName(),prod.getPrice(),prod.getCategory(),
				prod.getPnum(),prod.getImgurl(),prod.getDescription());
		
	}

	@Override
	public List<Product> findAll() {
		String sql = "select * from products";
		return DaoUtils.query(TransactionManager.getConn(),sql, new BeanListHandler<Product>(Product.class));
	}


	@Override
	public int deleteProd(String id) {
		String sql = "delete from products where id=?";
		return DaoUtils.Update(TransactionManager.getConn(),sql, id);
	}

	@Override
	public int getProdSum(String name, String category, double min, double max) {
		String sql = "select count(*) from products where name like ?" +
				"and category like ? and price>? and price<?";
		return DaoUtils.query(TransactionManager.getConn(),sql, new ResultSetHandler<Integer>() {

			@Override
			public Integer handler(ResultSet rs) throws Exception {
				rs.next();
				return rs.getInt(1);
			}
		}, "%"+name+"%","%"+category+"%",min,max);
	}

	@Override
	public List<Product> getProdByKeyLimit(String name, String category,
			double min, double max, int begin, int rowperpage) {
		String sql = "select * from products where name like ? and category like ? " +
				" and price > ? and price< ? limit ?,?";
		return DaoUtils.query(TransactionManager.getConn(),sql,new BeanListHandler<Product>(Product.class),
				 "%"+name+"%","%"+category+"%",min,max,begin,rowperpage);
	}
	
	@Override
	public Product findProdById(String id) {
		String sql = "select * from products where id = ?";
		return DaoUtils.query(TransactionManager.getConn(),sql, new BeanHandler<Product>(Product.class), id);
	}
	
	@Override
	public int updatePnum(String id, int newPnum) {
		String sql = "update products set pnum=? where id=?";
		return DaoUtils.Update(TransactionManager.getConn(),sql, newPnum,id);
	}

	/*@Override
	public Product transactionFindProdById(String product_id) {
		String sql = "select * from products where id = ?";
		return DaoUtils.query(TransactionManager.getConn(), sql, 
				new BeanHandler<Product>(Product.class), product_id);
	}

	@Override
	public void transactionUpdatePnum(String id, int newNum) {
		String sql = "update products set pnum = ? where id = ?";
		DaoUtils.Update(TransactionManager.getConn(), sql, newNum,id);
	}
*/
}
