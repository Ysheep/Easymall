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
		  throw new MsgException("form����д���������ύ��ʽ���Ƿ�ʹ�ö�ý���װ");
		}
		
		map.put("id", UUID.randomUUID().toString());
		
		//1����ͼƬ�ϴ���������
		//1.1���建���λ��
		String temp = "/WEB-INF/temp";
		//1.2�ļ��ϴ���·��
		String path = "/WEB-INF/upload";
		//1.3�����ļ��ϴ��Ĺ�������
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//1.4���û�������С
		factory.setSizeThreshold(1024*10);
		//1.5������ʱ�ļ��ı���λ��
		factory.setRepository(new File(getServletContext().getRealPath(temp)));
		//1.6�����ļ��ϴ��ĺ�����Ķ���
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		//1.7�������ļ������������
		fileUpload.setHeaderEncoding("utf-8");
		//1.8�����õ����ļ��Ĵ�С
		fileUpload.setFileSizeMax(1024*1024*2);
		//1.9����form���ϴ��ļ����ֵ
		fileUpload.setSizeMax(1024*1024*2);
		try {
			//1.10����request����
			List<FileItem> items = fileUpload.parseRequest(request);
			//1.11��������ÿһ������
			for (FileItem item : items) {
				//1.12�ж��Ƿ�Ϊ��ͨ����
				if(item.isFormField()){
					map.put(item.getFieldName(), item.getString("utf-8"));
				}else{//������ļ��ϴ���
					//1.13��ȡ�ϴ��ļ�������
					String fname = item.getName();
					//IE���ְ汾�������ȡ�ļ�����ʱ�򣬻��ȡ������·������ȡ�ļ���
					if(fname.indexOf("\\")!=-1){
						fname = fname.substring(fname.lastIndexOf("\\")+1);
					}
					//�����ļ�����
					fname = UUID.randomUUID().toString()+"_"+fname;
					String hash = Integer.toHexString(fname.hashCode());
					char chs[] = hash.toCharArray();
					//ƴ·��
					for (char c : chs) {
						path = path +"/"+ c;//WEB-INF/upload/5/9/a/d/...
					}
					//����Ŀ¼
					new File(getServletContext().getRealPath(path)).mkdirs();
					//��imgurl���Ա�����map����
					//map.put("imgurl", path+"/"+fname);
					map.put(item.getFieldName(), path+"/"+fname);
					//IO����
					InputStream in = item.getInputStream();
					OutputStream out = new FileOutputStream(getServletContext().
							getRealPath(path+"/"+fname));
					byte[] bt = new byte[1024];
					int len = -1;
					while((len=in.read(bt))!=-1){
						out.write(bt, 0, len);
					}
					//ˢ�������ر���
					out.flush();
					out.close();
					in.close();
					//ɾ����ʱ�ļ�
					item.delete();
				}
			}
			//2������Ʒ��Ϣ��ӵ����ݿ��С�������
			Product prod = new Product();
			BeanUtils.populate(prod, map);
			ProductService service = BasicFactory.getFactory().
					          getInstance(ProductService.class);
			service.addProd(prod);
			//3����ʾ��Ʒ��ӳɹ����ص���̨��Ʒ���б�ҳ��
			response.getWriter().write("��Ʒ��ӳɹ�");
			response.setHeader("Refresh", "2;url="+request.getContextPath()+"/servlet/BackProdListServlet");
		}catch(SizeLimitExceededException se){
			response.getWriter().write("�ļ��ϴ������趨��С");
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

}
