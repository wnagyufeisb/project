package com.kong.center.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.mail.internet.MimeUtility;

import net.sf.json.JSONObject;
import noumena.payment.userverify.ChannelVerify;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import redis.clients.jedis.Jedis;

import com.kong.center.beans.AccountGameBean;
import com.kong.center.beans.ActivateLogBean;
import com.kong.center.beans.LoadLogBean;
import com.kong.center.beans.RebackBean;
import com.kong.center.beans.RegisterLogBean;
import com.kong.center.beans.ResetPasswordLogBean;
import com.kong.center.beans.ResultBean;
import com.kong.center.beans.UserBindingBean;
import com.kong.center.beans.UserInfoBean;
import com.kong.center.dao.AccountGameDao;
import com.kong.center.dao.ActivateLogDao;
import com.kong.center.dao.ConnectionFactory;
import com.kong.center.dao.LoadLogDao;
import com.kong.center.dao.RegisterLogDao;
import com.kong.center.dao.ResetPasswordLogDao;
import com.kong.center.dao.UserBindingDao;
import com.kong.center.dao.UserInfoDao;
import com.kong.center.service.util.DateUtil;
import com.kong.center.service.util.ErrorCode;
import com.kong.center.service.util.MD5;
import com.kong.center.service.util.MailSenderInfo;
import com.kong.center.service.util.RedisUtil;
import com.kong.center.service.util.SimpleMailSender;

/***
 * 用户信息的业务
 * 
 * @author kz
 *
 */
public class UserInfoService {
	private UserInfoDao userInfoDao = new UserInfoDao();
	private RegisterLogDao registerLogDao = new RegisterLogDao();
	private AccountGameDao accountGameDao = new AccountGameDao();
	private LoadLogDao loadLogDao = new LoadLogDao();
	private ResetPasswordLogDao resetPasswordLogDao = new ResetPasswordLogDao();
	private UserBindingDao userBindingDao = new UserBindingDao();
	private ActivateLogDao activateLogDao = new ActivateLogDao();
	private int maxTime = 3600 * 24 * 8;

	/**
	 * 获取session
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = ConnectionFactory
				.getSessionFactory();
		return sqlSessionFactory.openSession();
	}

	// private UserBindingDao userBindingDao =new UserBindingDao();
	/***
	 * 用户登录
	 * 
	 * @param map
	 * @return
	 */
	public ResultBean<RebackBean> userLoad(Map<String, String> map) {
		Jedis jedis = RedisUtil.getJedis();
		ResultBean<RebackBean> resultBean = new ResultBean<>();
		String game = map.get("game"); // 游戏
		String account = map.get("account"); // 账号
		String password = map.get("password"); // 密码
		String deviceId = map.get("deviceId"); // 设备id
		String deviceType = map.get("deviceType"); // 设备种类
		String systemVersion = map.get("systemVersion"); // 系统版本
		String systemType = map.get("systemType");// 系统类别
		String otherInfo = map.get("otherInfo"); // 其他信息
		String channel = map.get("channel"); // 渠道
		String cookie = map.get("cookie"); // cookies值
		String userType = map.get("userType"); // 登录类型
		String thirdPartyAccount = map.get("thirdPartyAccount"); // 第三方账号
		String thirdPartyType = map.get("thirdPartyType"); // 第三方账号类型
		String token = map.get("token"); // 第三方账号
		String roleId = map.get("roleId"); // 角色id
		String roleName = map.get("roleName"); // 角色名
		String server = map.get("server"); // 服务
		String loadInfo = map.get("loadInfo"); // 登录信息
		String ip = map.get("ip"); // 登录信息
	
		String guid = "";
		try {
			if (game == null || "".equals(game)) {
				jedis.close();
				return new ResultBean<>(false, "登录参数game不能为空",
						ErrorCode.loadGameNotNull, null);
			}
			// 1 自动登录
			if ("1".equals(userType)) {
				System.out.println("userLoad==========="
						+ DateUtil.getCurTimeStr() + "===============Auto load"
						+ cookie + "/game " + game);
				if (cookie == null || "".equals(cookie)) {
					jedis.close();
					return new ResultBean<>(false, "登录cookies不能为空",
							ErrorCode.loadCookiesNotNull, null);
				}
				resultBean = autoLoadVerify(cookie, game, guid, jedis);
			}
			// 2账号密码登录
			else if ("2".equals(userType)) {
				System.out.println("userLoad==========="
						+ DateUtil.getCurTimeStr()
						+ "===============account password load" + account
						+ "/" + password + "/game " + game);
				if (password == null || "".equals(password) || account == null
						|| "".equals(account)) {
					jedis.close();
					return new ResultBean<>(false, "登录账号密码不能为空",
							ErrorCode.loadAccountAndPasswordNotNull, null);
				}
				resultBean = accountVerify(password, account, game, guid, jedis);
			}
			// 3 游客1(有设备号)登录
			else if ("3".equals(userType)) {
				System.out.println("userLoad==========="
						+ DateUtil.getCurTimeStr()
						+ "===============guest1  deviceId  load" + deviceId
						+ "/game " + game);
				if (deviceId == null || "".equals(deviceId)) {
					jedis.close();
					return new ResultBean<>(false, "登录设备不能为空",
							ErrorCode.loaddeviceNotNull, null);
				}
				resultBean = gustOneVerify(deviceId, game, guid, "guest", jedis);

			}
			// 三方登录
			else if ("4".equals(userType)) {
				System.out.println("userLoad==========="
						+ DateUtil.getCurTimeStr()
						+ "===============third party  load" + token + "/"
						+ thirdPartyType + "/game " + game);
				if (token == null || "".equals(token) || thirdPartyType == null
						|| "".equals(thirdPartyType)) {
					jedis.close();
					return new ResultBean<>(false, "登录token和第三方类型不能为空",
							ErrorCode.loadTokenNotNull, null);
				}
				resultBean = gustThirdPartyVerify(token, thirdPartyAccount,
						game, guid, thirdPartyType, loadInfo, jedis);
			} else {
				jedis.close();
				return new ResultBean<>(false, "没有该登录的类型",
						ErrorCode.loadTypeNotNull, null);
			}
			// 加入登陆日志
			if (resultBean.getIsSuccess()) {
				LoadLogBean loadLogBean = new LoadLogBean(guid, loadInfo,
						userType, ip, deviceId, deviceType, systemVersion,
						systemType, roleId, roleName, server, otherInfo,
						channel, game, new Date());
				loadLogDao.addLoadInfo(loadLogBean);
			}
			jedis.close();
			return resultBean;
		} catch (Exception e) {
			System.out.println("autoLoadVerify===================>error" + e);
			e.printStackTrace();
			jedis.close();
			return new ResultBean<>(false, "登录系统发生错误",
					ErrorCode.loadSystemError, null);
		}

	}

	/***
	 * 根据guid获取用户资料
	 * 
	 * @param jedis
	 * @param guid
	 * @return
	 */
	private String getUserInfoByGuid(Jedis jedis, String guid) {
		String userInfo = jedis.get("userInfo" + guid);
		if (userInfo == null || "".equals(userInfo)) {
			UserInfoBean userInfoBean = userInfoDao.getUserInfoByGuid(guid);
			userInfo = JSONObject.fromObject(userInfoBean).toString();
			jedis.setex("userInfo" + guid, maxTime, userInfo);
		}
		return userInfo;
	}

