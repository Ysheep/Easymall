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
		
		//1�����ղ���
		String id = request.getParameter("id");
		//2������������ProductService
	    ProductService service = BasicFactory.getFactory().
				getInstance(ProductService.class);
		//3�����ø���id��ѯ��Ʒ��Ϣ�ķ���
		Product prod = service.findProdById(id);
		//4��ȡ��Ʒ��ͼƬ��url��ת����ͼƬ<img>,ʹͼƬ�Զ����͵������:ʵ���Ͼ����ļ�������
		request.getRequestDispatcher(prod.getImgurl()).
		                    forward(request, response);

	}

}
