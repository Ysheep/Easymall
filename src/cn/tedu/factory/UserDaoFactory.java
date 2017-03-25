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
		//�����ô��ƣ�ÿ�ε��ø÷���ʱ���ļ����ᱻ���¼���һ�Σ�Ч��̫��
		String userDaoImplStr = PropUtils.getProp("UserDao");
		try {
			//�����ȫ·������ʹ�÷��䴴������
			Class clz = Class.forName(userDaoImplStr);
			//��������
			return (UserDao)clz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
}
