package cn.tedu.factory;

import cn.tedu.dao.UserDao;
import cn.tedu.utils.PropUtils;

public class UserDaoFactory {
	private UserDaoFactory(){}
	private static UserDaoFactory factory = new UserDaoFactory();
	public static UserDaoFactory getFactory(){
		return factory;
	}
	public UserDao getUserDao(){
		/*Properties prop = new Properties();
		prop.load(inStream);*/
		//如果这么设计，每次调用该方法时，文件都会被重新加载一次，效率太低
		String userDaoImplStr = PropUtils.getProp("UserDao");
		try {
			//有类的全路径名，使用反射创建对象
			Class clz = Class.forName(userDaoImplStr);
			//创建对象
			return (UserDao)clz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
}
