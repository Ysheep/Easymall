package cn.tedu.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
/**將ResultSetj结果集中第一行封装到JavaBean对象中
 * @param <T>
 */
public class BeanHandler<T> implements ResultSetHandler<T>{
	private Class<T> clz;
	public BeanHandler(Class<T> clz){
		this.clz = clz;
	}
	public T handler(ResultSet rs) throws Exception {
		if(rs.next()){
			//创建javabean对象
			T t = clz.newInstance();
			//将结果集中的值
			BeanInfo bi = Introspector.getBeanInfo(clz);
			PropertyDescriptor[] pds = bi.getPropertyDescriptors();
			for (PropertyDescriptor pd : pds) {
				//获取属性名称
				String name = pd.getName();
				Method mtd = pd.getWriteMethod();
				try{
					//调用该方法为该属性赋值  class
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
