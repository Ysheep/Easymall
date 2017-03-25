package cn.tedu.service;

import cn.tedu.domain.User;



public interface UserService extends Service{

	/**完成用户注册的功能
	 * @param user：封装一个用户信息User类的对象
	 * @throws Exception
	 */
	public void regist(User user) throws Exception;
	/**实现登录：根据用户名和密码查询用户
	 * @param username：用户名
	 * @param password：密码
	 * @return 登录成功返回该用户的信息，失败则返回null
	 */
	public User login(String username, String password);
	/**
	 * 检查用户名是否存在
	 * @param username：用户名
	 * @return 存在返回用户信息，不存在返回null
	 */
	public User findByUname(String username);

}
