package com.kong.center.beans;

public class ResultBean<T> {

	private boolean isSuccess;
	private String message;
	private T data;
	private String statusCode;
	
	
	public ResultBean() {
		
	}
	
	public ResultBean(boolean isSuccess,String message,String statusCode,T date) {
		this.setIsSuccess(isSuccess);
		this.setMessage(message);
		this.setData(date);
		this.setStatusCode(statusCode);
		
	}
	
	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	/**
	 * 
	 * @日期：2014年12月18日
	 * @Title: setProperties 
	 * @Description: TODO 构造一个复制所有属性的方法
	 * @param isSuccess
	 * @param message
	 * @param data void
	 */
	
	public void setProperties(boolean isSuccess, String message,String statusCode, T data) {
		this.isSuccess = isSuccess;
		this.message = message;
		this.setData(data);
		this.setStatusCode(statusCode);
	}

	@Override
	public String toString() {
		return "ResultBean [isSuccess=" + isSuccess + ", message=" + message
				+ ", data=" + data + "]";
	}
	
	
}
