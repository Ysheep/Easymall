package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.domain.Product;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProductService;

public class ProdInfo extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String thispage = request.getParameter("thispage");
		String rowperpage = request.getParameter("rowperpage");
		
		ProductService service = BasicFactory.getFactory()
				           .getInstance(ProductService.class);
		
		Product prod = service.findProdById(id);
		request.setAttribute("prod", prod);
		
		request.setAttribute("rowperpage", rowperpage);
		request.setAttribute("thispage",thispage);
		request.getRequestDispatcher(request.getContextPath()+"/prodInfo.jsp")
		       .forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
