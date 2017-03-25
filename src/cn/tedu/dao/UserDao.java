package cn.tedu.dao;

import cn.tedu.domain.User;

public interface UserDao extends Dao{
	/**根据用户名查询该用户的信息
	 * @param username：用户名
	 * @return 该用户名对应用户信息，存在则返回该用户信息，不存返回null
	 */
	public User findUserByUsername(String username);
	/**
	 * 添加用户
	 * @param user封装了用户信息javabean对象
	 */
	public void addUser(User user);
	/**根据用户名和密码查询用户信息
	 * @param username:用户名
	 * @param password:密码
	 * @return 存在则返回该用户信息，不存在则返回null
	 */
	public User findUserByUsernameAndPwd(String username, String password);
	
}