	/***
	 * 第三方登录验证
	 * 
	 * @param token
	 * @param game
	 * @return
	 */
	private ResultBean<RebackBean> gustThirdPartyVerify(String token,
			String thirdPartyAccount, String game, String guid,
			String loadType, String loadInfo, Jedis jedis) {
		ResultBean<RebackBean> resultBean = new ResultBean<>();
		try {
			System.out.println("ThirdPartyVerify verifyString" + token);
			String jsomString = JSONObject.fromObject(
					token.replace("\\\"", "\"").replace(" ", "+")).toString();
			String id = ChannelVerify.verify2(jsomString, true);
			// String id="facebook_662038833976828";
			System.out.println("gustThirdPartyVerify id " + id);
			if (id == null || "".equals(id)) {
				return new ResultBean<>(false, "第三方登录验证未能通过",
						ErrorCode.loadThirdPartyVerifyFail, null);
			}
			loadInfo = id;
			thirdPartyAccount = id;
			UserInfoBean userInfoBean = getUserInfoByThirdParty(game, id,
					loadType, jedis);
			if (userInfoBean == null) {
				userInfoBean = userInfoDao.getUserInfoBythirdPartyAccount(
						thirdPartyAccount, loadType);
				if (userInfoBean == null) {
					return new ResultBean<>(false, "登录用户不存在",
							ErrorCode.loadUserNotExit, null);
				}
				// 生成游戏的标识
				String gameId = getGameId(userInfoBean.getGuid(), game);
				AccountGameBean accountGameBean = new AccountGameBean(
						userInfoBean.getGuid(), gameId, "登录自动生成", game,
						new Date());
				accountGameDao.addAccountGame(accountGameBean);
				jedis.setex("ThirdParty" + id + game, 3600 * 12, JSONObject
						.fromObject(userInfoBean).toString());
			}

			guid = userInfoBean.getGuid();
			resultBean = loadVerify(game, loadType, userInfoBean, jedis);
			return resultBean;
		} catch (Exception e) {
			System.out.println(loadType + "=========================>" + e);
			return new ResultBean<>(false, "第三方系统出现错误",
					ErrorCode.loadSystemError, null);
		}

	}

	/***
	 * 根据三方获取用户信息
	 * 
	 * @param game
	 * @param thirdUid
	 * @return
	 */
	private UserInfoBean getUserInfoByThirdParty(String game, String thirdUid,
			String loadType, Jedis jedis) {
		String userInfoJson = jedis.get("ThirdParty" + thirdUid + game);
		UserInfoBean userInfoBean = new UserInfoBean();
		// 获取用户信息
		if (userInfoJson != null && !"".equals(userInfoJson)) {
			userInfoBean = (UserInfoBean) JSONObject.toBean(
					JSONObject.fromObject(userInfoJson), UserInfoBean.class);
		} else {
			userInfoBean = userInfoDao.getUserInfoByThirdParty(thirdUid,
					loadType, game);
			if (userInfoBean != null) {
				jedis.setex("ThirdParty" + thirdUid + game, 3600 * 12,
						JSONObject.fromObject(userInfoBean).toString());
			}
		}
		return userInfoBean;

	}

	/***
	 * 游客1的验证
	 * 
	 * @param deviceId
	 * @param game
	 * @param guid
	 * @return
	 */
	private ResultBean<RebackBean> loadVerify(String game, String loadType,
			UserInfoBean userInfoBean, Jedis jedis) {
		try {
			if (userInfoBean == null) {
				return new ResultBean<>(false, "登录用户不存在",
						ErrorCode.loadUserNotExit, null);
			}
			// 查询出账号绑定的游戏信息
			AccountGameBean accountGameBean = getAccountGameBean(
					userInfoBean.getGuid(), game, jedis);
			// 生成返回信息
			RebackBean rebackBean = getRebackInfo(userInfoBean.getGuid(),
					accountGameBean.getGameId(), game,
					userInfoBean.getUserType(), userInfoBean, accountGameBean,
					jedis);
			return new ResultBean<>(true, "用户登陆成功", ErrorCode.loadSuccess,
					rebackBean);
		} catch (Exception e) {
			System.out.println(loadType + "=========================>" + e);
			return new ResultBean<>(false, "登录系统出现错误",
					ErrorCode.loadSystemError, null);
		}

	}

	/***
	 * 游客1的验证
	 * 
	 * @param deviceId
	 * @param game
	 * @param guid
	 * @return
	 */
	private ResultBean<RebackBean> gustOneVerify(String deviceId, String game,
			String guid, String loadType, Jedis jedis) {
		try {
			UserInfoBean userInfoBean = getUserInfoByDevice(deviceId, game,
					jedis);
			if (userInfoBean == null) {
				userInfoBean = userInfoDao.getUserInfoByDeviceId(deviceId);
				if (userInfoBean == null) {
					return new ResultBean<>(false, "登录用户不存在",
							ErrorCode.loadUserNotExit, null);
				}
				// 生成游戏的标识
				String gameId = getGameId(userInfoBean.getGuid(), game);
				AccountGameBean accountGameBean = new AccountGameBean(
						userInfoBean.getGuid(), gameId, "登录自动生成", game,
						new Date());
				accountGameDao.addAccountGame(accountGameBean);
				jedis.setex("DeviceLoad" + deviceId + game, 3600 * 12,
						JSONObject.fromObject(userInfoBean).toString());
			}
			guid = userInfoBean.getGuid();
			return loadVerify(game, loadType, userInfoBean, jedis);
		} catch (Exception e) {
			System.out.println("gustOneVerify=========================>" + e);
			return new ResultBean<>(false, "游客系统出现错误",
					ErrorCode.loadSystemError, null);
		}

	}

	/***
	 * 根据用户机型查询游客
	 * 
	 * @param deviceId
	 * @param deviceType
	 * @param systemVersion
	 * @param systemType
	 * @param game
	 * @return
	 */
	private UserInfoBean getUserInfoByDevice(String deviceId, String game,
			Jedis jedis) {
		String userInfoJson = jedis.get("DeviceLoad" + deviceId + game);
		UserInfoBean userInfoBean = new UserInfoBean();
		// 获取用户信息
		if (userInfoJson != null && !"".equals(userInfoJson)) {
			userInfoBean = (UserInfoBean) JSONObject.toBean(
					JSONObject.fromObject(userInfoJson), UserInfoBean.class);
		} else {
			userInfoBean = userInfoDao.getUserInfoByDevice(deviceId, game);
			if (userInfoBean != null) {
				jedis.setex("DeviceLoad" + deviceId + game, 3600 * 12,
						JSONObject.fromObject(userInfoBean).toString());
			}
		}
		return userInfoBean;
	}

