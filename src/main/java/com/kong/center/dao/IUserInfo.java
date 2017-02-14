package com.kong.center.dao;

import org.apache.ibatis.annotations.Param;

import com.kong.center.beans.UserInfoBean;

/***
 * 有关用户的信息的接口
 * 
 * @author kz
 *
 */
public interface IUserInfo {

	/***
	 * 根据账户id获取用户信息
	 * 
	 * @return
	 */
	public UserInfoBean getUserInfoByGuid(@Param("guid") String  guid);

	/***
	 * 根据账户密码检验用户信息
	 * 
	 * @param account
	 * @return
	 */
	public UserInfoBean getUserInfoByAccount(@Param("account") String account,
			@Param("game") String game, @Param("password") String password);

	/***
	 * 根据官方账号获取账号信息 官方账号 email，account,phone
	 * 
	 * @param account
	 * @param game
	 * @return
	 */
	public UserInfoBean getOfficeUserInfo(@Param("account") String account,
			@Param("game") String game);

	/***
	 * 增加用户信息
	 * 
	 * @param userInfoBean
	 */
	public void addUserInfo(UserInfoBean userInfoBean);

	/***
	 * 根据设备号和游戏获取游戏信息
	 * 
	 * @param deviceId
	 * @param game
	 * @return
	 */
	public UserInfoBean getUserInfoByDevice(@Param("deviceId") String deviceId,
			@Param("game") String game);

	/***
	 * 根据三方信息获取账户
	 * 
	 * @param thirdPartyAccount
	 * @param thirdPartyType
	 * @param game
	 * @return
	 */
	public UserInfoBean getUserInfoByThirdParty(
			@Param("thirdPartyAccount") String thirdPartyAccount,
			@Param("thirdPartyType") String thirdPartyType,
			@Param("game") String game);

	/***
	 * 根据三方账号获取账户
	 * 
	 * @param thirdPartyAccount
	 * @param thirdPartyType
	 * @param game
	 * @return
	 */
	public UserInfoBean getUserInfoBythirdPartyAccount(
			@Param("thirdPartyAccount") String thirdPartyAccount,
			@Param("thirdPartyType") String thirdPartyType);

	/***
	 * 根据官方账号查询信息 email phone account
	 * 
	 * @param thirdPartyAccount
	 * @param thirdPartyType
	 * @param game
	 * @return
	 */
	public UserInfoBean getUserInfoByOffice(
			@Param("officeAccount") String officeAccount);

	/***
	 * 根据设备号查询支付中心信息
	 * 
	 * @param thirdPartyAccount
	 * @param thirdPartyType
	 * @param game
	 * @return
	 */
	public UserInfoBean getUserInfoByDeviceId(@Param("deviceId") String deviceId);

	/***
	 * 修改密码
	 * 
	 * @param guid
	 * @param password
	 */
	public void setPassword(@Param("guid") String guid,
			@Param("password") String password);

	/***
	 * 更改用户信息
	 * 
	 * @param userInfoBean
	 */
	public void setUserInfo(UserInfoBean userInfoBean);
	
	public void setEmailEmpty(@Param("guid") String guid,String email);

}
