package com.stone.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
/*
* 用户表
* */
@Data
@Table(name = "userinfo")
public class UserBean {

	/**
	 * 主键
	 */
	@Id
	private Integer id;
	private String name;

	/**
	 * 密码
	 */
	private String pwd;



	/**
	 * 手机号/登录名
	 */
	@Column(name = "phone_number")
	private String phoneNumber;



	/**
	 * 商家信息
	 */
	//店铺名称
	@Column(name = "store_name")
	private String storeName;
	//店铺地址
	@Column(name = "store_address")
	private String storeAddress;
	//店铺首页图片
	@Column(name = "store_image")
	private String storeImage;

	private String type;

	private String money;
	public String getStoreName(){return storeName;}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public Integer getId(){return id;}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