	/***
	 * 账号验证
	 * 
	 * @return
	 * @throws Exception
	 */
	private ResultBean<RebackBean> accountVerify(String password,
			String account, String game, String guid, Jedis jedis) {
		System.out.println("accountVerify=================>+param" + password
				+ " " + account + " " + game);
		UserInfoBean userInfoBean = new UserInfoBean();
		try {
			String userInfoJson = jedis.get("accountLoad" + account);
			// 获取用户信息
			if (userInfoJson != null && !"".equals(userInfoJson)) {
				userInfoBean = (UserInfoBean) JSONObject
						.toBean(JSONObject.fromObject(userInfoJson),
								UserInfoBean.class);
			} else {
				userInfoBean = userInfoDao.getUserInfoByAccount(account, game,
						password);
				if (userInfoBean != null) {
					jedis.setex("accountLoad" + account, 3600 * 12, JSONObject
							.fromObject(userInfoBean).toString());
				}
			}
			// 验证账号密码
			if (userInfoBean == null) {
				return new ResultBean<>(false, "登录账号密码错误",
						ErrorCode.loadAccountPasswordError, null);
			}
			if (!password.equals(userInfoBean.getPassword())) {
				return new ResultBean<>(false, "密码不正确",
						ErrorCode.loadAccountPasswordError, null);
			}
			// 查询出账号绑定的游戏信息
			AccountGameBean accountGameBean = getAccountGameBean(
					userInfoBean.getGuid(), game, jedis);
			if (accountGameBean == null) {
				// 生成游戏的标识
				String gameId = getGameId(userInfoBean.getGuid(), game);
				accountGameBean = new AccountGameBean(userInfoBean.getGuid(),
						gameId, "登录自动生成", game, new Date());
				accountGameDao.addAccountGame(accountGameBean);
				jedis.setex("AccountGame" + guid + game, 3600 * 12, JSONObject
						.fromObject(accountGameBean).toString());
			}
			guid = userInfoBean.getGuid();
			// 生成返回信息
			RebackBean rebackBean = getRebackInfo(userInfoBean.getGuid(),
					accountGameBean.getGameId(), game,
					userInfoBean.getUserType(), userInfoBean, accountGameBean,
					jedis);
			return new ResultBean<>(true, "服务端登录成功", ErrorCode.loadSuccess,
					rebackBean);
		} catch (Exception e) {
			System.out
					.println("accountVerify=======================>error" + e);
			return new ResultBean<>(false, "系统出现错误", ErrorCode.loadSystemError,
					null);
		}

	}

	/***
	 * 获取账号游戏绑定信息
	 * 
	 * @param guid
	 * @param game
	 * @return
	 */
	private AccountGameBean getAccountGameBean(String guid, String game,
			Jedis jedis) {
		AccountGameBean accountGameBean = new AccountGameBean();
		String accountGameJson = jedis.get("AccountGame" + guid + game);
		if (accountGameJson != null && !"".equals(accountGameJson)) {
			accountGameBean = (AccountGameBean) JSONObject.toBean(
					JSONObject.fromObject(accountGameJson),
					AccountGameBean.class);
		} else {
			accountGameBean = accountGameDao.getAccountGameBean(game, guid);
			if (accountGameBean != null) {
				if (accountGameBean != null) {
					jedis.setex("AccountGame" + guid + game, 3600 * 12,
							JSONObject.fromObject(accountGameBean).toString());
				}
			}
		}
		return accountGameBean;

	}

	/***
	 * 自动登录验证
	 * 
	 * @return
	 * @throws Exception
	 */
	private ResultBean<RebackBean> autoLoadVerify(String cookies, String game,
			String guid, Jedis jedis) throws Exception {
		try {
			String temp[] = cookies.split(":");
			String uuid = temp[1];
			String signGuid = MD5.md5(cookies + uuid + game, "UTF-8");
			// 根据cookies获取guid

			guid = jedis.get("guid" + signGuid);
			if (guid == null) {
				return new ResultBean<>(false, "cookie信息不正确",
						ErrorCode.loadCookieError, null);
			}
			String userInfoJson = getUserInfoByGuid(jedis, guid);
			if (userInfoJson == null) {
				return new ResultBean<>(false, "cookie信息不正确",
						ErrorCode.loadCookieError, null);
			}
			System.out.println("autoLoadVerify userInfo==============="
					+ userInfoJson);
			// 用户信息
			UserInfoBean userInfoBean = (UserInfoBean) JSONObject.toBean(
					JSONObject.fromObject(userInfoJson), UserInfoBean.class);
			String accountGameJson = getAccountGameBean(jedis, guid, game);
			System.out.println("autoLoadVerify accountGame==============="
					+ accountGameJson);
			// 用户游戏绑定信息
			AccountGameBean accountGameBean = (AccountGameBean) JSONObject
					.toBean(JSONObject.fromObject(accountGameJson),
							AccountGameBean.class);

			jedis.setex("guid" + signGuid, 1, "");
			// 生成返回信息
			RebackBean rebackBean = getRebackInfo(userInfoBean.getGuid(),
					accountGameBean.getGameId(), game,
					userInfoBean.getUserType(), userInfoBean, accountGameBean,
					jedis);

			System.out.println("autoLoadVerify===================>"
					+ rebackBean.toString());
			return new ResultBean<>(true, "服务端登录成功", ErrorCode.loadSuccess,
					rebackBean);
		} catch (Exception e) {
			System.out.println("autoLoadVerify===================>error" + e);
			e.printStackTrace();
			return new ResultBean<>(false, "系统发生错误", ErrorCode.loadSystemError,
					null);
		}

	}

	private String getAccountGameBean(Jedis jedis, String guid, String game) {
		String accountString = jedis.get("accountGameBean" + guid + game);
		if (accountString == null || "".equals(accountString)) {
			AccountGameBean accountGameBean = accountGameDao
					.getAccountGameBean(game, guid);
			accountString = JSONObject.fromObject(accountGameBean).toString();
			jedis.setex("accountGameBean" + guid + game, maxTime, accountString);
		}
		return accountString;
	}

