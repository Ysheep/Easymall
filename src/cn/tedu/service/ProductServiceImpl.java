package cn.tedu.service;

import java.util.List;

import cn.tedu.dao.ProductDao;
import cn.tedu.domain.Page;
import cn.tedu.domain.Product;
import cn.tedu.factory.BasicFactory;

public class ProductServiceImpl implements ProductService{
	
	private ProductDao prodDao = BasicFactory.getFactory()
			.getInstance(ProductDao.class);

	@Override
	public void addProd(Product prod) {
		prodDao.addProd(prod);
	}

	@Override
	public List<Product> findAll() {
		return prodDao.findAll();
	}

	@Override
	public Product findProdById(String id) {
		return prodDao.findProdById(id);
	}

	@Override
	public int updatePnum(String id, int newNum) {
		return prodDao.updatePnum(id, newNum);
	}

	@Override
	public int deleteProd(String id) {
		return prodDao.deleteProd(id);
	}

	@Override
	public Page pageList(int thispage, int rowperpage, String name,
			String category, double min, double max) {
		Page page = new Page();
		
		page.setName(name.trim());
		page.setCategory(category.trim());
		if(min!=-1){//如果使用默认值，page.getMin()就是空指针
			page.setMin(min);
		}
		if(max!=Double.MAX_VALUE){
			page.setMax(max);
		}
		page.setThispage(thispage);
		page.setRowperpage(rowperpage);
		
		int sumrow = prodDao.getProdSum(page.getName(),page.getcategory(),min,max);
		page.setSumrow(sumrow);
		
		int sumpage = sumrow/rowperpage+(sumrow%rowperpage==0?0:1);
		page.setSumpage(sumpage);
		
		page.setBackpage(thispage==1?1:(thispage-1));
		
		page.setNextpage(thispage==sumpage?sumpage:(thispage+1));
	
		page.setList(prodDao.getProdByKeyLimit(page.getName(), page.getcategory(),
				min,max,(thispage-1)*rowperpage , rowperpage));
		return page;
	}


	
	

}
