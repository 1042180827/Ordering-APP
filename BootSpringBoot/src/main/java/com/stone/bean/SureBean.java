package com.stone.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "sure_sys")
public class SureBean {
    /**
     * 主键id
     */
    @Id
    private Integer id;

    private String caipin;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_id")
    private String userId;
    private String beizhu;
    private String zongjia;
    private String date;

    @Column(name = "store_id")
    private Integer storeId;
    @Column(name = "store_name")
    private String storeName;

    private Integer status;
}
