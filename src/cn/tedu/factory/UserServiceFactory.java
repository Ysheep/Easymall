package cn.tedu.factory;

import cn.tedu.service.UserService;
import cn.tedu.utils.PropUtils;

public class UserServiceFactory {
	private UserServiceFactory(){}
	private static UserServiceFactory factory = new UserServiceFactory();
	public static UserServiceFactory getFactory(){
		return factory;
	}
	public UserService getUserService(){
		/*Properties prop = new Properties();
		prop.load(inStream);*/
		//�����ô��ƣ�ÿ�ε��ø÷���ʱ���ļ����ᱻ���¼���һ�Σ�Ч��̫��
		String UserServiceImplStr = PropUtils.getProp("UserService");
		try {
			//�����ȫ·������ʹ�÷��䴴������
			Class clz = Class.forName(UserServiceImplStr);
			//��������
			return (UserService)clz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
}
