package com.kong.center.beans;

/***
 * 登录注册返回数据的实体
 * 
 * @author kz
 *
 */
public class RebackBean {

	private String guid;

	private String token;

	private String type;

	private String cookies;

	private String timestamp;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCookies() {
		return cookies;
	}

	public void setCookies(String cookies) {
		this.cookies = cookies;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "RebackBean [guid=" + guid + ", token=" + token + ", type="
				+ type + ", cookies=" + cookies + ", timestamp=" + timestamp
				+ "]";
	}

}
