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
		//1、检查用户名是否存在
		if(userDao.findUserByUsername(user.getUsername())!=null){
			throw new MsgException("用户名已经存在");
		}
		//2、如果不存在该用户名，则将用户信息添加到数据库中
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
