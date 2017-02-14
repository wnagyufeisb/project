package com.kong.center.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.kong.center.beans.UserInfoBean;

public class UserInfoDao {
	/**
	 * 获取session
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = ConnectionFactory
				.getSessionFactory();
		return sqlSessionFactory.openSession();
	}

	/***
	 * 根据账户id获取用户信息
	 * 
	 * @param userId
	 * @param sqlSession
	 * @return
	 */
	public UserInfoBean getUserInfoByGuid(String guid, SqlSession sqlSession) {
		IUserInfo iUserInfo = sqlSession.getMapper(IUserInfo.class);
		return iUserInfo.getUserInfoByGuid(guid);
	}

	public UserInfoBean getUserInfoByGuid(String guid) {
		try (SqlSession sqlSession = getSession()) {
			IUserInfo iUserInfo = sqlSession.getMapper(IUserInfo.class);
			return iUserInfo.getUserInfoByGuid(guid);
		}
	}

	/***
	 * 根据账户获取用户信息
	 * 
	 * @param account
	 * @return
	 */
	public UserInfoBean getUserInfoByAccount(String account, String game,
			String password) {
		try (SqlSession sqlSession = getSession()) {
			IUserInfo iUserInfo = sqlSession.getMapper(IUserInfo.class);
			return iUserInfo.getUserInfoByAccount(account, game, password);
		}
	}

	/***
	 * 增加用户信息
	 * 
	 * @param userInfoBean
	 */
	public void addUserInfo(UserInfoBean userInfoBean, SqlSession sqlSession) {
		IUserInfo iUserInfo = sqlSession.getMapper(IUserInfo.class);
		iUserInfo.addUserInfo(userInfoBean);
	}

	/***
	 * 根据设备号和游戏获取游戏信息
	 * 
	 * @param deviceId
	 * @param game
	 * @return
	 */
	public UserInfoBean getUserInfoByDevice(String deviceId, String game) {
		try (SqlSession sqlSession = getSession()) {
			IUserInfo iUserInfo = sqlSession.getMapper(IUserInfo.class);
			return iUserInfo.getUserInfoByDevice(deviceId, game);
		}
	}	
	public UserInfoBean getUserInfoByDevice(String deviceId, String game,SqlSession sqlSession) {
			IUserInfo iUserInfo = sqlSession.getMapper(IUserInfo.class);
			return iUserInfo.getUserInfoByDevice(deviceId, game);
	}

	/***
	 * 根据三方信息获取账户
	 * 
	 * @param thirdPartyAccount
	 * @param thirdPartyType
	 * @param game
	 * @return
	 */
	public UserInfoBean getUserInfoByThirdParty(String thirdPartyAccount,
			String thirdPartyType, String game) {
		try (SqlSession sqlSession = getSession()) {
			IUserInfo iUserInfo = sqlSession.getMapper(IUserInfo.class);
			return iUserInfo.getUserInfoByThirdParty(thirdPartyAccount,
					thirdPartyType, game);
		}
	}

	public UserInfoBean getUserInfoByThirdParty(String thirdPartyAccount,
			String thirdPartyType, String game, SqlSession sqlSession) {
		IUserInfo iUserInfo = sqlSession.getMapper(IUserInfo.class);
		return iUserInfo.getUserInfoByThirdParty(thirdPartyAccount,
				thirdPartyType, game);
	}

	/***
	 * 根据官方账号获取信息
	 * 
	 * @param account
	 * @param game
	 * @param sqlSession
	 * @return
	 */
	public UserInfoBean getOfficeUserInfo(String account, String game,
			SqlSession sqlSession) {
		IUserInfo iUserInfo = sqlSession.getMapper(IUserInfo.class);
		return iUserInfo.getOfficeUserInfo(account, game);
	}

	/***
	 * 根据三方账号获取账户
	 * 
	 * @param thirdPartyAccount
	 * @param thirdPartyType
	 * @param game
	 * @return
	 */
	public UserInfoBean getUserInfoBythirdPartyAccount(
			String thirdPartyAccount, String thirdPartyType,
			SqlSession sqlSession) {
		IUserInfo iUserInfo = sqlSession.getMapper(IUserInfo.class);
		return iUserInfo.getUserInfoBythirdPartyAccount(thirdPartyAccount,
				thirdPartyType);
	}
	/***
	 * 根据三方账号获取账户
	 * 
	 * @param thirdPartyAccount
	 * @param thirdPartyType
	 * @param game
	 * @return
	 */
	public UserInfoBean getUserInfoBythirdPartyAccount(
			String thirdPartyAccount, String thirdPartyType) {
		try (SqlSession sqlSession = getSession()) {
		IUserInfo iUserInfo = sqlSession.getMapper(IUserInfo.class);
		return iUserInfo.getUserInfoBythirdPartyAccount(thirdPartyAccount,
				thirdPartyType);
		}
	}


	/***
	 * 根据官方账号查询信息 email phone account
	 * 
	 * @param thirdPartyAccount
	 * @param thirdPartyType
	 * @param game
	 * @return
	 */
	public UserInfoBean getUserInfoByOffice(String officeAccount,
			SqlSession sqlSession) {
		IUserInfo iUserInfo = sqlSession.getMapper(IUserInfo.class);
		return iUserInfo.getUserInfoByOffice(officeAccount);

	}

	public UserInfoBean getUserInfoByOffice(String officeAccount) {
		try (SqlSession sqlSession = getSession()) {
			IUserInfo iUserInfo = sqlSession.getMapper(IUserInfo.class);
			return iUserInfo.getUserInfoByOffice(officeAccount);
		}

	}

	/***
	 * 根据设备号查询支付中心信息
	 * 
	 * @param thirdPartyAccount
	 * @param thirdPartyType
	 * @param game
	 * @return
	 */
	public UserInfoBean getUserInfoByDeviceId(String deviceId,
			SqlSession sqlSession) {
		IUserInfo iUserInfo = sqlSession.getMapper(IUserInfo.class);
		return iUserInfo.getUserInfoByDeviceId(deviceId);
	}
	
	public UserInfoBean getUserInfoByDeviceId(String deviceId) {
		try (SqlSession sqlSession = getSession()) {
		IUserInfo iUserInfo = sqlSession.getMapper(IUserInfo.class);
		return iUserInfo.getUserInfoByDeviceId(deviceId);
		}
	}

	/***
	 * 修改密码
	 * 
	 * @param guid
	 * @param password
	 */
	public void setPassword(String guid, String password, SqlSession sqlSession) {
		IUserInfo iUserInfo = sqlSession.getMapper(IUserInfo.class);
		iUserInfo.setPassword(guid, password);

	}

	/***
	 * 更改用户信息
	 * 
	 * @param userInfoBean
	 */
	public void setUserInfo(UserInfoBean userInfoBean) {
		try (SqlSession sqlSession = getSession()) {
			IUserInfo iUserInfo = sqlSession.getMapper(IUserInfo.class);
			iUserInfo.setUserInfo(userInfoBean);
		}
	}

	public void setUserInfo(UserInfoBean userInfoBean, SqlSession sqlSession) {
		IUserInfo iUserInfo = sqlSession.getMapper(IUserInfo.class);
		iUserInfo.setUserInfo(userInfoBean);
	}

}
