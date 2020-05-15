package com.stone.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.stone.mapper.UserMapper;
import com.stone.bean.UserBean;
import com.stone.service.UserService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	@Override
	public int addUser(UserBean user, boolean isSave) {
		int retFlag = 0;
		int flag = 0;
		JSONObject retObj = new JSONObject();
		// 用户注册/修改信息
		if (isSave) {
			Example example = new Example(UserBean.class);
			example.createCriteria().andEqualTo("phoneNumber", user.getPhoneNumber());
			UserBean user1 = userMapper.selectOneByExample(example);
			if (user1 == null) {
				flag = userMapper.insertUseGeneratedKeys(user);
			} else {
				// 用户已注册
				return retFlag;
			}
		} else {
			flag = userMapper.updateByPrimaryKey(user);
		}
		if (flag > 0 ) {
			retFlag = 1;
		} else {
			retFlag = 2;
		}
		return retFlag;
	}

	@Override
	public UserBean getUserByName(String name, String pwd) {
		Example example = new Example(UserBean.class);
		example.createCriteria().andEqualTo("phoneNumber", name).andEqualTo("pwd", pwd);
		UserBean user = userMapper.selectOneByExample(example);
		if (user != null) {
			return user;
		}
		return null;
	}

	@Override
	public List<UserBean> getUsersByStatus(int status) {
		Example example = new Example(UserBean.class);
		example.createCriteria().andEqualTo("status", status);
		return userMapper.selectByExample(example);
	}

	@Override
	public List<UserBean> getAllUsers() {
		Example example = new Example(UserBean.class);
		return userMapper.selectByExample(example);
	}


	@Override
	public List<UserBean> getStore() {
		Example example = new Example(UserBean.class);
		example.createCriteria().andEqualTo("type", "merchant");
		return userMapper.selectByExample(example);
	}


}
