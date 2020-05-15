package com.stone.service;

import com.stone.bean.Numbers;
import com.stone.bean.UserBean;

import java.util.List;

public interface NumService {

	Numbers addNum(Numbers num);
	List<Numbers> getMaxNumByType(String type);
	Numbers updateNum(Numbers num);
	List<Numbers> getMinNumByType(String type);

}
