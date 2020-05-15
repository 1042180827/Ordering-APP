package com.stone.service;

import com.stone.bean.Food;
import com.stone.bean.OrderBean;

import java.util.List;

public interface OrderService {


	List<OrderBean> getOrderByMy(int id);
	int deleteOrder(int id);
	int addOrder(OrderBean order);

}
