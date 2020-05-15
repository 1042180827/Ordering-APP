package com.stone.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stone.bean.AdviceBean;
import com.stone.conf.constant.error.ErrorEnum;
import com.stone.service.TalkService;
import com.stone.util.ReturnJSONUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/pj")
public class TalkController {
	@Resource
	private TalkService talkService;

	/*
	* 保存评论
	* */
	@PostMapping("/add")
	public JSONObject savePing(@RequestBody JSONObject requestJson) {
		AdviceBean talk = JSON.parseObject(requestJson.toJSONString(), AdviceBean.class);
		int flag = talkService.saveTalk(talk);
		return flag > 0 ? ReturnJSONUtils.successJson() : ReturnJSONUtils.errorJson(ErrorEnum.E_400);
	}

	/*
	 *
	 * */
	@PostMapping("/get_store")
	public JSONObject getPing(@RequestParam int storeId) {
		return ReturnJSONUtils.successJson(talkService.getTalk(storeId));
	}

}
