package cn.tedu.service;

import java.util.List;

import cn.tedu.domain.Page;
import cn.tedu.domain.Product;

public interface ProductService extends Service{
	
	public void addProd(Product prod);
	
	public List<Product> findAll();

	public Product findProdById(String id);
	
	public int updatePnum(String id,int newNum);
	
	public int deleteProd(String id);

	public Page pageList(int thispage, int rowperpage, String name,
			String category, double min, double max);

}
