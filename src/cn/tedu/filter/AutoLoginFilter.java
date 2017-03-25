package cn.tedu.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import cn.tedu.domain.User;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.UserService;
import cn.tedu.utils.MD5Utils;

public class AutoLoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		
	    Cookie[] cookies = req.getCookies();
	    Cookie autologin = null;
	    if(cookies!=null){
	    	for (Cookie cookie : cookies) {
	    		if("autologin".equals(cookie.getName())){
	    			autologin = cookie;
	    		}
	    	}
	    }
		
		if(autologin!=null){
			String str = URLDecoder.decode(autologin.getValue(), "utf-8");
			String username = str.split(":")[0];
			String password = str.split(":")[1];
			UserService service = BasicFactory.getFactory()
					.getInstance(UserService.class);
			User user = service.login(username, MD5Utils.md5(password));
			//检验存在cookie中的autologin内的用户名和密码在数据库中存不存在
			if(user!=null){
				req.getSession().setAttribute("user", user);
			}
		} 
	    
        chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
