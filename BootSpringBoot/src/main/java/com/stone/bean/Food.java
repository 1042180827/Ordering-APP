package com.stone.bean;


import javax.persistence.Column;
import javax.persistence.Id;

public class Food {
	/**
	 * 主键
	 */
	@Id
	private Integer id;

	private String name;//名称
	@Column(name = "price")
	private double danjia;//单价
	private String type;//分类
	private String image;//图片
	@Column(name = "store_id")
	private String storeId;
	@Column(name = "store_name")
	private String storeName;
	@Column(name = "sale_count")
	private Integer saleCount;
	private String taste;//口味

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Integer getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(Integer saleCount) {
		this.saleCount = saleCount;
	}

	public String getStatus() {
		return status;
	}

	public String getTaste(){return taste;}

	public void setTaste(String taste){this.taste = taste;}

	public void setStatus(String status) {
		this.status = status;
	}
}
