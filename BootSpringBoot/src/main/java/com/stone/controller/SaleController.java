package com.stone.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import com.alibaba.fastjson.JSONObject;
import com.stone.bean.SaleBean;
import com.stone.bean.SureBean;
import com.stone.conf.constant.error.ErrorEnum;
import com.stone.service.SaleService;
import com.stone.service.SureService;
import com.stone.util.ReturnJSONUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController {
	@Resource
	private SaleService saleService;

	
	/*
	 * 加入
	 * */
	@PostMapping("/add")
	public JSONObject addOrder(@RequestBody String str) {
		str = str.replace("\\","");
		str = str.substring(1,str.length()-1);
		System.out.println(str);
		JSONObject jsonObject = JSONObject.parseObject(str);
		String jsonObjString = jsonObject.getString("sale");
		List<SaleBean> list = (List<SaleBean>) JSONArray.parseArray(jsonObjString, SaleBean.class);
		boolean isSave = jsonObject.getBoolean("isSave");
		int flag = saleService.addSale(list,isSave);
		return flag > 0 ? ReturnJSONUtils.successJson() : ReturnJSONUtils.errorJson(ErrorEnum.E_400);
	}


	/*
	 *
	 * */
	@PostMapping("/get_store")
	public JSONObject getOrder(@RequestParam int storeId) {
		return ReturnJSONUtils.successJson(saleService.getSaleByStore(storeId));
	}

}
