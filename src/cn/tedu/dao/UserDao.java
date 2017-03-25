package cn.tedu.dao;

import cn.tedu.domain.User;

public interface UserDao extends Dao{
	/**�����û�����ѯ���û�����Ϣ
	 * @param username���û���
	 * @return ���û�����Ӧ�û���Ϣ�������򷵻ظ��û���Ϣ�����淵��null
	 */
	public User findUserByUsername(String username);
	/**
	 * ����û�
	 * @param user��װ���û���Ϣjavabean����
	 */
	public void addUser(User user);
	/**�����û����������ѯ�û���Ϣ
	 * @param username:�û���
	 * @param password:����
	 * @return �����򷵻ظ��û���Ϣ���������򷵻�null
	 */
	public User findUserByUsernameAndPwd(String username, String password);
	
}
