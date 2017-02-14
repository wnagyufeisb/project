package com.kong.center.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kong.center.beans.AccountGameBean;

public interface IAccountGame {

	/****
	 * 增加账号游戏对照信息
	 * 
	 * @param accountGameBean
	 */
	public void addAccountGame(AccountGameBean accountGameBean);

	/***
	 * 根据游戏和用户中心主键获取账户游戏绑定信息
	 * 
	 * @param game
	 * @param guid
	 * @return
	 */
	public AccountGameBean getAccountGameBean(@Param("game") String game,
			@Param("guid") String guid);

	/***
	 * 根据guid获取器所拥有的游戏
	 * @param guid
	 * @return
	 */
	public List<String> getGameByGuid(@Param("guid") String guid);
}
