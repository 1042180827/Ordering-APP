package com.saxiao.orderinghelpapp.model;

/**
 * 评价类
 */
public class PingBean {
	/**
	 * 主键id
	 */
	private int id;
	/**
	 * 评价人id
	 */
	private int userId;
	/**
	 * 评价人姓名
	 */
	private String name;
	/**
	 * 被评价订单id
	 */
	private int orderId;
	/**
	 * 被评价商家id
	 */
	private int storeId;
	/**
	 * 被评价商家名称
	 */
	private String storeName;
	/**
	 * 评论的内容
	 */
	private String content;
	/**
	 * 评论的日期
	 */
	private String date;



	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
