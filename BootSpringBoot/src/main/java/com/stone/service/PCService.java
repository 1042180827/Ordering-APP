package com.stone.service;

import com.stone.bean.UserBean;

import java.util.List;

public interface PCService {

    UserBean getUserByName(String name, String pwd);
    List<UserBean> getUsersByStatus(int status);

}