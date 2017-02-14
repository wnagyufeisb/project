package com.kong.center.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.kong.center.beans.ActivateLogBean;

public class ActivateLogDao {
	/**
	 * 获取session
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = ConnectionFactory
				.getSessionFactory();
		return sqlSessionFactory.openSession();
	}
	/***
	 * 添加激活日志
	 * 
	 * @param activateLogBean
	 */
	public void addActivateLog(ActivateLogBean activateLogBean){
		try(SqlSession sqlSession=getSession()){
		IActivateLog iActivateLog = sqlSession.getMapper(IActivateLog.class);
		iActivateLog.addActivateLog(activateLogBean);
		sqlSession.commit(true);
		}
	}
	/***
	 * 获取激活日志信息
	 * @param game
	 * @param guid
	 * @return
	 */
	public ActivateLogBean getActivateLogByGame(String game,String guid){
		try(SqlSession sqlSession=getSession()){
			IActivateLog iActivateLog = sqlSession.getMapper(IActivateLog.class);
			return iActivateLog.getActivateLogByGame(game, guid);
	}
	}
	
	
	
}
