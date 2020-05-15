package com.stone.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "sale_sys")
public class SaleBean {
    /**
     * 主键id
     */
    @Id
    private Integer id;

    private String name;

    @Column(name = "store_id")
    private Integer storeId;

    private Integer sale;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    @Override public boolean equals(Object obj) {
        SaleBean f = (SaleBean) obj;
        return f != null && this.getName().equals(f.getName());
    }
}
