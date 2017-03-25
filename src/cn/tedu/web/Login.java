package cn.tedu.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.domain.User;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.UserService;
import cn.tedu.utils.MD5Utils;

public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String remname = request.getParameter("remname");
			String autologin = request.getParameter("autologin");
		
			if("true".equals(remname)){
				Cookie renameCookie = new Cookie("username", URLEncoder.encode(username,"utf-8"));
				renameCookie.setPath(request.getContextPath()+"/");
				renameCookie.setMaxAge(60*60*24*30);
				response.addCookie(renameCookie);		
		    }else{
		    	Cookie remnameC = new Cookie("username", "");
				remnameC.setPath(request.getContextPath()+"/");
				remnameC.setMaxAge(0);
				response.addCookie(remnameC);
		    }
			
			
				UserService service = BasicFactory.getFactory()
						.getInstance(UserService.class);
				User user = service.login(username, MD5Utils.md5(password));
				
				if("true".equals(autologin)){
					Cookie autologinCookie = new Cookie("autologin",URLEncoder
							.encode(username+":"+password,"utf-8"));
					autologinCookie.setPath(request.getContextPath()+"/");
					autologinCookie.setMaxAge(60*60*24*30);
					response.addCookie(autologinCookie);		
				}
				
				if(user!=null){
					request.getSession().setAttribute("user", user);
					response.sendRedirect(request.getContextPath()+"/index.jsp");
				}else{
					throw new MsgException("用户名或密码错误");
				}
		
		} catch (MsgException e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