	/***
	 * 用户注册
	 * 
	 * @param map
	 * @return
	 */
	public ResultBean<RebackBean> userRegister(Map<String, String> map) {
		ResultBean<RebackBean> resultBean = new ResultBean<>();
		Jedis jedis = RedisUtil.getJedis();
		String account = map.get("account"); // 账号
		String password = map.get("password");// 密码
		String phone = map.get("phone"); // 电话
		String email = map.get("email"); // 邮箱
		String thirdPartyAccount = map.get("thirdPartyAccount"); // 第三方账号
		String userType = map.get("userType");// 用户类型
		String deviceId = map.get("deviceId"); // 设备id
		String deviceType = map.get("deviceType"); // 设备种类
		String systemVersion = map.get("systemVersion"); // 系统版本
		String systemType = map.get("systemType");// 系统类别
		String otherInfo = map.get("otherInfo"); // 其他信息
		String channel = map.get("channel"); // 渠道
		String game = map.get("game"); // 游戏
		String registerType = map.get("registerType");// 注册类型 thirdParty
														// office guest
		String ip = map.get("ip");// 注册ip
		String token = map.get("token");// 注册ip
		String registerAccount = map.get("registerAccount");// 注册账号
		// 判断账户类型
		if (!"thirdParty".equals(registerType)
				&& !"office".equals(registerType)
				&& !"guest".equals(registerType)) {
			jedis.close();
			return new ResultBean<>(false, "注册类型不正确",
					ErrorCode.registerTypeError, null);
		}
		boolean isAddUserInfo = true;
		// 生成的标识
		String guid = getGuid();
		// 生成游戏的标识
		String gameId = getGameId(guid, game);
		UserInfoBean userInfoBean = new UserInfoBean(guid, account, password,
				phone, email, "", thirdPartyAccount, userType, "", deviceId,
				deviceType, systemVersion, systemType, otherInfo, new Date());
		// 生成注册日志信息
		RegisterLogBean registerLogBean = new RegisterLogBean(guid,
				registerAccount, userType, ip, deviceId, deviceType,
				systemVersion, systemType, otherInfo + ":"
						+ userInfoBean.toString(), channel, game, new Date());
		AccountGameBean accountGameBean = new AccountGameBean(guid, gameId,
				otherInfo, game, new Date());
		System.out
				.println("userRegister===============================>userRegister param"
						+ userInfoBean.toString());
		try (SqlSession sqlSession = getSession()) {
			// 第三方的注册验证
			if ("thirdParty".equals(registerType)) {
				String jsomString = JSONObject.fromObject(
						token.replace("\\\"", "\"").replace(" ", "+"))
						.toString();
				// 设定参数
				thirdPartyAccount = ChannelVerify.verify2(jsomString, true);
				userInfoBean.setThirdPartyAccount(thirdPartyAccount);
				registerLogBean.setRegisterUser(thirdPartyAccount);
				if (thirdPartyAccount == null || "".equals(thirdPartyAccount)) {
					jedis.close();
					return new ResultBean<>(false, "注册第三方验证失败",
							ErrorCode.registerThirdPartyVerifyFail, null);
				}
				// 取出已有的用户中心信息
				UserInfoBean thirdPartyAccountUserInfo = userInfoDao
						.getUserInfoBythirdPartyAccount(thirdPartyAccount,
								userType, sqlSession);
				if (thirdPartyAccountUserInfo != null) {
					registerLogBean
							.setGuid(thirdPartyAccountUserInfo.getGuid());
					userInfoBean = thirdPartyAccountUserInfo;
					isAddUserInfo = false;
				}
			}
			// 向数据库中添加信息
			try {
				// 三方账号是否存在
				if ("thirdParty".equals(registerType)) {
					userInfoBean.setAccount("");
					userInfoBean.setPassword("");
					userInfoBean.setEmail("");
					UserInfoBean uInfoBean = userInfoDao
							.getUserInfoByThirdParty(thirdPartyAccount,
									userType, game, sqlSession);
					if (uInfoBean != null) {
						jedis.close();
						return new ResultBean<>(false, "该三方账号已经注册过",
								ErrorCode.registerThirdPartyIsExit, null);
					}

				} else if ("office".equals(registerType)) {
					userInfoBean.setThirdPartyAccount("");
					// 注册账号长度错误
					if (registerAccount.length() < 6
							|| registerAccount.length() > 30) {
						jedis.close();
						return new ResultBean<>(false, "注册账号长度不正确",
								ErrorCode.registerAccountLengthError, null);
					}
					// 账户特殊字符
					if (!isHaveSpecalSign(registerAccount)) {
						jedis.close();
						return new ResultBean<>(false, "注册账号不能有特殊字符",
								ErrorCode.registerSpecalSignError, null);
					}
					UserInfoBean uInfoBean = userInfoDao.getOfficeUserInfo(
							account, game, sqlSession);
					if (uInfoBean != null) {
						jedis.close();
						return new ResultBean<>(false, "该邮箱，手机，账号已经注册过",
								ErrorCode.registerOfficeIsExit, null);
					}
					if (email != null && !"".equals(email)) {
						UserInfoBean userInfoBeanEmail = userInfoDao
								.getUserInfoByOffice(email);
						if (userInfoBeanEmail != null) {
							jedis.close();
							return new ResultBean<>(false, "该邮箱，手机，账号已经注册过",
									ErrorCode.registerOfficeIsExit, null);
						}
					}
					UserInfoBean officeUserInfoBean = userInfoDao
							.getUserInfoByOffice(registerAccount, sqlSession);
					if (officeUserInfoBean != null) {
						registerLogBean.setGuid(officeUserInfoBean.getGuid());
						userInfoBean = officeUserInfoBean;
						isAddUserInfo = false;
					}
				} else if ("guest".equals(registerType)) {
					userInfoBean.setAccount("");
					userInfoBean.setPassword("");
					userInfoBean.setEmail("");
					userInfoBean.setThirdPartyAccount("");
					/*
					 * UserInfoBean guestUserInfoBean = userInfoDao
					 * .getUserInfoByDevice(deviceId, gameId, sqlSession); if
					 * (guestUserInfoBean != null) { jedis.close(); return new
					 * ResultBean<>(false, "您已绑定过账号，不能以游客身份登录",
					 * ErrorCode.registerGuestExit, null); }
					 */
					UserInfoBean guestUserInfoBean = userInfoDao
							.getUserInfoByDevice(deviceId, gameId, sqlSession);
					if (guestUserInfoBean != null) {
						registerLogBean.setGuid(guestUserInfoBean.getGuid());
						userInfoBean = guestUserInfoBean;
						isAddUserInfo = false;
					}

				}
				// 注册信息
				resultBean = registerInfo(userInfoBean, accountGameBean,
						registerLogBean, isAddUserInfo, sqlSession);

				RebackBean rebackBean = new RebackBean();
				// 生成返回的相应参数
				if (resultBean.getIsSuccess()) {
					rebackBean = getRebackInfo(guid, gameId, game, userType,
							userInfoBean, accountGameBean, jedis);
					System.out.println("userRegister============>rebackBean"
							+ rebackBean.toString());
					resultBean.setData(rebackBean);
				}
				String userInfoBeanJson = JSONObject.fromObject(userInfoBean)
						.toString();
				// redis存储用户信息

				jedis.setex("userInfo" + guid, maxTime, userInfoBeanJson);
				// redis存储账号游戏信息
				jedis.setex("accountGameBean" + guid + game, maxTime,
						JSONObject.fromObject(accountGameBean).toString());
				jedis.close();
				sqlSession.commit(true);
				return new ResultBean<>(true, "注册成功",
						ErrorCode.registerSuccess, rebackBean);
			} catch (Exception e) {
				e.printStackTrace();
				sqlSession.rollback();
				jedis.close();
				return new ResultBean<>(false, "注册系统出现错误",
						ErrorCode.registerSystemError, null);
			}
		}

	}

	private boolean isHaveSpecalSign(String sign) {
		boolean is = false;
		if (sign != null & !"".equals(sign)) {
			for (int i = 0; i < sign.length(); i++) {
				char a = sign.charAt(i);
				if (a >= 48 && a <= 57) {
					is = true;
				} else if ((a >= 65 && a <= 90) || (a >= 97 && a <= 122)) {
					is = true;
				} else {
					return false;
				}
			}
		}
		return is;

	}

	/***
	 * 得到返回信息,cookies,token,加墨的guid
	 * 
	 * @return
	 * @throws Exception
	 */

