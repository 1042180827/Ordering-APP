package com.stone.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stone.bean.Numbers;
import com.stone.bean.UserBean;
import com.stone.conf.constant.error.ErrorEnum;
import com.stone.service.NumService;
import com.stone.service.UserService;
import com.stone.util.ReturnJSONUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/num")
public class NumController {
	@Resource
	private NumService numService;


	@PostMapping("/addNum")
	public JSONObject addNum(@RequestBody JSONObject requestJson) {
		Numbers numbers = JSON.parseObject(requestJson.toJSONString(), Numbers.class);
		List<Numbers> list = numService.getMaxNumByType(numbers.getTypes());
		if(list!=null && list.size()>0){
			numbers.setNum(list.get(0).getNum()+1);
		}else{
			numbers.setNum(1);
		}

		Numbers flag = numService.addNum(numbers);
		if(flag!=null){
			return ReturnJSONUtils.successJson(flag);
		}else{
			return ReturnJSONUtils.errorJson(ErrorEnum.E_10008);
		}
		//return flag == 0 ? ReturnJSONUtils.errorJson(ErrorEnum.E_10008): (flag == 1 ? ReturnJSONUtils.successJson() : ReturnJSONUtils.errorJson(ErrorEnum.E_400));
	}


	@PostMapping("/seeNum")
	public Integer seeNum(@RequestParam String type){
		/**
		 * 模拟叫号，
		 * 具体实现：
		 * 5秒请求一次接口，把一条数据置1（status=1）代表可以就餐。
		 * 并且获取当前桌位类型的前面排队人数（前面还有多少位）。
		 */

		List<Numbers> list = numService.getMinNumByType(type);
		for(int i=0;i<list.size();i++){
			if(list.get(i).getStatus()!=1){
				list.get(i).setStatus(1);
				numService.updateNum(list.get(i));
				//当前叫到了哪个号
				return list.get(i).getNum();
			}

		}

		return -1;
	}


}
