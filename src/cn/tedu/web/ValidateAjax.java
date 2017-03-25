package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.domain.User;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.UserService;
import cn.tedu.utils.DaoUtils;

public class ValidateAjax extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
        String username = request.getParameter("username");
        UserService service = BasicFactory.getFactory().getInstance(UserService.class);
        
 		try {
 			User user = service.findByUname(username);
 			if(user!=null){
 				response.getWriter().write("�û����Ѿ���ע��");
 			}else{
 				response.getWriter().write("��ϲ���û�������ʹ��");
 			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
