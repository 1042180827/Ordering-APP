package com.stone.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "orderinfo")
public class OrderBean {
    /**
     * 主键id
     */
    @Id
    private Integer id;

    private String caipin;
    private String danjia;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_id")
    private String userId;
    private String date;
    private String type;
    @Column(name = "fenshu")
    private Integer fenshu;
    @Column(name = "store_id")
    private Integer storeId;
    @Column(name = "store_name")
    private String storeName;
    private String foodTaste;

    public String getFoodTaste(){ return foodTaste;}
    public String getCaipin() {
        return caipin;
    }

    public void setCaipin(String caipin) {
        this.caipin = caipin;
    }

}
