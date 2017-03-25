package cn.tedu.web.back;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.domain.Product;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProductService;

public class ProdImg extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//1、接收参数
		String id = request.getParameter("id");
		//2、声明并创建ProductService
	    ProductService service = BasicFactory.getFactory().
				getInstance(ProductService.class);
		//3、调用根据id查询商品信息的方法
		Product prod = service.findProdById(id);
		//4获取商品的图片的url，转发到图片<img>,使图片自动发送到浏览器:实际上就是文件的下载
		request.getRequestDispatcher(prod.getImgurl()).
		                    forward(request, response);

	}

}
