package cn.tedu.web.back;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.domain.SaleInfo;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;

public class SaleList extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
		List<SaleInfo> saleList = service.findSaleList();
		request.setAttribute("saleList", saleList);
		request.getRequestDispatcher("/back/saleList.jsp").forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
