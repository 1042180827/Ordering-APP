package com.stone.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import org.springframework.format.annotation.DateTimeFormat;

/*
* 基础信息
* */
public abstract class BaseEntity implements Serializable {

	/*
	* 创建时间
	* */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "create_time")
	private Date createTime;

	/*
	* 创建人
	* */
	@Column(name = "create_by")
	private String createBy;

	/*
	* 修改时间
	* */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "update_time")
	private Date updateTime;

	/*
	* 修改人
	* */
	@Column(name = "update_by")
	private String updateBy;
}
