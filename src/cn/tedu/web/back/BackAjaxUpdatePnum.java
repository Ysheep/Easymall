package cn.tedu.web.back;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProductService;

public class BackAjaxUpdatePnum extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String newPnumStr = request.getParameter("newPnum");
		int newNum = Integer.parseInt(newPnumStr);
		
		ProductService service = BasicFactory.getFactory().getInstance(ProductService.class);
		int data = service.updatePnum(id, newNum);
		
		response.getWriter().write((data>0)+"");
 
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
