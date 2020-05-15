package com.stone.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stone.bean.OrderBean;
import com.stone.conf.constant.error.ErrorEnum;
import com.stone.service.OrderService;
import com.stone.util.ReturnJSONUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Resource
	private OrderService orderService;

	
	/*
	 * 加入订单
	 * */
	@PostMapping("/add_to")
	public JSONObject addOrder(@RequestBody JSONObject requestJson) {
		OrderBean order = JSON.parseObject(requestJson.toJSONString(), OrderBean.class);
		int flag = orderService.addOrder(order);
		return flag > 0 ? ReturnJSONUtils.successJson() : ReturnJSONUtils.errorJson(ErrorEnum.E_400);
	}

	/*
	 * 删除
	 * */
	@PostMapping("/delete")
	public JSONObject deleteOrder(@RequestParam int userId) {
		return ReturnJSONUtils.successJson(orderService.deleteOrder(userId));
	}

	/*
	 *
	 * */
	@PostMapping("/get_id")
	public JSONObject getOrder(@RequestParam int userId) {
		return ReturnJSONUtils.successJson(orderService.getOrderByMy(userId));
	}


}
