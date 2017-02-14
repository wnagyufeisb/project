package com.kong.center.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.kong.center.beans.RegisterLogBean;

public class RegisterLogDao {
	/**
	 * 获取session
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = ConnectionFactory
				.getSessionFactory();
		return sqlSessionFactory.openSession();
	}

	/***
	 * 增加注册日志信息
	 * 
	 * @param userInfoBean
	 */
	public void addRegisterLog(RegisterLogBean registerLog,
			SqlSession sqlSession) {
		IRegisterLog iRegisterLog = sqlSession.getMapper(IRegisterLog.class);
		iRegisterLog.addRegisterLog(registerLog);
	}
}
