package com.kong.center.dao;

import com.kong.center.beans.ResetPasswordLogBean;

public interface IResetPasswordLog {

	/***
	 * 增加修改修改密码日志信息
	 * @param resetPasswordLogBean
	 */
	public void addResetPasswordLog(ResetPasswordLogBean resetPasswordLogBean);

}
