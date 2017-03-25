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
		//��Ҫ�ַ���UserDao
		//�����ݹ�������UserDao.class  :clz.getName()--"cn.tedu.dao.UserDao
		//��ȡ�ӿ�����Ӧʵ�����ȫ·����                                              UserDao��
		String implStr = PropUtils.getProp(clz.getSimpleName());
		
		if(Service.class.isAssignableFrom(clz)){
			try {
				Class implClz = Class.forName(implStr);
				 final T t = (T) implClz.newInstance();
				@SuppressWarnings("unchecked")
				T proxy = (T) Proxy.newProxyInstance(
                 /*clz.getClassLoader()????????*/
				t.getClass().getClassLoader(),
				/*�������Ҫ�����ӿھ�����д��new Class[]{clz}*/	
	            t.getClass().getInterfaces(), new InvocationHandler() {
							
							@Override  /*��Ҫ�������proxy���÷�����������γ���ѭ��*/
							public Object invoke(Object proxy, Method method, Object[] args)
									throws Throwable {
								if(method.isAnnotationPresent(Transaction.class)){
									try {
										System.out.println("------------------>����ʼ");
										TransactionManager.startTran();
										Object invoke = method.invoke(t, args);
										TransactionManager.commitTran();
										System.out.println("------------------->�����ύ");
										return invoke;
									} catch(InvocationTargetException e){ 
										TransactionManager.rollbackTran();
										System.out.println("------------------->����ع�");
										throw e.getTargetException();
									} catch (Exception e) {
										TransactionManager.rollbackTran();
										System.out.println("------------------->����ع�");
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
				return proxy;//��д��û�ж��󷵻أ�����ֿ�ָ���쳣
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}else if(Dao.class.isAssignableFrom(clz)){
			try {
				//�����ȫ·������ʹ�÷��䴴������             cn.tedu.dao.UserDao��
				Class implClz = Class.forName(implStr);
				//��������
				return (T)implClz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
			
		}else{
			throw new RuntimeException("��������ֻ�ܻ�ȡService��Dao�ӽӿڵ�ʵ�������");
		}
	}
}
