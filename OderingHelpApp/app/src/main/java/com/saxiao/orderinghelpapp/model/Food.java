package com.saxiao.orderinghelpapp.model;

public class Food {
	private int id;
	private String name;//名称
	private double danjia;//单价
	private String type;//分类
	private String image;//图片
	private byte[] bitmap;
	private String taste;

	/**
	 * 所属店铺
	 */
	private String storeId;
	private String storeName;

	/**
	 * 销售量
	 */
	private int saleCount;

	//标记
	private boolean isAdd;

	/**
	 * 正常
	 * 售罄
	 */
	private String status;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDanjia() {
		return danjia;
	}

	public void setDanjia(double danjia) {
		this.danjia = danjia;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isAdd() {
		return isAdd;
	}

	public void setAdd(boolean add) {
		isAdd = add;
	}

	public byte[] getBitmap() {
		return bitmap;
	}

	public void setBitmap(byte[] bitmap) {
		this.bitmap = bitmap;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public int getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaste(){ return taste;}

	public void setTaste(String taste){this.taste = taste;}
}
