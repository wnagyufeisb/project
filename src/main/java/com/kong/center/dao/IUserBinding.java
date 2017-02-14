package com.kong.center.dao;

import com.kong.center.beans.UserBindingBean;

public interface IUserBinding {

	// 根据绑定号码
	public UserBindingBean getUserbindingInfo(String bindingNumber);

	/***
	 * 增加用户绑定信息
	 * 
	 * @param userBindingBean
	 */
	public void addUserBindingInfo(UserBindingBean userBindingBean);

}
