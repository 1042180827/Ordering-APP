package com.saxiao.orderinghelpapp.model;

/**
 * 取号
 */
public class Numbers {
	private int id;

	/**
	 * A、B、C、D
	 */
	private String types;

	/**
	 * 客人手机号
	 */
	private String userPhone;

	/**
	 * 取号时间
	 */
	private String date;

	/**
	 * 排号值
	 */
	private int num;

	/**
	 * 状态
	 * 0未叫到
	 * 1叫到
	 */
	private int status;

	public String getType() {
		return types;
	}

	public void setType(String type) {
		this.types = type;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
