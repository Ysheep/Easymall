package cn.tedu.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncodingFilter implements Filter {
	private String encode;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
        encode = filterConfig.getInitParameter("encode");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
          response.setContentType("text/html;charset="+encode);
          //只解决post的:
          request.setCharacterEncoding(encode);
          chain.doFilter(new MyRequest((HttpServletRequest) request), response);
	}

	@Override
	public void destroy() {}
	
	private class MyRequest extends HttpServletRequestWrapper{
        
		private HttpServletRequest request;
		
		public MyRequest(HttpServletRequest request) {
			super(request);
			this.request = request;
		}
		
		@Override
		public String getParameter(String name) {
			if("get".equalsIgnoreCase(request.getMethod())){
				try {
				String val = request.getParameter(name);
				return val==null?null:new String(val.getBytes("ISO8859-1"),encode);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("aaaaaaaaaaasssss");
				}
			}
			return request.getParameter(name);
		}
		
		@Override
		public Map<String, String[]> getParameterMap() {
			if("get".equalsIgnoreCase(request.getMethod())){
				try {
				Map<String, String[]> map = request.getParameterMap();
				for(Map.Entry<String, String[]> enty:map.entrySet()){
					String[] vals = enty.getValue();
					for (int i = 0; i < vals.length; i++) {
					  vals[i] = new String(vals[i].getBytes("ISO8859-1"),encode);
					}
				}
				return map;
				} catch (Exception e) {
				   e.printStackTrace();
				   throw new RuntimeException();
				}
			}
			return request.getParameterMap();
		}
		
		@Override
		public String[] getParameterValues(String name) {
			if("get".equalsIgnoreCase(request.getMethod())){
				try {
				String []vals = request.getParameterValues(name);
				if(vals!=null){
				   for (int i = 0; i < vals.length; i++) {
					  vals[i] = new String(vals[i].getBytes("ISO8859-1"),encode);
				    }
				}
				return vals;
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException();
				}
		     }
			return request.getParameterValues(name);
	     }
	}	
}
