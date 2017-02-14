package com.kong.center.beans;

import java.util.Date;

/***
 * 用户信息的实体
 * 
 * @author kz
 *
 */
public class UserInfoBean {
	// 用户中心主键
	private String guid;
	// 账户
	private String account;
	// 密码
	private String password;
	// 手机
	private String phone;
	// 邮箱
	private String email;
	// 邮箱是否绑定
	private String isBindingEmail;
	// 三方账号
	private String thirdPartyAccount;
	// 用户类型
	private String userType;
	// 用户状态
	private String userStatus;
	// 设备号
	private String deviceId;
	// 机型
	private String deviceType;
	// 系统版本
	private String systemVersion;
	// 系统类别
	private String systemType;
	// 其他信息
	private String otherInfo;
	// 建立时间
	private Date createTime;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsBindingEmail() {
		return isBindingEmail;
	}

	public void setIsBindingEmail(String isBindingEmail) {
		this.isBindingEmail = isBindingEmail;
	}

	public String getThirdPartyAccount() {
		return thirdPartyAccount;
	}

	public void setThirdPartyAccount(String thirdPartyAccount) {
		this.thirdPartyAccount = thirdPartyAccount;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "UserInfoBean [guid=" + guid + ", account=" + account
				+ ", password=" + password + ", phone=" + phone + ", email="
				+ email + ", isBindingEmail=" + isBindingEmail
				+ ", thirdPartyAccount=" + thirdPartyAccount + ", userType="
				+ userType + ", userStatus=" + userStatus + ", deviceId="
				+ deviceId + ", deviceType=" + deviceType + ", systemVersion="
				+ systemVersion + ", systemType=" + systemType + ", otherInfo="
				+ otherInfo + ", createTime=" + createTime + "]";
	}

	public UserInfoBean(String guid, String account, String password,
			String phone, String email, String isBindingEmail,
			String thirdPartyAccount, String userType, String userStatus,
			String deviceId, String deviceType, String systemVersion,
			String systemType, String otherInfo, Date createTime) {
		super();
		this.guid = guid;
		this.account = account;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.isBindingEmail = isBindingEmail;
		this.thirdPartyAccount = thirdPartyAccount;
		this.userType = userType;
		this.userStatus = userStatus;
		this.deviceId = deviceId;
		this.deviceType = deviceType;
		this.systemVersion = systemVersion;
		this.systemType = systemType;
		this.otherInfo = otherInfo;
		this.createTime = createTime;
	}

	public UserInfoBean() {
		super();
	}

}
