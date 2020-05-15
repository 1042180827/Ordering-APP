package com.saxiao.orderinghelpapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * 评价
 */
public class Advice implements Serializable {
	/**
	 * 主键id
	 */
	private int id;
	/**
	 *
	 */
	private String zhuo;

	/**
	 * 内容
	 */
	private String content;
	/**
	 * 发布日期
	 */
	private String date;

	/**
	 * 文中图片
	 */
	private String image;

	/**
	 * 本地地址
	 */
	private List<String> path;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getZhuo() {
		return zhuo;
	}

	public void setZhuo(String zhuo) {
		this.zhuo = zhuo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<String> getPath() {
		return path;
	}

	public void setPath(List<String> path) {
		this.path = path;
	}
}