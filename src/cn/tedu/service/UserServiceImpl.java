package cn.tedu.service;

import cn.tedu.dao.UserDao;
import cn.tedu.domain.User;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.utils.DaoUtils;


public class UserServiceImpl implements UserService{
	
	private UserDao userDao = (UserDao) BasicFactory.getFactory().
			                  getInstance(UserDao.class);
	@Override
	public void regist(User user)  {
		//1������û����Ƿ����
		if(userDao.findUserByUsername(user.getUsername())!=null){
			throw new MsgException("�û����Ѿ�����");
		}
		//2����������ڸ��û��������û���Ϣ��ӵ����ݿ���
		userDao.addUser(user);
	}

	@Override
	public User login(String username, String password) {
		return userDao.findUserByUsernameAndPwd(username,password);
	}

	@Override
	public User findByUname(String username) {
		return userDao.findUserByUsername(username);
	}


}
