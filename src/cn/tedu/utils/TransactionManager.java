package cn.tedu.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>(){
		protected Connection initialValue() {
			try {
				return DaoUtils.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			return null;
		}
	};
	private TransactionManager(){}
	public static Connection getConn(){
		//��threadLocal�л�ȡ�������Ӷ���conn,���threadLocal��û��conn,�����initialValue()��ʼ��һ�����Ӷ���
		//�������ֱ�ӷ���
		return threadLocal.get();
	}
	public static void startTran(){
		try {
			threadLocal.get().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void commitTran(){
		try {
			threadLocal.get().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void rollbackTran(){
		try {
			threadLocal.get().rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void release(){
		try {
			threadLocal.get().close();
			//��threadLocal��ɾ����
			threadLocal.remove();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
