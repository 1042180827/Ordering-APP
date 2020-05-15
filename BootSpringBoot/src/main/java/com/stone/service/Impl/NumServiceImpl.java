package com.stone.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.stone.bean.Numbers;
import com.stone.bean.UserBean;
import com.stone.mapper.NumMapper;
import com.stone.mapper.UserMapper;
import com.stone.service.NumService;
import com.stone.service.UserService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NumServiceImpl implements NumService {

	@Resource
	private NumMapper numMapper;




	@Override
	public List<Numbers> getMaxNumByType(String type) {
		Example example = new Example(Numbers.class);
		example.createCriteria().andEqualTo("types", type);
		example.setOrderByClause("num desc");
		return numMapper.selectByExample(example);
	}

	@Override
	public List<Numbers> getMinNumByType(String type) {
		Example example = new Example(Numbers.class);
		example.createCriteria().andEqualTo("types", type);
		example.setOrderByClause("num asc");
		return numMapper.selectByExample(example);
	}



	@Override
	public Numbers addNum(Numbers num) {
		if(numMapper.insertUseGeneratedKeys(num)>0){
			return num;
		}else{
			return null;
		}
	}

	@Override
	public Numbers updateNum(Numbers num) {
		if(numMapper.updateByPrimaryKey(num)>0){
			return num;
		}else{
			return null;
		}
	}
}
