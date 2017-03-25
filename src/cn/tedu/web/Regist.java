package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.tedu.domain.User;
import cn.tedu.service.UserService;
import cn.tedu.service.UserServiceImpl;
import cn.tedu.utils.MD5Utils;

public class Regist extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		User user = new User();
		
		try {
			BeanUtils.populate(user, request.getParameterMap());
			
			String code = (String) request.getSession().getAttribute("code");
			user.check(code);
			
			UserService us = new UserServiceImpl();
			user.setPassword(MD5Utils.md5(user.getPassword()));
			user.setPassword2(MD5Utils.md5(user.getPassword2()));
			us.regist(user);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
		}  
		
		response.getWriter().write("恭喜注册成功, 3秒之后跳转到首页...如果没有跳转,"+"可以点击下面的链接:<br>" +
				"<a href='"+request.getContextPath()+"/index.jsp'>http://www.easymall.com</a>");
		response.setHeader("Refresh", "3;url="+request.getContextPath()+"/index.jsp");
	}

}
