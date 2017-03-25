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

public class UpdateCart extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		int newBuyNum = Integer.parseInt(request.getParameter("newBuyNum"));
		
		ProductService service = BasicFactory.getFactory()
				                 .getInstance(ProductService.class);
		Product prod = service.findProdById(id);
		Map<Product,Integer> cart = null;
		Object obj = request.getSession().getAttribute("cart");
		if(obj!=null){
			cart = (Map<Product,Integer>)obj;
		}else{
			cart = new HashMap<Product,Integer>();
			request.getSession().setAttribute("cart", cart);
		}
		cart.put(prod, newBuyNum);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
