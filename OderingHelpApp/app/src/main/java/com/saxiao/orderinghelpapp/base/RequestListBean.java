package com.saxiao.orderinghelpapp.base;

import java.util.List;

/**
 * 返回数据总类
 */

public class RequestListBean<T> {

	private boolean success;
	private String msg;
	private List<T> returnData;
	private int currPage;
	private int pageSize;
	private int total;

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getReturnData() {
		return returnData;
	}

	public void setReturnData(List<T> returnData) {
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
