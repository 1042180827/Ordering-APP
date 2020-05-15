package com.stone.service;

import com.stone.bean.OrderBean;
import com.stone.bean.SureBean;

import java.util.List;

public interface SureService {



	int addOrder(SureBean order,boolean isSave);

	List<SureBean> getMy(int userId);

	List<SureBean> getStore(int storeId);
}
