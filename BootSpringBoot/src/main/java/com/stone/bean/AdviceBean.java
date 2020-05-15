package com.stone.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/*
* 评价表
* */
@Data
@Table(name = "ping_tb")
public class AdviceBean {

	/**
	 * 主键
	 */
	@Id
	private Integer id;

	/**
	 * 评价人id
	 */
	@Column(name = "user_id")
	private Integer userId;
	/**
	 * 评价人姓名
	 */
	private String name;
	/**
	 * 被评价订单id
	 */
	@Column(name="order_id")
	private Integer orderId;
	/**
	 * 被评价商家id
	 */
	@Column(name="store_id")
	private Integer storeId;
	/**
	 * 被评价商家名称
	 */
	@Column(name ="store_name")
	private String storeName;
	/**
	 * 评论的内容
	 */
	private String content;
	/**
	 * 评论的日期
	 */
	private String date;


}
/**

 */
