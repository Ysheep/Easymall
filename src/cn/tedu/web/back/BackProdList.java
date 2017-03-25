package cn.tedu.web.back;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.domain.Page;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProductService;

public class BackProdList extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String nameStr = request.getParameter("name");
		String categoryStr = request.getParameter("category");
		String name = nameStr==null?"":nameStr;
		String category = categoryStr==null?"":categoryStr;
		
		int thispage = Integer.parseInt(request.getParameter("thispage"));//1
		int rowperpage = Integer.parseInt(request.getParameter("rowperpage"));//2
//		double min=-1;
//		double max=Double.MAX_VALUE;
		
		ProductService service = BasicFactory.getFactory().
				                 getInstance(ProductService.class);
		
		Page backgroundpage = service.pageList(thispage, rowperpage, name, category, -1, Double.MAX_VALUE);
		
		request.setAttribute("page", backgroundpage);
		
		request.getRequestDispatcher(request.getContextPath()+
				"/back/manageProd.jsp").forward(request, response);

	}

}
