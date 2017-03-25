package cn.tedu.dao;

import java.util.List;

import cn.tedu.domain.Product;

public interface ProductDao extends Dao{
	
	public void addProd(Product prod);
	
	public List<Product> findAll();
	
	public Product findProdById(String id);
	
	public int updatePnum(String id, int newNum);
	
	public int deleteProd(String id);

	public int getProdSum(String name, String category, double min, double max);

    public List<Product> getProdByKeyLimit(String name, String category, 
    		double min, double max,int begin,int rowperpage);

	/*public Product transactionFindProdById(String product_id);

	public void transactionUpdatePnum(String id, int i);*/
}
