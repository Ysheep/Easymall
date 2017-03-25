package cn.tedu.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BeanListHandler<T> implements ResultSetHandler<List<T>>{
	private Class<T> clz;
	public BeanListHandler(Class<T> clz){
		this.clz = clz;
	}
	public List<T> handler(ResultSet rs) throws Exception {
		List<T> list = new ArrayList<T>();
		while(rs.next()){
			//����javaBean����
			T t = clz.newInstance();
			//�������װbean������
			BeanInfo bi = Introspector.getBeanInfo(clz);
			PropertyDescriptor pds[] = bi.getPropertyDescriptors();
			for (PropertyDescriptor pd : pds) {
				String name = pd.getName();
				Method mtd = pd.getWriteMethod();
				/*//ִ�и�ֵ����
				try{
					//���ø÷���Ϊ�����Ը�ֵ
					mtd.invoke(t, rs.getObject(name));
				}*/
				Class type = pd.getPropertyType();
				try{
					Object value = null;
		//ר��Ϊ�б����Ĳ�ѯ��saleList��sale_num���Զ�Ӧ�޸�
					if(type==int.class){
						value = rs.getInt(name);
					}else{
						value = rs.getObject(name);
					}
					mtd.invoke(t, value);
				}catch (SQLException e) {
					continue;
				}
			}
			//������ѭ��������javabean������ӵ�list������
			list.add(t);
		}
		return list;
	}

}
