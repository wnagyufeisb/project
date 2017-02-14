package com.kong.center.service.util;

public class ErrorCode {
	
	//注册成功
	public static String registerSuccess="10";
	//注册类型不正确
	public static String registerTypeError="11";
	//注册第三方验证失败
	public static String registerThirdPartyVerifyFail="12";
	//该三方账号已经注册过
	public static String registerThirdPartyIsExit="13";
	//邮箱，手机，账号已经注册过
	public static String registerOfficeIsExit="14";
	//注册系统失败
	public static String registerSystemError="15";
	//注册账号长度不正确
	public static String registerAccountLengthError="16";
	//注册账号不能有特殊字符
	public static String registerSpecalSignError="17";
	//您已绑定过账号，不能以游客身份登录
	public static String registerGuestExit="18";
	
	
	
	//服务端登录成功
	public static String loadSuccess="20";
	//登录参数game不能为空
	public static String loadGameNotNull="21";
	//登录cookies不能为空
	public static String loadCookiesNotNull="22";
	//登录账号密码不能为空
	public static String loadAccountAndPasswordNotNull="23";
	//登录设备不能为空
	public static String loaddeviceNotNull="24";
	//登录token和第三方类型不能为空
	public static String loadTokenNotNull="25";
	//没有该登录的类型
	public static String loadTypeNotNull="26";
	//登录系统发生错误
	public static String loadSystemError="27";
	//自动登录cookie信息不正确
	public static String loadCookieError="28";
	//登录账号密码错误
	public static String loadAccountPasswordError="29";
	//登录用户不存在
	public static String loadUserNotExit="201";
	//第三方登录验证未能通过
	public static String loadThirdPartyVerifyFail="202";
	//登录游客已绑定
	public static String loadGuestBinding="203";
	
	
	
	
	//获取验证码成功
	public static String sendEmailSuccess="30";
	//获取验证码错误
	public static String sendEmailError="31";
	
	
	
	//获验证验证码成功
	public static String verifyCodeSuccess="40";
	//验证码验证类型不正确
	public static String verifyCodeTypeError="41";
	//验证码系统发生错误
	public static String verifyCodeSystemError="42";
	//验证码验证失效
	public static String verifyCodeOverTime="43";
	//验证码不正确
	public static String verifyCodeError="44";
	//验证手机或邮箱不正确
	public static String verifyCodePhoneEmailError="45";
	//验证用户未绑定账号信息
	public static String verifyCodeAccountNotExit="46";
	
	
	//修改密码成功
	public static String resetPasswordSuccess="50";
	//修改密码失败
	public static String resetPasswordFail="51";
	//两次输入的密码不一致
	public static String resetPasswordPasseordDifferent="52";
	//验证码超时，请重新获取验证码
	public static String resetPasswordVerifyCodeOverTime="53";
	//登录状态异常
	public static String resetPasswordLoadError="54";
	//密码输入错误
	public static String resetPasswordError="55";
	
	
	
	
	//绑定成功
	public static String bindingSuccess="60";
	//绑定的类型（bindingType）不能为空
	public static String bindingTypeIsNotNull="61";
	//绑定没有有效的用户信息
	public static String bindingUserNotEffective="62";
	//绑定用户不存在
	public static String bindingUserNotExit="63";
	//绑定密码不能为空
	public static String bindingPasswordIsNull="64";
	//两次密码输入不一致
	public static String bindingdPasseordDifferent="65";
	//绑定类型不存在
	public static String bindingTypeIsExit="66";
	//第三方账号已经绑定，不能重复绑定
	public static String bindingThirtPartyExit="67";
	//绑定失败
	public static String bindingError="68";
	//账号已经绑定，不能重复绑定
	public static String bindingAccountExit="69";
	//您使用的第三方账号已经绑定过其他账户
	public static String bindingThirdPartyOtherExit="610";
	//绑定使用账号已经绑定过其他游戏账户
	public static String bindingThirdAccountOtherExit="611";
	//绑定使用邮箱已经绑定过其他游戏账户
	public static String bindingThirdEmailOtherExit="612";
	//绑定账号长度不正确
	public static String bindingAccountLengthError="613";
	//绑定账号不能有特殊字符
	public static String bindingSpecalSignError="614";
	//绑定账号不能有特殊字符
	public static String bindingPasswordSpecalSignError="615";
	
	
}
