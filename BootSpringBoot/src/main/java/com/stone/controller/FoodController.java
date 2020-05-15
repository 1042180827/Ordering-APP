package com.stone.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stone.bean.Food;
import com.stone.bean.FoodType;
import com.stone.bean.OrderBean;
import com.stone.bean.SureBean;
import com.stone.conf.constant.error.ErrorEnum;
import com.stone.service.FoodService;
import com.stone.service.OrderService;
import com.stone.util.ReturnJSONUtils;
import org.apache.ibatis.annotations.Property;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/food")
public class FoodController {
	@Resource
	private FoodService foodService;


	

	@PostMapping("/get")
	public JSONObject getOrder(@RequestParam int id) {
		List<Food> list = foodService.getStore(id);

        /**
         * 先把分类存到tyeList里面，然后在把食物列表存入对应的分类里面
         */

        List<FoodType> allList = new ArrayList<>();
        //先把所有的type取出放到集合(allList中有重复的分类)
        for(int i=0;i<list.size();i++){
            FoodType f = new FoodType();
            f.setName(list.get(i).getType());
            f.setFood(new ArrayList<>());
            allList.add(f);
        }
        //去除重复
        Set<FoodType> setData = new HashSet<FoodType>();
        setData.addAll(allList);
        List<FoodType> typeList = new ArrayList<>(setData);
        System.out.println(typeList.size());

        //把食物添加到对应的分类下
        for(int i=0;i<typeList.size();i++){
            typeList.get(i).setFood(foodService.getFood(id,typeList.get(i).getName()));
        }
        System.out.println(ReturnJSONUtils.successJson(typeList));
		return ReturnJSONUtils.successJson(typeList);
	}

    @PostMapping("/get_store")
    public JSONObject getOrder2(@RequestParam int id) {
        return ReturnJSONUtils.successJson(foodService.getStore(id));
    }

    @PostMapping("/get_all")
    public JSONObject getAll() {
        return ReturnJSONUtils.successJson(foodService.getAllFood());
    }

    @PostMapping("/get_id")
    public JSONObject getFoodById(@RequestParam int id) {
	    System.out.println(ReturnJSONUtils.successJson(foodService.getFoodById(id)).toJSONString());
        return ReturnJSONUtils.successJson(foodService.getFoodById(id));
    }

    @PostMapping("/delete")
    public JSONObject deleteFood(@RequestParam int id) {
        return ReturnJSONUtils.successJson(foodService.deleteFood(id));
    }

    @PostMapping("/search")
    public JSONObject searchFood(@RequestParam int storeId,@RequestParam String con) {
        return ReturnJSONUtils.successJson(foodService.searchFood(storeId,con));
    }

    /*
     * 添加
     * */
    @PostMapping("/add")
    public JSONObject addOrder(@RequestBody JSONObject requestJson) {
        System.out.println(requestJson);
        Food food = JSON.parseObject(requestJson.getJSONObject("food").toJSONString(), Food.class);
        boolean isSave = requestJson.getBoolean("isSave");
        int flag = foodService.addFood(food,isSave);
        return flag > 0 ? ReturnJSONUtils.successJson() : ReturnJSONUtils.errorJson(ErrorEnum.E_400);
    }
//    @PostMapping("/recommmand")
//    public JSONObject recommandFood(@RequestParam int userId) {
//        orderService.getOrderByMy(userId);
//
//        System.out.println(ReturnJSONUtils.successJson(foodService.getFoodById(id)).toJSONString());
//        return ReturnJSONUtils.successJson(foodService.getFoodById(id));
//    }


}
