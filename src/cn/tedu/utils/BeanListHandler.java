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
			//创建javaBean对象
			T t = clz.newInstance();
			//将结果封装bean对象中
			BeanInfo bi = Introspector.getBeanInfo(clz);
			PropertyDescriptor pds[] = bi.getPropertyDescriptors();
			for (PropertyDescriptor pd : pds) {
				String name = pd.getName();
				Method mtd = pd.getWriteMethod();
				/*//执行赋值操作
				try{
					//调用该方法为该属性赋值
					mtd.invoke(t, rs.getObject(name));
				}*/
				Class type = pd.getPropertyType();
				try{
					Object value = null;
		//专门为有别名的查询的saleList的sale_num属性对应修改
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
			//将本次循环创建的javabean对象添加到list集合中
			list.add(t);
		}
		return list;
	}

}
