package cn.tedu.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import cn.tedu.annotation.Transaction;
import cn.tedu.dao.Dao;
import cn.tedu.service.Service;
import cn.tedu.utils.PropUtils;
import cn.tedu.utils.TransactionManager;

public class BasicFactory {
	private BasicFactory(){}
	private static BasicFactory factory = new BasicFactory();
	public static BasicFactory getFactory(){
		return factory;
	}
	public <T> T getInstance(Class<T> clz){
		//需要字符串UserDao
		//而传递过来的是UserDao.class  :clz.getName()--"cn.tedu.dao.UserDao
		//获取接口名对应实现类的全路径名                                              UserDao等
		String implStr = PropUtils.getProp(clz.getSimpleName());
		
		if(Service.class.isAssignableFrom(clz)){
			try {
				Class implClz = Class.forName(implStr);
				 final T t = (T) implClz.newInstance();
				@SuppressWarnings("unchecked")
				T proxy = (T) Proxy.newProxyInstance(
                 /*clz.getClassLoader()????????*/
				t.getClass().getClassLoader(),
				/*如果还需要其他接口就这样写：new Class[]{clz}*/	
	            t.getClass().getInterfaces(), new InvocationHandler() {
							
							@Override  /*不要用这里的proxy调用方法，否则会形成死循环*/
							public Object invoke(Object proxy, Method method, Object[] args)
									throws Throwable {
								if(method.isAnnotationPresent(Transaction.class)){
									try {
										System.out.println("------------------>事务开始");
										TransactionManager.startTran();
										Object invoke = method.invoke(t, args);
										TransactionManager.commitTran();
										System.out.println("------------------->事务提交");
										return invoke;
									} catch(InvocationTargetException e){ 
										TransactionManager.rollbackTran();
										System.out.println("------------------->事务回滚");
										throw e.getTargetException();
									} catch (Exception e) {
										TransactionManager.rollbackTran();
										System.out.println("------------------->事务回滚");
										e.printStackTrace();
										throw e.getCause();
									}finally{
										TransactionManager.release();
									}
								}else{
									try {
										return method.invoke(t, args);
									} catch (Exception e) {
										TransactionManager.release();//??????????
										e.printStackTrace();
										throw e.getCause();
									}
								}
							}
						});
				return proxy;//不写就没有对象返回，会出现空指针异常
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}else if(Dao.class.isAssignableFrom(clz)){
			try {
				//有类的全路径名，使用反射创建对象             cn.tedu.dao.UserDao等
				Class implClz = Class.forName(implStr);
				//创建对象
				return (T)implClz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
			
		}else{
			throw new RuntimeException("别乱来，只能获取Service或Dao子接口的实现类对象");
		}
	}
}
