package com.stone.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stone.bean.OrderBean;
import com.stone.bean.SureBean;
import com.stone.conf.constant.error.ErrorEnum;
import com.stone.service.OrderService;
import com.stone.service.SureService;
import com.stone.util.ReturnJSONUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sure")
public class SureController {
	@Resource
	private SureService orderService;

	
	/*
	 * 加入订单
	 * */
	@PostMapping("/add")
	public JSONObject addOrder(@RequestBody JSONObject requestJson) {
		SureBean order = JSON.parseObject(requestJson.getJSONObject("sure").toJSONString(), SureBean.class);
		boolean isSave = requestJson.getBoolean("isSave");
		int flag = orderService.addOrder(order,isSave);
		System.out.println(flag);
		return flag > 0 ? ReturnJSONUtils.successJson() : ReturnJSONUtils.errorJson(ErrorEnum.E_400);
	}

	/*
	 *
	 * */
	@PostMapping("/get_my")
	public JSONObject getOrder(@RequestParam int userId) {
		return ReturnJSONUtils.successJson(orderService.getMy(userId));
	}


	@PostMapping("/get_store")
	public JSONObject getStoreOrder(@RequestParam int storeId) {
		return ReturnJSONUtils.successJson(orderService.getStore(storeId));
	}


	@PostMapping("/update")
	public JSONObject updateStatus(@RequestParam int storeId) {
		return ReturnJSONUtils.successJson(orderService.getStore(storeId));
	}
}
