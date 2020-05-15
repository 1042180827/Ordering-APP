package com.saxiao.orderinghelpapp.model;

import java.util.List;

public class FoodType {
	private int id;
	private String name;
	private List<Food> food;
	/**
	 * 是否展开
	 */
	private boolean isShow=false;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Food> getFood() {
		return food;
	}

	public void setFood(List<Food> food) {
		this.food = food;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean show) {
		isShow = show;
	}
}
