package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.domain.Page;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProductService;

public class ProductPage extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String nameStr = request.getParameter("name");
		String categoryStr = request.getParameter("category");
		String minStr = request.getParameter("minprice");
		String maxStr = request.getParameter("maxprice");
		
		int thispage = Integer.parseInt(request.getParameter("thispage"));
		int rowperpage = Integer.parseInt(request.getParameter("rowperpage"));
		
		String name = nameStr!=null?nameStr:"";
		String category=categoryStr!=null?categoryStr:"";
		
		double min = -1;
		double max = Double.MAX_VALUE;
		
		if(minStr!=null&&!"".equals(minStr.trim())){
			min = Double.parseDouble(minStr);
		}
		if(maxStr!=null&&!"".equals(maxStr.trim())){
			max = Double.parseDouble(maxStr);
		}
		
		ProductService service = BasicFactory.getFactory().
				getInstance(ProductService.class);
		
		Page page = service.pageList(thispage,rowperpage,name,category,min,max);
		
		request.setAttribute("page", page);
		
		request.getRequestDispatcher(request.getContextPath()+"/page.jsp")
		                            .forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
