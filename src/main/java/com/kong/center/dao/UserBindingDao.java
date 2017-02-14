package com.kong.center.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.kong.center.beans.UserBindingBean;

public class UserBindingDao {

	/**
	 * 获取session
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = ConnectionFactory
				.getSessionFactory();
		return sqlSessionFactory.openSession();
	}

	public UserBindingBean getUserbindingInfo(String bindingNumber) {
		try (SqlSession sqlSession = getSession()) {
			IUserBinding iUserBinding = sqlSession
					.getMapper(IUserBinding.class);
			return iUserBinding.getUserbindingInfo(bindingNumber);
		}
	}

	/***
	 * 增加用户绑定信息
	 * 
	 * @param userBindingBean
	 */
	public void addUserBindingInfo(UserBindingBean userBindingBean,
			SqlSession sqlSession) {
		IUserBinding iUserBinding = sqlSession.getMapper(IUserBinding.class);
		iUserBinding.addUserBindingInfo(userBindingBean);
	}

}
