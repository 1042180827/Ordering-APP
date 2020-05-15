package com.saxiao.orderinghelpapp.model;

import java.io.Serializable;

public class SureOrder implements Serializable {
	private int id;
	private String caipin;
	private int userId;
	private String userName;
	private String beizhu;
	private String zongjia;
	private String date;

	/**
	 * 在哪家下的订单
	 */
	private int storeId;
	private String storeName;

	/**
	 * 0：未接单
	 * 1：已接单
	 */
	private int status;

	public String getCaipin() {
		return caipin;
	}

	public void setCaipin(String caipin) {
		this.caipin = caipin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	public String getZongjia() {
		return zongjia;
	}

	public void setZongjia(String zongjia) {
		this.zongjia = zongjia;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
