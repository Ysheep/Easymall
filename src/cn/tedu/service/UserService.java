package cn.tedu.service;

import cn.tedu.domain.User;



public interface UserService extends Service{

	/**����û�ע��Ĺ���
	 * @param user����װһ���û���ϢUser��Ķ���
	 * @throws Exception
	 */
	public void regist(User user) throws Exception;
	/**ʵ�ֵ�¼�������û����������ѯ�û�
	 * @param username���û���
	 * @param password������
	 * @return ��¼�ɹ����ظ��û�����Ϣ��ʧ���򷵻�null
	 */
	public User login(String username, String password);
	/**
	 * ����û����Ƿ����
	 * @param username���û���
	 * @return ���ڷ����û���Ϣ�������ڷ���null
	 */
	public User findByUname(String username);

}
