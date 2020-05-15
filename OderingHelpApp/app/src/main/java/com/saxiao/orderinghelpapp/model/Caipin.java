package com.saxiao.orderinghelpapp.model;



public class Caipin {
    private int id;
    private String name;
    private Integer userId;
    private String storeId;
    private String taste;




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

    public Integer getUserId() { return userId ;}

    public void  setUserId(Integer userId) { this.userId=userId; }

    public String getTaste() { return taste; }

    public void  setTaste(String taste){ this.taste = taste; }

    public String getStoreId(){ return storeId; }

    public void setStoreId(String storeId) { this.storeId = storeId;}

}
