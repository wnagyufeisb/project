package com.kong.center.beans;

import java.util.Date;

public class ActivateLogBean {
	//主键
	private int code;
	//用户中心主键
	private String guid;
	//游戏
	private String game;
	//ip
	private String ip;
	//设备号
	private String deviceId;
	//机型
	private String deviceType;
	//系统版本
	private String systemVersion;
	//系统类别
	private String systemType;
	//渠道
	private String channel;
	//其他信息
	private String otherInfo;
	//建立时间
	private Date createTime;
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
	public String getGame() {
		return game;
	}
	public void setGame(String game) {
		this.game = game;
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
	public ActivateLogBean( String guid, String game, String ip,
			String deviceId, String deviceType, String systemVersion,
			String systemType, String channel, String otherInfo, Date createTime) {
		super();
		this.guid = guid;
		this.game = game;
		this.ip = ip;
		this.deviceId = deviceId;
		this.deviceType = deviceType;
		this.systemVersion = systemVersion;
		this.systemType = systemType;
		this.channel = channel;
		this.otherInfo = otherInfo;
		this.createTime = createTime;
	}
	public ActivateLogBean() {
		super();
	}
	@Override
	public String toString() {
		return "ActivateLogBean [code=" + code + ", guid=" + guid + ", game="
				+ game + ", ip=" + ip + ", deviceId=" + deviceId
				+ ", deviceType=" + deviceType + ", systemVersion="
				+ systemVersion + ", systemType=" + systemType + ", channel="
				+ channel + ", otherInfo=" + otherInfo + ", createTime="
				+ createTime + "]";
	}
	
	

}
