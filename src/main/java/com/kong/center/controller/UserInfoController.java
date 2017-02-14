package com.kong.center.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kong.center.beans.RebackBean;
import com.kong.center.beans.ResultBean;
import com.kong.center.service.UserInfoService;

@Controller
@RequestMapping(value = "/user")
public class UserInfoController {
	private UserInfoService userInfoService = new UserInfoService();

	/***
	 * 用户登录
	 * 
	 * @param request
	 * @param account
	 * @param password
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/userload", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<RebackBean> userLoad(HttpServletRequest request) {
		Map<String, String> params = getRequestMap(request);
		String ip = request.getRemoteAddr();
		params.put("ip", ip);
		ResultBean<RebackBean> resultBean = userInfoService.userLoad(params);
		System.out.println("userload=============" + new Date() + "=========="
				+ resultBean.toString());
		return resultBean;
	}

	/**
	 * 注册
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<RebackBean> userRegister(HttpServletRequest request) {

		Map<String, String> params = getRequestMap(request);
		ResultBean<RebackBean> resultBean = userInfoService
				.userRegister(params);
		System.out.println("register=============" + new Date() + "=========="
				+ resultBean.toString());
		return resultBean;
	}

	/***
	 * 激活日志
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<String> userActivate(HttpServletRequest request) {
		Map<String, String> params = getRequestMap(request);
		String ip = request.getRemoteAddr();
		params.put("ip", ip);
		ResultBean<String> resultBean = userInfoService.userActivate(params);
		System.out.println("activate=============" + new Date() + "=========="
				+ resultBean.toString());
		return resultBean;
	}

	/****
	 * 获取请求的map
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, String> getRequestMap(HttpServletRequest request) {
		Map<String, String> params = new HashMap<>();
		Map<?, ?> requestParams = request.getParameterMap();
		for (Iterator<?> iter = requestParams.keySet().iterator(); iter
				.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		return params;
	}

	/***
	 * 验证验证码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<String> resetPassword(HttpServletRequest request) {
		Map<String, String> params = getRequestMap(request);
		String ip = request.getRemoteAddr();
		params.put("ip", ip);

		return userInfoService.resetPassword(params);
	}

	/***
	 * 账号修改密码
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/resetPasswordByAccount", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<String> resetPasswordByAccount(HttpServletRequest request)
			throws Exception {
		Map<String, String> params = getRequestMap(request);
		String ip = request.getRemoteAddr();
		params.put("ip", ip);
		return userInfoService.resetPasswordByAccount(params);
	}

	/***
	 * 验证验证码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/validateVerifyCode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<String> findPassword(HttpServletRequest request) {
		Map<String, String> params = getRequestMap(request);
		ResultBean<String> resultBean = userInfoService
				.validateVerifyCode(params);
		return resultBean;
	}

	/***
	 * 发送验证码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sendVerifyCode", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<String> sendEmail(HttpServletRequest request) {
		Map<String, String> params = getRequestMap(request);
		return userInfoService.sendEmail(params);
	}

	/***
	 * 根据token获取uid
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/verifyToken", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<String> verifyTokenGetUid(HttpServletRequest request) {
		Map<String, String> params = getRequestMap(request);
		String ip = request.getRemoteAddr();
		params.put("ip", ip);
		return userInfoService.verifyTokenGetUid(params);
	}

	/***
	 * 绑定操作
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/binding", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<String> binding(HttpServletRequest request) {
		Map<String, String> params = getRequestMap(request);
		String ip = request.getRemoteAddr();
		params.put("ip", ip);
		return userInfoService.binding(params);
	}

	/***
	 * 解绑操作
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/unbinding", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<String> unbinding(HttpServletRequest request) {
		Map<String, String> params = getRequestMap(request);
		String ip = request.getRemoteAddr();
		params.put("ip", ip);
		return userInfoService.binding(params);
	}
}
