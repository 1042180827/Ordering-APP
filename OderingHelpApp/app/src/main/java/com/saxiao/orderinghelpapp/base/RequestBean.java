package com.saxiao.orderinghelpapp.base;
/**
 * 返回数据总类
 */

public class RequestBean<T>{
    private  T returnData;
    private boolean success;
    private String msg;

	public T getReturnData() {
		return returnData;
	}

	public void setReturnData(T returnData) {
		this.returnData = returnData;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
