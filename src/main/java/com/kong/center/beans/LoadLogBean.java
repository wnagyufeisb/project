package com.kong.center.beans;

import java.util.Date;



/***
 * 登录日志表
 * 
 * @author kz
 *
 */
public class LoadLogBean {
	// 主键
	private int code;
	private String guid;// 用户中心主键
	private String loadUser;// 登录账户信息
	private String loadType;// 登录类型
	private String ip; // ip
	private String deviceId;// 设备号
	private String deviceType;// 机型
	private String systemVersion;// 系统版本
	private String systemType;// 系统类别
	private String roleId;// 角色id
	private String roleName;// 角色名
	private String server;// 区服
	private String otherInfo;// 其他信息
	private String channel;
	private Date createTime;// 建立时间
	private String game; // 游戏

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

	public String getLoadUser() {
		return loadUser;
	}

	public void setLoadUser(String loadUser) {
		this.loadUser = loadUser;
	}

	public String getLoadType() {
		return loadType;
	}

	public void setLoadType(String loadType) {
		this.loadType = loadType;
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

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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

	public LoadLogBean(String guid, String loadUser, String loadType,
			String ip, String deviceId, String deviceType,
			String systemVersion, String systemType, String roleId,
			String roleName, String server, String otherInfo, String channel,
			String game,Date createTime) {
		super();
		this.guid = guid;
		this.loadUser = loadUser;
		this.loadType = loadType;
		this.ip = ip;
		this.deviceId = deviceId;
		this.deviceType = deviceType;
		this.systemVersion = systemVersion;
		this.systemType = systemType;
		this.roleId = roleId;
		this.roleName = roleName;
		this.server = server;
		this.otherInfo = otherInfo;
		this.channel = channel;
		this.game = game;
		this.createTime=createTime;
	}

	public LoadLogBean() {
		super();
	}

	@Override
	public String toString() {
		return "LoadLogBean [code=" + code + ", guid=" + guid + ", loadUser="
				+ loadUser + ", loadType=" + loadType + ", ip=" + ip
				+ ", deviceId=" + deviceId + ", deviceType=" + deviceType
				+ ", systemVersion=" + systemVersion + ", systemType="
				+ systemType + ", roleId=" + roleId + ", roleName=" + roleName
				+ ", server=" + server + ", otherInfo=" + otherInfo
				+ ", channel=" + channel + ", createTime=" + createTime
				+ ", game=" + game + "]";
	}

}
