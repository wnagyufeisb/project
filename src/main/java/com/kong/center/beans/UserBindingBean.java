package com.kong.center.beans;

import java.util.Date;

/***
 * 用户绑定信息表
 * 
 * @author kz
 *
 */
public class UserBindingBean {

	private int code;
	// 用户中心主键
	private String guid;
	// 绑定信息
	private String bindingInfo;
	// 解绑前信息
	private String beforeBindingInfo;
	// 操作类型
	private String optionType;
	// ip
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

	public String getBindingInfo() {
		return bindingInfo;
	}

	public void setBindingInfo(String bindingInfo) {
		this.bindingInfo = bindingInfo;
	}

	public String getBeforeBindingInfo() {
		return beforeBindingInfo;
	}

	public void setBeforeBindingInfo(String beforeBindingInfo) {
		this.beforeBindingInfo = beforeBindingInfo;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Override
	public String toString() {
		return "UserBindingBean [code=" + code + ", guid=" + guid
				+ ", bindingInfo=" + bindingInfo + ", beforeBindingInfo="
				+ beforeBindingInfo + ", optionType=" + optionType + ", ip="
				+ ip + ", deviceId=" + deviceId + ", deviceType=" + deviceType
				+ ", systemVersion=" + systemVersion + ", systemType="
				+ systemType + ", otherInfo=" + otherInfo + ", createTime="
				+ createTime + ", channel=" + channel + "]";
	}

	public UserBindingBean(String guid, String bindingInfo,
			String beforeBindingInfo, String optionType, String ip,
			String deviceId, String deviceType, String systemVersion,
			String systemType, String otherInfo, String channel, Date createTime) {
		super();
		this.guid = guid;
		this.bindingInfo = bindingInfo;
		this.beforeBindingInfo = beforeBindingInfo;
		this.optionType = optionType;
		this.ip = ip;
		this.deviceId = deviceId;
		this.deviceType = deviceType;
		this.systemVersion = systemVersion;
		this.systemType = systemType;
		this.otherInfo = otherInfo;
		this.channel = channel;
		this.createTime = createTime;
	}

	public UserBindingBean() {
		super();
	}

}
