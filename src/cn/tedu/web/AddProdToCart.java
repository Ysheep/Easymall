package cn.tedu.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.domain.Product;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProductService;

public class AddProdToCart extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String buynumStr = request.getParameter("buynum");
		
		int buynum = 1;
		if(buynumStr!=null){
			buynum = Integer.parseInt(buynumStr);
		}
		ProductService service = BasicFactory.getFactory()
				.getInstance(ProductService.class);
		Product prod = service.findProdById(id);
		
		Map<Product,Integer> cart = null;
		Object obj = request.getSession().getAttribute("cart");
		if(obj!=null){
			cart = (Map<Product, Integer>) obj;
		}else{
			cart = new HashMap<Product, Integer>();
		}
		if(cart.containsKey(prod)){
			cart.put(prod, cart.get(prod)+buynum);
		}else{
			cart.put(prod, 1);
		}
		request.getSession().setAttribute("cart", cart);
	    
		response.getWriter().write("已成功添加"+buynum+"件~"+prod.getName()+"~商品到购物车");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
