package com.stone.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.stone.mapper.UserMapper;
import com.stone.bean.UserBean;
import com.stone.service.UserService;
import com.stone.service.PCService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;


@Service
public class PCServiceImpl implements PCService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserBean getUserByName(String name, String pwd) {
        Example example = new Example(UserBean.class);
        example.createCriteria().andEqualTo("phoneNumber", name).andEqualTo("pwd", pwd).andEqualTo("type","merchant");
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
}
