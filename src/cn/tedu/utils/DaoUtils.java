package cn.tedu.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DaoUtils {
	private static ComboPooledDataSource source = new ComboPooledDataSource();

	public static ComboPooledDataSource getSource() {
		return source;
	}
    
	public static Connection getConnection() throws Exception {
       return source.getConnection();
	}
	
	@Deprecated
	public static boolean getResultSetNext(String sql,Object...args){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				preparedStatement.setObject(i+1, args[i]);
			}
			resultSet = preparedStatement.executeQuery();
			return resultSet.next();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(); 
		} finally {
			release(connection, preparedStatement, resultSet);
		}
	}
	
	@Deprecated
	public static <T> T query(String sql,
			ResultSetHandler<T> handler,Object ... params){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DaoUtils.getConnection();
			ps = conn.prepareStatement(sql);
			//Ϊռλ����ֵ��Object... params��������������д������ΪObject�������鴫�ݽ���
			for(int i = 1;i<=params.length;i++){
				//ռλ���±��1��ʼ
				ps.setObject(i, params[i-1]);
			}
			//ִ�в�ѯ����
			rs = ps.executeQuery();
			//��rs������е����ݷ�װ��JavaBean�������javaBean�������List���϶���
			return handler.handler(rs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally{
			release(conn, ps, rs);
		}
		
	}
	
	
	@Deprecated
	public static int Update(String sql,Object...args){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				preparedStatement.setObject(i+1, args[i]);
			}
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally{
			release(connection, preparedStatement, null);
		}
	}
	
	//�����Է���,��������
		public static <T> T query(Connection conn,String sql,
				ResultSetHandler<T> handler,Object ... params){
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = conn.prepareStatement(sql);
				//Ϊռλ����ֵ��Object... params��������������д������ΪObject�������鴫�ݽ���
				for(int i = 1;i<=params.length;i++){
					//ռλ���±��1��ʼ
					ps.setObject(i, params[i-1]);
				}
				//ִ�в�ѯ����
				rs = ps.executeQuery();
				//��rs������е����ݷ�װ��JavaBean�������javaBean�������List���϶���
				return handler.handler(rs);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}finally{
				release(null, ps, rs);
			}
			
		}
	
	//�����Է���,��������
	public static int Update(Connection connection,String sql,Object...args){
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				preparedStatement.setObject(i+1, args[i]);
			}
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally{
			release(null, preparedStatement, null);
		}
	}
	
	
	public static void release(Connection connection,Statement statement,ResultSet resultSet){
		if(connection!=null){
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				connection=null;
			}
		}
		if(statement!=null){
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				statement=null;
			}
		}
		if(resultSet!=null){
			try {
				resultSet.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				resultSet=null;
			}
		}
	}
}
