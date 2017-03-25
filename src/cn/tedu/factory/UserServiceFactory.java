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
		//如果这么设计，每次调用该方法时，文件都会被重新加载一次，效率太低
		String UserServiceImplStr = PropUtils.getProp("UserService");
		try {
			//有类的全路径名，使用反射创建对象
			Class clz = Class.forName(UserServiceImplStr);
			//创建对象
			return (UserService)clz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
}
