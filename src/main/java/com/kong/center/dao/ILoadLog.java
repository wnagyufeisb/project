package com.kong.center.dao;

import com.kong.center.beans.LoadLogBean;

public interface ILoadLog {
	/**
	 * 增加用户登录日志
	 * 
	 * @param loadLogBean
	 */
	public void addLoadInfo(LoadLogBean loadLogBean);

}
