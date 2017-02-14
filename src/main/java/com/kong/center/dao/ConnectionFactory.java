package com.kong.center.dao;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class ConnectionFactory {
	private static SqlSessionFactory myBatisConnectionFactory;
	static {

		try {
			String resource = "config/mybatis-config.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			if (myBatisConnectionFactory == null) {
				myBatisConnectionFactory = new SqlSessionFactoryBuilder()
						.build(reader, "UserCenter");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("MyBatisConnectionFactory error" + e);
		}
	}

	/**
	 * 静�?�方法返回SQL实例
	 * 
	 * @return
	 */
	public static SqlSessionFactory getSessionFactory() {

		return myBatisConnectionFactory;
	}

}
