package com.kong.center.beans;

import java.util.Date;



public class AccountGameBean {
	// 主键
	private int code;
	// 支付中心id
	private String paycenterId;
	// 游戏id
	private String gameId;
	// 其他信息
	private String otherThing;
	// 建立时间
	private Date datetime;
	// 游戏
	private String game;

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getPaycenterId() {
		return paycenterId;
	}

	public void setPaycenterId(String paycenterId) {
		this.paycenterId = paycenterId;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getOtherThing() {
		return otherThing;
	}

	public void setOtherThing(String otherThing) {
		this.otherThing = otherThing;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	

	public AccountGameBean(String paycenterId, String gameId,
			String otherThing,  String game,Date datetime) {
		super();
		this.paycenterId = paycenterId;
		this.gameId = gameId;
		this.otherThing = otherThing;
		this.datetime = datetime;
		this.game = game;
	}

	public AccountGameBean() {
		super();
	}

	@Override
	public String toString() {
		return "AccountGameBean [code=" + code + ", paycenterId=" + paycenterId
				+ ", gameId=" + gameId + ", otherThing=" + otherThing
				+ ", datetime=" + datetime + ", game=" + game + "]";
	}

}
