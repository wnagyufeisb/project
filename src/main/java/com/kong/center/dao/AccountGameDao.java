package com.kong.center.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.kong.center.beans.AccountGameBean;

public class AccountGameDao {
	/**
	 * 获取session
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = ConnectionFactory
				.getSessionFactory();
		return sqlSessionFactory.openSession();
	}

	/****
	 * 增加账号游戏对照信息
	 * 
	 * @param accountGameBean
	 */
	public void addAccountGame(AccountGameBean accountGameBean,
			SqlSession sqlSession) {
		IAccountGame iAccountGame = sqlSession.getMapper(IAccountGame.class);
		iAccountGame.addAccountGame(accountGameBean);
	}

	public void addAccountGame(AccountGameBean accountGameBean) {
		try (SqlSession sqlSession = getSession()) {
			IAccountGame iAccountGame = sqlSession
					.getMapper(IAccountGame.class);
			iAccountGame.addAccountGame(accountGameBean);
			sqlSession.commit(true);
		}
	}

	/***
	 * 获取账户游戏绑定信息
	 * 
	 * @param game
	 * @param guid
	 * @return
	 */
	public AccountGameBean getAccountGameBean(String game, String guid) {
		try (SqlSession sqlSession = getSession()) {
			IAccountGame iAccountGame = sqlSession
					.getMapper(IAccountGame.class);
			return iAccountGame.getAccountGameBean(game, guid);
		}
	}

	/***
	 * 根据guid获取器所拥有的游戏
	 * 
	 * @param guid
	 * @return
	 */
	public List<String> getGameByGuid(String guid) {
		try (SqlSession sqlSession = getSession()) {
			IAccountGame iAccountGame = sqlSession
					.getMapper(IAccountGame.class);
			return iAccountGame.getGameByGuid(guid);
		}
	}
}
