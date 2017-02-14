package com.kong.center.beans;

import java.util.Date;

public class RegisterLogBean {
	private int code;
	private String guid;
	private String registerUser;
	private String registerType;
	private String ip;
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
	private String channel;
	private String game;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getRegisterUser() {
		return registerUser;
	}

	public void setRegisterUser(String registerUser) {
		this.registerUser = registerUser;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	@Override
	public String toString() {
		return "RegisterLog [code=" + code + ", guid=" + guid
				+ ", registerUser=" + registerUser + ", registerType="
				+ registerType + ", ip=" + ip + ", deviceId=" + deviceId
				+ ", deviceType=" + deviceType + ", systemVersion="
				+ systemVersion + ", systemType=" + systemType + ", otherInfo="
				+ otherInfo + ", createTime=" + createTime + ", channel="
				+ channel + ", game=" + game + "]";
	}



	public RegisterLogBean(String guid, String registerUser,
			String registerType, String ip, String deviceId, String deviceType,
			String systemVersion, String systemType, String otherInfo,
			 String channel, String game,Date createTime) {
		super();
		this.guid = guid;
		this.registerUser = registerUser;
		this.registerType = registerType;
		this.ip = ip;
		this.deviceId = deviceId;
		this.deviceType = deviceType;
		this.systemVersion = systemVersion;
		this.systemType = systemType;
		this.otherInfo = otherInfo;
		this.createTime = createTime;
		this.channel = channel;
		this.game = game;
	}

	public RegisterLogBean() {
		super();
	}

}
