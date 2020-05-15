package com.saxiao.orderinghelpapp.model;

import java.io.Serializable;

public class UserBean implements Serializable {
	private int id;

	/**
	 * 密码
	 */
	private String pwd;
	/**
	 * 手机号
	 */
	private String phoneNumber;

	private String name;

	/**
	 * 商家信息
	 */
	//店铺名称
	private String storeName;
	//店铺地址
	private String storeAddress;
	//店铺首页图片
	private String storeImage;

	/**
	 * 商家
	 * 买家
	 * 管理员
	 */
	private String type;

	/**
	 * 个人账户余额
	 */
	private String money;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreAddress() {
		return storeAddress;
	}

	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}

	public String getStoreImage() {
		return storeImage;
	}

	public void setStoreImage(String storeImage) {
		this.storeImage = storeImage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
}
