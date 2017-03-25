package cn.tedu.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.domain.Product;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProductService;

public class DelProdInCart extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		ProductService service = BasicFactory.getFactory().
				getInstance(ProductService.class);
		Product prod = service.findProdById(id);
		
		Map<Product,Integer> cart = (Map<Product,Integer>)request.
				getSession().getAttribute("cart");
		cart.remove(prod);
		response.sendRedirect(request.getContextPath()+"/cart.jsp");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
