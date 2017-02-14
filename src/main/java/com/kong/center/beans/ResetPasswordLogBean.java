package com.kong.center.beans;

import java.util.Date;

public class ResetPasswordLogBean {
	private String code;
	private String guid;
	private String beforePassword;
	private String nowPassword;
	private String loadIp;
	private String deviceId;
	private String deviceType;
	private String systemVersion;
	private String systemType;
	private String channel;
	private String otherInfo;
	private Date createTime;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getBeforePassword() {
		return beforePassword;
	}

	public void setBeforePassword(String beforePassword) {
		this.beforePassword = beforePassword;
	}

	public String getNowPassword() {
		return nowPassword;
	}

	public void setNowPassword(String nowPassword) {
		this.nowPassword = nowPassword;
	}

	public String getLoadIp() {
		return loadIp;
	}

	public void setLoadIp(String loadIp) {
		this.loadIp = loadIp;
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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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

	public ResetPasswordLogBean(String guid, String beforePassword,
			String nowPassword, String loadIp, String deviceId,
			String deviceType, String systemVersion, String systemType,
			String channel, String otherInfo, Date createTime) {
		super();
		this.guid = guid;
		this.beforePassword = beforePassword;
		this.nowPassword = nowPassword;
		this.loadIp = loadIp;
		this.deviceId = deviceId;
		this.deviceType = deviceType;
		this.systemVersion = systemVersion;
		this.systemType = systemType;
		this.channel = channel;
		this.otherInfo = otherInfo;
		this.createTime = createTime;
	}

	public ResetPasswordLogBean() {
		super();
	}

	@Override
	public String toString() {
		return "ResetPasswordLogBean [code=" + code + ", guid=" + guid
				+ ", beforePassword=" + beforePassword + ", nowPassword="
				+ nowPassword + ", loadIp=" + loadIp + ", deviceId=" + deviceId
				+ ", deviceType=" + deviceType + ", systemVersion="
				+ systemVersion + ", systemType=" + systemType + ", channel="
				+ channel + ", otherInfo=" + otherInfo + ", createTime="
				+ createTime + "]";
	}

}
