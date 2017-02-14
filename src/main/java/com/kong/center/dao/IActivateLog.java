package com.kong.center.dao;

import org.apache.ibatis.annotations.Param;

import com.kong.center.beans.ActivateLogBean;

/***
 * 激活日志的数据库接口
 * 
 * @author kz
 *
 */
public interface IActivateLog {
	/***
	 * 添加激活日志
	 * 
	 * @param activateLogBean
	 */
	public void addActivateLog(ActivateLogBean activateLogBean);
	/***
	 * 取出激活信息
	 * @param game
	 * @param guid
	 * @return
	 */
	public ActivateLogBean getActivateLogByGame(@Param("game") String game,@Param("guid") String guid);

}
