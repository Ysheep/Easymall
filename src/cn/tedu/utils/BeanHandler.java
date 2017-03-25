package cn.tedu.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
/**��ResultSetj������е�һ�з�װ��JavaBean������
 * @param <T>
 */
public class BeanHandler<T> implements ResultSetHandler<T>{
	private Class<T> clz;
	public BeanHandler(Class<T> clz){
		this.clz = clz;
	}
	public T handler(ResultSet rs) throws Exception {
		if(rs.next()){
			//����javabean����
			T t = clz.newInstance();
			//��������е�ֵ
			BeanInfo bi = Introspector.getBeanInfo(clz);
			PropertyDescriptor[] pds = bi.getPropertyDescriptors();
			for (PropertyDescriptor pd : pds) {
				//��ȡ��������
				String name = pd.getName();
				Method mtd = pd.getWriteMethod();
				try{
					//���ø÷���Ϊ�����Ը�ֵ  class
					mtd.invoke(t, rs.getObject(name));
				}catch (SQLException e) {
					continue;
				}
			}
			return t;
		}
		return null;
	}

}