	private RebackBean getRebackInfo(String guid, String uid, String game,
			String type, UserInfoBean userInfoBean,
			AccountGameBean accountGameBean, Jedis jedis) throws Exception {
		RebackBean rebackBean = new RebackBean();
		UUID uuid = UUID.randomUUID();
		String uuidString = uuid.toString();
		// 生成cookies
		String perCookie = accountGameBean.toString();
		String cookies = MD5.md5(perCookie, "UTF-8");
		cookies = cookies + ":" + uuidString;
		rebackBean.setCookies(cookies);
		// 加密的guid
		String signGuid = getSignGuid(cookies, uuidString, game);
		rebackBean.setGuid(signGuid);
		jedis.setex("guid" + signGuid, maxTime, guid);
		// 生成token
		String token = getAccountToken(userInfoBean, accountGameBean);
		jedis.setex(token, maxTime, uid);
		rebackBean.setToken(token);
		// 账号类型
		rebackBean.setType(type);
		return rebackBean;
	}

	/***
	 * 根据cookies加密一个guid主键
	 * 
	 * @param cookies
	 * @param uuid
	 * @param game
	 * @return
	 * @throws Exception
	 */
	private String getSignGuid(String cookies, String uuid, String game)
			throws Exception {
		return MD5.md5(cookies + uuid + game, "UTF-8");
	}

	/***
	 * 得到账号的token
	 * 
	 * @param userInfoBean
	 * @param accountGameBean
	 * @return
	 * @throws Exception
	 */
	private String getAccountToken(UserInfoBean userInfoBean,
			AccountGameBean accountGameBean) throws Exception {
		String pretoken = userInfoBean.toString() + accountGameBean.toString()
				+ DateUtil.getCurTimeStr();
		return MD5.md5(pretoken, "UTF-8");
	}

	/***
	 * 账号信息注册
	 * 
	 * @param userInfoBean
	 * @return
	 */
	private ResultBean<RebackBean> registerInfo(UserInfoBean userInfoBean,
			AccountGameBean accountGameBean, RegisterLogBean registerLogBean,
			Boolean isAddUserInfo, SqlSession sqlSession) {
		// 增加注册信息
		if (isAddUserInfo) {
			userInfoDao.addUserInfo(userInfoBean, sqlSession);
		}
		// 增加注册日志
		registerLogDao.addRegisterLog(registerLogBean, sqlSession);
		// 增加游戏账号
		accountGameDao.addAccountGame(accountGameBean, sqlSession);
		return new ResultBean<>(true, "注册成功", ErrorCode.registerSuccess, null);
	}

	/***
	 * 根据token
	 * 
	 * @param map
	 * @return
	 */
	public ResultBean<String> verifyTokenGetUid(Map<String, String> map) {
		Jedis jedis = RedisUtil.getJedis();
		String token = map.get("token");
		String uid = jedis.get(token);
		System.out.println("======================" + uid);
		if (uid == null || "".equals(token)) {
			jedis.close();
			return new ResultBean<>(false, "服务器验证不正确,verify error", "1", uid);
		}
		jedis.setex(token, 600, uid);
		jedis.close();
		return new ResultBean<>(true, "success", "1", uid);

	}

	/***
	 * 用户激活信息
	 * 
	 * @param map
	 * @return
	 */
	public ResultBean<String> userActivate(Map<String, String> map) {
		Jedis jedis = RedisUtil.getJedis();
		try {
			String cookie = map.get("cookie");
			String game = map.get("game");
			String ip = map.get("ip");
			String deviceId = map.get("deviceId");
			String deviceType = map.get("deviceType");
			String systemVersion = map.get("systemVersion");
			String systemType = map.get("systemType");
			String channel = map.get("channel");
			String otherInfo = map.get("otherInfo");
			String temp[] = cookie.split(":");
			String uuid = temp[1];
			String signGuid = MD5.md5(cookie + uuid + game, "UTF-8");
			// 根据cookies获取guid
			String guid = jedis.get("guid" + signGuid);
			String userInfoJson = getUserInfoByGuid(jedis, guid);
			System.out.println("userActivate userInfo==============="
					+ userInfoJson);
			ActivateLogBean activateLogBean = activateLogDao
					.getActivateLogByGame(game, guid);
			if (activateLogBean != null) {
				jedis.close();
				return new ResultBean<>(false, "菲第一次激活，激活失败", "1", null);
			}
			activateLogBean = new ActivateLogBean(guid, game, ip, deviceId,
					deviceType, systemVersion, systemType, channel, otherInfo,
					new Date());
			activateLogDao.addActivateLog(activateLogBean);
			jedis.close();
			return new ResultBean<>(true, "激活日志添加成功", "1", null);

		} catch (Exception e) {
			e.printStackTrace();
			jedis.close();
			return new ResultBean<>(false, "激活日志添加失败", "1", null);
		}
	}

	/***
	 * 修改密码
	 * 
	 * @param request
	 * @return
	 */
	public ResultBean<String> resetPassword(Map<String, String> map) {
		Jedis jedis = RedisUtil.getJedis();
		String password = map.get("password"); // 密码
		String rePassword = map.get("rePassword"); // 再次输入的密码
		String sign = map.get("sign"); // 验证返回的代码
		String deviceId = map.get("deviceId"); // 设备id
		String deviceType = map.get("deviceType"); // 设备类型
		String systemVersion = map.get("systemVersion"); // 系统版本
		String systemType = map.get("systemType"); // 系统类型
		String channel = map.get("channel"); // 渠道
		String otherInfo = map.get("otherInfo");
		String ip = map.get("ip");
		if (!password.equals(rePassword)) {
			jedis.close();
			return new ResultBean<>(false, "两次输入的密码不一致",
					ErrorCode.resetPasswordPasseordDifferent, null);
		}
		String info = jedis.get(sign);
		if (info == null || "".equals(info)) {
			jedis.close();
			return new ResultBean<>(false, "验证码超时，请重新获取验证码",
					ErrorCode.resetPasswordVerifyCodeOverTime, null);
		}
		String infos[] = info.split(":");
		ResetPasswordLogBean resetPasswordLogBean = new ResetPasswordLogBean(
				infos[0], infos[1], password, ip, deviceId, deviceType,
				systemVersion, systemType, channel, otherInfo, new Date());
		try (SqlSession sqlSession = getSession()) {
			try {
				userInfoDao.setPassword(infos[0], password, sqlSession);
				resetPasswordLogDao.addResetPasswordLog(resetPasswordLogBean,
						sqlSession);
				sqlSession.commit(true);
				jedis.setex(sign, 1, "");
				// 修改用户信息
				UserInfoBean userInfoBean = getUserInfoBeanByGuid(infos[0],
						jedis);
				userInfoBean.setPassword(password);
				setAccountLoadInfo(userInfoBean, jedis);
				jedis.close();
				return new ResultBean<>(true, "修改密码成功",
						ErrorCode.resetPasswordSuccess, null);
			} catch (Exception e) {
				e.printStackTrace();
				sqlSession.rollback();
				jedis.close();
				return new ResultBean<>(false, "修改密码失败",
						ErrorCode.resetPasswordFail, null);
			}
		}

	}

