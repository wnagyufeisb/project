package com.kong.center.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.kong.center.beans.LoadLogBean;

public class LoadLogDao {
	/**
	 * 获取session
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = ConnectionFactory
				.getSessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 增加用户登录日志
	 * 
	 * @param loadLogBean
	 */
	public void addLoadInfo(LoadLogBean loadLogBean){
		try(SqlSession sqlSession=getSession()){
			ILoadLog iLoadLog = sqlSession.getMapper(ILoadLog.class);
			iLoadLog.addLoadInfo(loadLogBean);
			sqlSession.commit(true);
		}
		
	}
}
