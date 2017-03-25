package cn.tedu.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropUtils {
	private static Properties prop = null;
	private static String filename = "config.properties";
	private PropUtils(){}
	//�����������ú��ô������¼��شﵽ���õ�Ŀ�ģ�
	public static void setFilename(String filename) {
		PropUtils.filename = filename;
	}
	
	static{
		prop = new Properties();
		try {
			prop.load(new FileInputStream(PropUtils.class.getClassLoader().getResource(filename).getPath()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public static Properties getProp(){
		return prop;
	}
	
	public static String getProp(String key){
		return prop.getProperty(key);
	}

}
