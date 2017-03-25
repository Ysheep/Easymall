package cn.tedu.tag;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class MyUrlDecode extends SimpleTagSupport{
	
	
	public void doTag() throws JspException, IOException {
		super.doTag();
		PageContext pc = (PageContext)getJspContext();
		Cookie[] cs = ((HttpServletRequest)(pc.getRequest())).getCookies();
		Cookie remnameC = null;
		if(cs != null){
			for(Cookie c:cs){
				if("username".equals(c.getName())){
					remnameC = c;
				}
			}
		}
		String username = "";
		if(remnameC != null){
			username = URLDecoder.decode(remnameC.getValue(), "utf-8");
		}
		getJspContext().getOut().write(username);
		
	}
}
