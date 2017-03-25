package cn.tedu.dao;

import cn.tedu.domain.User;
import cn.tedu.utils.BeanHandler;
import cn.tedu.utils.DaoUtils;
import cn.tedu.utils.TransactionManager;

public class UserDaoImpl implements UserDao {
	
	public User findUserByUsername(String username) {

		try {
			String sql = "select * from user where username=?";
			return DaoUtils.query(TransactionManager.getConn(),sql, new BeanHandler<User>(User.class), username);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public User findUserByUsernameAndPwd(String username, String password) {
		try {
			String sql = "select * from user where username=? and password=?";
			return DaoUtils.query(TransactionManager.getConn(),sql, new BeanHandler<User>(User.class), username,password);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public void addUser(User user) {
		try{
			String sql = "insert into user(id,username,password,nickname,email) values(null, ?, ?, ?, ?)";
			DaoUtils.Update(TransactionManager.getConn(),sql, user.getUsername(),user.getPassword(),
					user.getNickname(),user.getEmail());
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
