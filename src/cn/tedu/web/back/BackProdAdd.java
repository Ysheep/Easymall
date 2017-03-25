package cn.tedu.web.back;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.tedu.domain.Product;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProductService;

public class BackProdAdd extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		   doGet(req, resp);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Map<String,String> map = new HashMap<String, String>();
		
		if(!ServletFileUpload.isMultipartContent(request)){
		  throw new MsgException("form表单编写错误，请检测提交方式和是否使用多媒体封装");
		}
		
		map.put("id", UUID.randomUUID().toString());
		
		//1、将图片上传到服务器
		//1.1定义缓存的位置
		String temp = "/WEB-INF/temp";
		//1.2文件上传的路径
		String path = "/WEB-INF/upload";
		//1.3创建文件上传的工厂对象
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//1.4设置缓冲区大小
		factory.setSizeThreshold(1024*10);
		//1.5设置临时文件的保存位置
		factory.setRepository(new File(getServletContext().getRealPath(temp)));
		//1.6生成文件上传的核心类的对象
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		//1.7、处理文件名乱码的问题
		fileUpload.setHeaderEncoding("utf-8");
		//1.8、设置单个文件的大小
		fileUpload.setFileSizeMax(1024*1024*2);
		//1.9设置form表单上传文件最大值
		fileUpload.setSizeMax(1024*1024*2);
		try {
			//1.10解析request对象
			List<FileItem> items = fileUpload.parseRequest(request);
			//1.11遍历处理每一个表单项
			for (FileItem item : items) {
				//1.12判断是否为普通表单项
				if(item.isFormField()){
					map.put(item.getFieldName(), item.getString("utf-8"));
				}else{//如果是文件上传项
					//1.13获取上传文件的名称
					String fname = item.getName();
					//IE部分版本浏览器获取文件名的时候，会获取到本地路径：截取文件名
					if(fname.indexOf("\\")!=-1){
						fname = fname.substring(fname.lastIndexOf("\\")+1);
					}
					//避免文件重名
					fname = UUID.randomUUID().toString()+"_"+fname;
					String hash = Integer.toHexString(fname.hashCode());
					char chs[] = hash.toCharArray();
					//拼路径
					for (char c : chs) {
						path = path +"/"+ c;//WEB-INF/upload/5/9/a/d/...
					}
					//创建目录
					new File(getServletContext().getRealPath(path)).mkdirs();
					//将imgurl属性保存在map对象
					//map.put("imgurl", path+"/"+fname);
					map.put(item.getFieldName(), path+"/"+fname);
					//IO操作
					InputStream in = item.getInputStream();
					OutputStream out = new FileOutputStream(getServletContext().
							getRealPath(path+"/"+fname));
					byte[] bt = new byte[1024];
					int len = -1;
					while((len=in.read(bt))!=-1){
						out.write(bt, 0, len);
					}
					//刷新流，关闭流
					out.flush();
					out.close();
					in.close();
					//删除临时文件
					item.delete();
				}
			}
			//2、将商品信息添加到数据库中。。。。
			Product prod = new Product();
			BeanUtils.populate(prod, map);
			ProductService service = BasicFactory.getFactory().
					          getInstance(ProductService.class);
			service.addProd(prod);
			//3、提示商品添加成功，回到后台商品的列表页面
			response.getWriter().write("商品添加成功");
			response.setHeader("Refresh", "2;url="+request.getContextPath()+"/servlet/BackProdListServlet");
		}catch(SizeLimitExceededException se){
			response.getWriter().write("文件上传超过设定大小");
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

}