	/***
	 * 修改密码
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ResultBean<String> resetPasswordByAccount(Map<String, String> map)
			throws Exception {
		Jedis jedis = RedisUtil.getJedis();
		String oldPassword = map.get("oldPassword"); // 密码
		String password = map.get("password"); // 密码
		String game = map.get("game");
		String rePassword = map.get("rePassword"); // 再次输入的密码
		String cookies = map.get("cookie"); // 验证返回的代码
		String deviceId = map.get("deviceId"); // 设备id
		String deviceType = map.get("deviceType"); // 设备类型
		String systemVersion = map.get("systemVersion"); // 系统版本
		String systemType = map.get("systemType"); // 系统类型
		String channel = map.get("channel"); // 渠道
		String otherInfo = map.get("otherInfo");
		String ip = map.get("ip");
		if (!password.equals(rePassword)) {
			jedis.close();
			return new ResultBean<>(false, "两次输入的密码不一致",
					ErrorCode.resetPasswordPasseordDifferent, null);
		}
		String temp[] = cookies.split(":");
		String uuid = temp[1];
		String signGuid = MD5.md5(cookies + uuid + game, "UTF-8");
		// 根据cookies获取guid
		String guid = jedis.get("guid" + signGuid);
		String userInfoJson = getUserInfoByGuid(jedis, guid);
		System.out.println("autoLoadVerify userInfo==============="
				+ userInfoJson);
		if (userInfoJson == null || "".equals(userInfoJson)) {
			return new ResultBean<>(false, "登录状态异常",
					ErrorCode.resetPasswordLoadError, null);
		}
		UserInfoBean userInfoBean = (UserInfoBean) JSONObject.toBean(
				JSONObject.fromObject(userInfoJson), UserInfoBean.class);
		if (!oldPassword.equals(userInfoBean.getPassword())) {
			return new ResultBean<>(false, "密码输入错误",
					ErrorCode.resetPasswordError, null);
		}
		ResetPasswordLogBean resetPasswordLogBean = new ResetPasswordLogBean(
				guid, userInfoBean.getPassword(), password, ip, deviceId,
				deviceType, systemVersion, systemType, channel, otherInfo,
				new Date());
		try (SqlSession sqlSession = getSession()) {
			try {
				userInfoDao.setPassword(guid, password, sqlSession);
				resetPasswordLogDao.addResetPasswordLog(resetPasswordLogBean,
						sqlSession);
				sqlSession.commit(true);
				userInfoBean.setPassword(password);
				setAccountLoadInfo(userInfoBean, jedis);
				jedis.close();
				return new ResultBean<>(true, "修改密码成功",
						ErrorCode.resetPasswordSuccess, null);
			} catch (Exception e) {
				e.printStackTrace();
				sqlSession.rollback();
				jedis.close();
				return new ResultBean<>(false, "修改密码失败",
						ErrorCode.resetPasswordFail, null);
			}
		}

	}

	/***
	 * 找回密码验证验证码
	 * 
	 * @param map
	 * @return
	 */
	public ResultBean<String> validateVerifyCode(Map<String, String> map) {
		Jedis jedis = RedisUtil.getJedis();
		String type = map.get("type");
		String verifyCode = map.get("verifyCode");
		String officeAccount = map.get("officeAccount");
		try {
			// 判断找回摩玛的方式
			if ("email".equals(type)) {
				ResultBean<String> resultBean = verifyEmailCode(type,
						verifyCode, officeAccount, jedis);
				jedis.close();
				return resultBean;
			} else {
				jedis.close();
				return new ResultBean<>(false, "验证码验证类型不正确",
						ErrorCode.verifyCodeSuccess, null);
			}
		} catch (Exception e) {
			jedis.close();
			e.printStackTrace();
			return new ResultBean<>(false, "验证码系统发生错误",
					ErrorCode.verifyCodeSystemError, null);
		}
	}

	/***
	 * 找回密码验证邮箱验证码
	 * 
	 * @param type
	 * @param verifyCode
	 * @param officeAccount
	 * @param jedis
	 * @return
	 * @throws Exception
	 */
	private ResultBean<String> verifyEmailCode(String type, String verifyCode,
			String officeAccount, Jedis jedis) throws Exception {
		// 获取内部的验证码
		String verifyCodeIn = jedis.get(officeAccount);
		if (verifyCodeIn == null || "".equals(verifyCodeIn)) {
			return new ResultBean<>(false, "验证码验证失效",
					ErrorCode.verifyCodeOverTime, null);
		}
		if (!verifyCode.equals(verifyCodeIn)) {
			return new ResultBean<>(false, "验证码不正确", ErrorCode.verifyCodeError,
					null);
		}
		// 判断是否存在绑定账号
		UserInfoBean userInfoBean = userInfoDao
				.getUserInfoByOffice(officeAccount);
		if (userInfoBean == null) {
			return new ResultBean<>(false, "验证手机或邮箱不正确",
					ErrorCode.verifyCodePhoneEmailError, null);
		}
		if (userInfoBean.getAccount() == null
				|| "".equals(userInfoBean.getAccount())) {
			return new ResultBean<>(false, "验证用户未绑定账号信息",
					ErrorCode.verifyCodeAccountNotExit, null);
		}
		String preSign = type + verifyCode + officeAccount
				+ DateUtil.getCurTimeStr();
		String findPasswordSign = MD5.md5(preSign, "UTF-8");
		jedis.setex(findPasswordSign, 3600, userInfoBean.getGuid() + ":"
				+ userInfoBean.getPassword());
		return new ResultBean<>(true, "验证成功", ErrorCode.verifyCodeSuccess,
				findPasswordSign);
	}

	/***
	 * 发送邮件验证码
	 * 
	 * @param request
	 * @return
	 */
	public ResultBean<String> sendEmail(Map<String, String> map) {
		String email = map.get("email");
		Jedis jedis = RedisUtil.getJedis();
		try {
			String verifyCode = getVerifyCode();
			sendmail(email, "验证码", verifyCode);
			jedis.setex(email, 60 * 15, verifyCode);
			jedis.close();
			System.out.println("sendEmail==================" + verifyCode);
			return new ResultBean<>(true, "获取验证码成功",
					ErrorCode.sendEmailSuccess, null);
		} catch (Exception e) {
			e.printStackTrace();
			jedis.close();
			return new ResultBean<>(false, "获取验证码错误", ErrorCode.sendEmailError,
					null);
		}
	}

	/***
	 * 得到guid的方法
	 * 
	 * @return
	 */
	private String getGuid() {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddhhmmss");
		String guid = sdf1.format(new Date());
		for (int i = 0; i < 6; i++) {

			Random random = new Random();
			int randomNum = random.nextInt(10);
			guid = guid + randomNum;

		}
		return guid;

	}

	/**
	 * 生成gameid
	 * 
	 * @return
	 */
	private String getGameId(String guid, String game) {
		String gameId = game + guid;
		Random random = new Random();
		int randomNum = random.nextInt(10000);
		gameId = gameId + randomNum;
		return gameId;
	}

	/***
	 * 判断参数是否为空
	 * 
	 * @param data
	 *            空了返回false，非空返回true
	 * @return
	 */
	private boolean isEmptyString(String data) {
		if (data == null || "".equals(data.trim())) {
			return false;
		}
		return true;

	}

