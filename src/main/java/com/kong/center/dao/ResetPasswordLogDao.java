package com.kong.center.dao;

import org.apache.ibatis.session.SqlSession;

import com.kong.center.beans.ResetPasswordLogBean;

public class ResetPasswordLogDao {

	/***
	 * 增加密码修改日志
	 * 
	 * @param userInfoBean
	 */
	public void addResetPasswordLog(ResetPasswordLogBean resetPasswordLogBean,
			SqlSession sqlSession) {
		IResetPasswordLog iResetPasswordLog = sqlSession
				.getMapper(IResetPasswordLog.class);
		iResetPasswordLog.addResetPasswordLog(resetPasswordLogBean);
	}

}
