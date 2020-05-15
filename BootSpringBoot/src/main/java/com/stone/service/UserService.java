package com.stone.service;

import com.stone.bean.UserBean;

import java.util.List;

public interface UserService {

	int addUser(UserBean user, boolean isSave);

	UserBean getUserByName(String name, String pwd);
	List<UserBean> getUsersByStatus(int status);

	List<UserBean> getAllUsers();
	List<UserBean> getStore();
}
