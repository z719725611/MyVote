package com.yc.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.yc.utils.LogUtils;

//单例模式  特点   构造方法私有化
public class MyProperties extends Properties{

	
	private static final long serialVersionUID = 7527422364622115641L;
	private static MyProperties myproperties;
	private static String propertyFileName="db.properties";//注意：db.properties必须放在src下面
	//单例模式
	private MyProperties(){
		//类加载器，是一个类，这个类用于处理器路径下的一些操作
		InputStream lis=MyProperties.class.getClassLoader().getResourceAsStream(propertyFileName);
		
		try {
			load(lis);
		} catch (IOException e) {
			LogUtils.logger.error("error to read properties file",e);
			throw new RuntimeException(e);
		}
		
	}
	//确保单例
	//synchronized:当多线程访问时，保证一次只能有一个请求访问这个方法
	public synchronized static MyProperties getInstance(){
		if(myproperties==null){
			myproperties=new MyProperties();
		}
		return myproperties;
	}
}
