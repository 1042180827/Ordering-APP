package com.saxiao.orderinghelpapp.model;

public class Recommand {
    private int id;
    private String caipin;
    private int userId;
    private String userName;
    private String taste;


    public String getCaipin() {
        return caipin;
    }

    public void setCaipin(String caipin) {
        this.caipin = caipin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTaste(){return taste;}

    public void setTaste(String taste){this.taste = taste;}
}
