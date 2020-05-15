package com.saxiao.orderinghelpapp.model;

import android.app.Activity;

public class IndexItem {

	private Integer name;
	private Integer image;
	private Class<? extends Activity> classPazh;
	private int sum;
	private int background;//颜色

	public IndexItem(Integer name, Integer image, Class<? extends Activity> classPazh,int background) {
		super();
		this.name = name;
		this.image = image;
		this.classPazh = classPazh;
		this.sum = 0;
		this.background=background;
	}

	public Integer getName() {
		return name;
	}

	public void setName(Integer name) {
		this.name = name;
	}

	public Integer getImage() {
		return image;
	}

	public void setImage(Integer image) {
		this.image = image;
	}

	public Class<? extends Activity> getClassPazh() {
		return classPazh;
	}

	public void setClassPazh(Class<? extends Activity> classPazh) {
		this.classPazh = classPazh;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}
	public int getBackground() {
		return background;
	}

	public void setBackground(int background) {
		this.background = background;
	}

}