	public static void main(String arg[]) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddhhmmss");
		System.out.println(sdf1.format(new Date()));
	}

	/***
	 * 绑定功能的实现
	 * 
	 * @param map
	 * @return
	 */

	public ResultBean<String> binding(Map<String, String> map) {
		Jedis jedis = RedisUtil.getJedis();
		String token = map.get("token"); // 第三方token
		String thirdPartyType = map.get("thirdPartyType"); // 第三方类型
		String type = map.get("bindingType"); // 绑定类型
		String account = map.get("account"); // 账户
		String password = map.get("password"); // 密码
		String rePassword = map.get("rePassword");// 再次输入密码
		String email = map.get("email"); // 海外账户绑定类型
		String signGuid = map.get("signGuid"); // 海外账户绑定类型
		String deviceId = map.get("deviceId"); // 设备id
		String deviceType = map.get("deviceType"); // 设备类型
		String systemVersion = map.get("systemVersion"); // 系统版本
		String systemType = map.get("systemType"); // 系统类型
		String channel = map.get("channel"); // 渠道
		String otherInfo = map.get("otherInfo");
		String ip = map.get("ip");
		if (type == null) {
			jedis.close();
			return new ResultBean<>(false, "绑定的类型（bindingType）不能为空",
					ErrorCode.bindingTypeIsNotNull, null);
		}
		if (signGuid == null || "".equals(signGuid)) {
			jedis.close();
			return new ResultBean<>(false, "绑定没有有效的用户信息",
					ErrorCode.bindingUserNotEffective, null);
		}

		// 根据sign获取guid
		String guid = jedis.get("guid" + signGuid);
		if (guid == null || "".equals(guid)) {
			jedis.close();
			return new ResultBean<>(false, "绑定没有有效的用户信息",
					ErrorCode.bindingUserNotEffective, null);
		}
		UserInfoBean userInfoBean = getUserInfoBeanByGuid(guid, jedis);
		if (userInfoBean == null) {
			jedis.close();
			return new ResultBean<>(false, "绑定用户信息不存在",
					ErrorCode.bindingUserNotExit, null);
		}

		UserBindingBean userBindingBean = new UserBindingBean(guid, "",
				userInfoBean.toString(), "", ip, deviceId, deviceType,
				systemVersion, systemType, otherInfo, channel, new Date());
		// 账户类新
		if (type.equals("account")) {
			if (password == null || "".equals(password) || rePassword == null
					|| "".equals(rePassword)) {
				jedis.close();
				return new ResultBean<>(false, "绑定密码不能为空",
						ErrorCode.bindingPasswordIsNull, null);
			}
			if (!password.equals(rePassword)) {
				jedis.close();
				return new ResultBean<>(false, "两次密码输入不一致",
						ErrorCode.bindingdPasseordDifferent, null);
			}
			return bindingAccount(account, password, email, rePassword,
					userInfoBean, userBindingBean, jedis);
		} else if (type.equals("thirdParty")) {
			// 第三方绑定
			return bingdingThirdParty(token, thirdPartyType, userInfoBean,
					userBindingBean, jedis);
		}/*
		 * else if (type.equals("email")) { // 邮箱绑定
		 * 
		 * }
		 */else {
			jedis.close();
			return new ResultBean<>(false, "绑定类型不存在",
					ErrorCode.bindingUserNotExit, null);
		}
	}

	/***
	 * 解绑操作
	 * 
	 * @param map
	 * @return
	 */
	public ResultBean<String> unbinding(Map<String, String> map) {
		Jedis jedis = RedisUtil.getJedis();
		String signGuid = map.get("signGuid"); // 海外账户绑定类型
		String deviceId = map.get("deviceId"); // 设备id
		String deviceType = map.get("deviceType"); // 设备类型
		String systemVersion = map.get("systemVersion"); // 系统版本
		String systemType = map.get("systemType"); // 系统类型
		String channel = map.get("channel"); // 渠道
		String otherInfo = map.get("otherInfo");
		String type = map.get("type"); // 绑定类型
		if (type == null) {
			jedis.close();
			return new ResultBean<>(false, "绑定类型不能为空", "1", null);
		}
		if (signGuid == null || "".equals(signGuid)) {
			jedis.close();
			return new ResultBean<>(false, "没有有效的用户信息", "1", null);
		}

		// 根据sign获取guid
		String guid = jedis.get("guid" + signGuid);
		if (guid == null || "".equals(guid)) {
			jedis.close();
			return new ResultBean<>(false, "没有有效的用户信息", "1", null);
		}
		UserInfoBean userInfoBean = getUserInfoBeanByGuid(guid, jedis);
		if (userInfoBean == null) {
			jedis.close();
			return new ResultBean<>(false, "用户信息不存在", "1", null);
		}
		if (type.equals("office")) {
			return null;

		} else if (type.equals("thirdParty")) {
			return null;
		} else {
			jedis.close();
			return new ResultBean<>(false, "该类型不存在", "1", null);
		}

	}

	/****
	 * 第三方绑定
	 * 
	 * @param token
	 * @param thirdPartyType
	 * @param userInfoBean
	 * @param userBindingBean
	 * @param jedis
	 * @return
	 */
	private ResultBean<String> bingdingThirdParty(String token,
			String thirdPartyType, UserInfoBean userInfoBean,
			UserBindingBean userBindingBean, Jedis jedis) {
		// 第三方绑定
		if (userInfoBean.getThirdPartyAccount() != null
				&& !"".equals(userInfoBean.getThirdPartyAccount())) {
			jedis.close();
			return new ResultBean<>(false, "第三方账号已经绑定，不能重复绑定",
					ErrorCode.bindingThirtPartyExit, null);
		}

		System.out.println("ThirdPartyVerify verifyString" + token);
		String jsomString = JSONObject.fromObject(
				token.replace("\\\"", "\"").replace(" ", "+")).toString();
		String id = ChannelVerify.verify2(jsomString, true);
		System.out.println("ThirdPartyVerify id " + id);
		UserInfoBean userInfoBeanThirdParty = userInfoDao
				.getUserInfoBythirdPartyAccount(id, thirdPartyType);
		if (userInfoBeanThirdParty != null) {
			return new ResultBean<>(false, "您使用的第三方账号已经绑定过其他账户",
					ErrorCode.bindingThirdPartyOtherExit, null);
		}
		userInfoBean.setThirdPartyAccount(id);
		userInfoBean.setUserType(thirdPartyType);
		userBindingBean.setBindingInfo(userBindingBean.toString());
		userBindingBean.setOptionType("thirdPartyVerify binding");
		setAccountLoadInfo(userInfoBean, jedis);
		try (SqlSession sqlSession = getSession()) {
			boolean isSuccess = setUserInfo(userInfoBean, userBindingBean,
					sqlSession, jedis);
			if (isSuccess) {
				jedis.close();
				return new ResultBean<>(true, "绑定成功", ErrorCode.bindingSuccess,
						null);
			} else {
				jedis.close();
				return new ResultBean<>(false, "绑定失败", ErrorCode.bindingError,
						null);
			}
		}
	}

	/***
	 * 账户绑定
	 * 
	 * @param account
	 * @param password
	 * @param rePassword
	 * @param userInfoBean
	 * @param userBindingBean
	 * @param jedis
	 * @return
	 */
	private ResultBean<String> bindingAccount(String account, String password,
			String email, String rePassword, UserInfoBean userInfoBean,
			UserBindingBean userBindingBean, Jedis jedis) {
		if (!verifyPassword(password)) {
			return new ResultBean<>(false, "绑定账号不能有特殊字符",
					ErrorCode.bindingPasswordSpecalSignError, null);
		}
		// 绑定账号长度错误
		if (account.length() < 6 || account.length() > 30) {
			return new ResultBean<>(false, "绑定账号长度不正确",
					ErrorCode.bindingAccountLengthError, null);
		}
		// 账户特殊字符
		if (!isHaveSpecalSign(account)) {
			return new ResultBean<>(false, "绑定账号不能有特殊字符",
					ErrorCode.bindingSpecalSignError, null);
		}
		if (userInfoBean.getAccount() != null
				&& !"".equals(userInfoBean.getAccount())) {
			jedis.close();
			return new ResultBean<>(false, "账号已经绑定，不能重复绑定",
					ErrorCode.bindingAccountExit, null);
		}
		UserInfoBean userInfoBeanAccount = userInfoDao
				.getUserInfoByOffice(account);
		if (userInfoBeanAccount != null) {
			jedis.close();
			return new ResultBean<>(false, "绑定使用账号已经绑定过其他游戏账户",
					ErrorCode.bindingThirdAccountOtherExit, null);
		}
		if (email != null && !"".equals(email)) {
			UserInfoBean userInfoBeanEmail = userInfoDao
					.getUserInfoByOffice(email);
			if (userInfoBeanEmail != null) {
				jedis.close();
				return new ResultBean<>(false, "绑定使用邮箱已经绑定过其他游戏账户",
						ErrorCode.bindingThirdEmailOtherExit, null);
			}
		}
		userInfoBean.setEmail(email);
		userInfoBean.setAccount(account);
		userInfoBean.setPassword(rePassword);
		userInfoBean.setUserType("acccount");
		setAccountLoadInfo(userInfoBean, jedis);
		userBindingBean.setBindingInfo(userBindingBean.toString());
		userBindingBean.setOptionType("acccount binding");
		try (SqlSession sqlSession = getSession()) {
			boolean isSuccess = setUserInfo(userInfoBean, userBindingBean,
					sqlSession, jedis);
			if (isSuccess) {
				jedis.close();
				return new ResultBean<>(true, "绑定成功", ErrorCode.bindingSuccess,
						null);
			} else {
				jedis.close();
				return new ResultBean<>(false, "绑定失败", ErrorCode.bindingError,
						null);
			}
		}
	}

	/***
	 * 设置账号登录的redis值
	 * 
	 * @param userInfoBean
	 * @param jedis
	 */
	private void setAccountLoadInfo(UserInfoBean userInfoBean, Jedis jedis) {
		jedis.setex("accountLoad" + userInfoBean.getAccount(), 3600 * 12,
				JSONObject.fromObject(userInfoBean).toString());
		jedis.setex("accountLoad" + userInfoBean.getEmail(), 3600 * 12,
				JSONObject.fromObject(userInfoBean).toString());
		jedis.setex("accountLoad" + userInfoBean.getPhone(), 3600 * 12,
				JSONObject.fromObject(userInfoBean).toString());
		jedis.setex("guidUserInfo" + userInfoBean.getGuid(), maxTime,
				JSONObject.fromObject(userInfoBean).toString());
		jedis.setex("userInfo" + userInfoBean.getGuid(), maxTime, JSONObject
				.fromObject(userInfoBean).toString());
		List<String> listGame = accountGameDao.getGameByGuid(userInfoBean
				.getGuid());
		if (listGame != null && !listGame.isEmpty()) {
			for (int i = 0; i < listGame.size(); i++) {
				jedis.setex("ThirdParty" + userInfoBean.getThirdPartyAccount()
						+ listGame.get(i), 1, "");
				jedis.setex(
						"AccountGame" + userInfoBean.getGuid()
								+ listGame.get(i), 1, "");
				jedis.setex("DeviceLoad" + userInfoBean.getDeviceId()
						+ listGame.get(i), 1, "");
			}
		}
	}

	/***
	 * 更改用户信息
	 * 
	 * @param userInfoBean
	 * @param sqlSession
	 * @param jedis
	 */
	private boolean setUserInfo(UserInfoBean userInfoBean,
			UserBindingBean userBindingBean, SqlSession sqlSession, Jedis jedis) {
		try {
			userInfoDao.setUserInfo(userInfoBean, sqlSession);
			userBindingDao.addUserBindingInfo(userBindingBean, sqlSession);
			jedis.setex("guidUserInfo" + userInfoBean.getGuid(), maxTime,
					JSONObject.fromObject(userInfoBean).toString());
			sqlSession.commit(true);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();
			return false;
		}

	}

	/***
	 * 获取用户信息
	 * 
	 * @param guid
	 * @param jedis
	 * @return
	 */
	private UserInfoBean getUserInfoBeanByGuid(String guid, Jedis jedis) {
		String key = "guidUserInfo";
		String userInfoJson = jedis.get(key + guid);
		if (userInfoJson == null || "".equals(userInfoJson)) {
			UserInfoBean userInfoBean = userInfoDao.getUserInfoByGuid(guid);
			if (userInfoBean != null) {
				jedis.setex(key + guid, maxTime,
						JSONObject.fromObject(userInfoBean).toString());
			}
			return userInfoBean;
		} else {
			return (UserInfoBean) JSONObject.toBean(
					JSONObject.fromObject(userInfoJson), UserInfoBean.class);
		}

	}

	/***
	 * 验证密码格式
	 * 
	 * @param verifyCode
	 * @return
	 */

	private boolean verifyPassword(String password) {
		boolean is = false;
		if (password == null || password.length() < 6 || password.length() > 20) {
			return false;
		}
		for (int i = 0; i < password.length(); i++) {
			char a = password.charAt(i);
			if (a < 33 && a > 125) {
				return false;
			} else if (a == 34 || a == 39 || a == 92) {
				return false;
			} else {
				is = true;
			}
		}
		return is;
	}

	/***
	 * 得到6位验证码
	 * 
	 * @return
	 */
	private String getVerifyCode() {
		String verifyCode = "";
		for (int i = 0; i < 6; i++) {

			Random random = new Random();
			int randomNum = random.nextInt(10);
			verifyCode = verifyCode + randomNum;

		}
		return verifyCode;
	}

	/****
	 * 发送验证码
	 * 
	 * @param sendEmail
	 * @param title
	 * @param verifyCode
	 */
	@SuppressWarnings("static-access")
	private void sendmail(String sendEmail, String title, String verifyCode) {
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("42.120.219.29");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("service@kzwgame.com");
		mailInfo.setPassword("KongZhong.com");// 您的邮箱密码
		mailInfo.setFromAddress("service@kzwgame.com");
		String[] receivers = null;
		receivers = new String[] { sendEmail };
		// receivers = new String[]{"lvyao@kongzhong.com"};
		String[] ccs = receivers;
		mailInfo.setCcs(ccs);
		try {
			String cont = "<table border='1'><tr><th>验证码</th></tr>";

			cont += "<tr><td>" + verifyCode + "</td></tr>";
			cont += "</table><br/><br/>";
			cont += "<h1>祝您工作顺利！</h1>";
			mailInfo.setSubject(MimeUtility.encodeText(title, "utf-8", "Q"));
			mailInfo.setContent(cont);
			SimpleMailSender sms = new SimpleMailSender();
			sms.sendHtmlMail(mailInfo);
		} catch (Exception es) {
			es.printStackTrace();
		}
	}

}
