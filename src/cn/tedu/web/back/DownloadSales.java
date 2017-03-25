package cn.tedu.web.back;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.domain.SaleInfo;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;

public class DownloadSales extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
		List<SaleInfo> list =  service.findSaleList();
		String data = "商品id,商品名称,销售总量\r\n";
		for (SaleInfo info : list) {
			data+=info.toString()+"\r\n";
		}
		//response.setContentType("application/x-msdownload");????????
		//火狐浏览器不好用？？？？？
		String filename = "EasyMall销售榜单_"+new Date().toLocaleString()+".csv";
		response.setHeader("Content-Disposition", "attachment;filename="+filename);
		response.getWriter().write(data);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
