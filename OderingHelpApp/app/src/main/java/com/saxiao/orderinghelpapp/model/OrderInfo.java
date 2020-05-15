package com.saxiao.orderinghelpapp.model;

/**
 * 购物车
 */
public class OrderInfo {
	private int id;

	private String caipin;
	private String danjia;
	private String userId;
	private String userName;
	private String date;
	private String type;
	private String foodTaste;

	private int fenshu;
	private int storeId;
	private String storeName;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCaipin() {
		return caipin;
	}

	public void setCaipin(String caipin) {
		this.caipin = caipin;
	}

	public String getDanjia() {
		return danjia;
	}

	public void setDanjia(String danjia) {
		this.danjia = danjia;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getFenshu() {
		return fenshu;
	}

	public void setFenshu(int fenshu) {
		this.fenshu = fenshu;
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

	public String getFoodTaste() { return foodTaste; }

	public  void setFoodTaste(String food_taste) { this.foodTaste = food_taste; }
}
