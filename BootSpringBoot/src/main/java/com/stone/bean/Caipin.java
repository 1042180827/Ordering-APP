package com.stone.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "caipin")
public class Caipin {
    /**
     * 主键id
     */
    @Id
    private Integer id;

    private String name;//名称
    @Column(name = "storeId")
    private String storeId;


    private String taste;
    @Column(name = "userId")
    private int userId;
    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }
    public String getTaste(){
        return taste;
    }
}
