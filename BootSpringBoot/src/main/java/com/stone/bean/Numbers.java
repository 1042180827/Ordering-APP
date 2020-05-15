package com.stone.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/*
* 取号表
* */
@Data
@Table(name = "numbers")
public class Numbers {

	/**
	 * 主键
	 */
	@Id
	private Integer id;

	/**
	 *
	 */
	@Column(name = "user_phone")
	private String userPhone;

	/**
	 *
	 */
	@Column(name = "num_type")
	private String types;

	private Integer num;

	private String date;

	private Integer status;

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
