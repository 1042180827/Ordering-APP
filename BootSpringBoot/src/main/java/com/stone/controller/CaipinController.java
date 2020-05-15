package com.stone.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stone.bean.Caipin;
import com.stone.bean.Food;
import com.stone.conf.constant.error.ErrorEnum;
import com.stone.service.CaipinService;
import com.stone.service.FoodService;
import com.stone.service.OrderService;
import com.stone.util.ReturnJSONUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

import static jdk.nashorn.internal.objects.NativeArray.sort;

@RestController
@RequestMapping("/rec")
public class CaipinController {
    @Resource
    private FoodService foodService;
    @Resource
    private CaipinService caipinService;
    List <String> tastes;
    int[] rec_order;
    String[] rec_taste;
    int count =0;
    List<Food> food;

    @PostMapping("/recommend")
    public JSONObject getRecFood(@RequestParam int id){
        tastes = new ArrayList<>();
        rec_taste = new String[3];
        food = new ArrayList<>();
        food.clear();
        List<Caipin> caipins = caipinService.getCaipinById(id);
        for(Caipin caipin :caipins) {
            String taste = caipin.getTaste();
            tastes.add(taste);
        }
        System.out.println(tastes);
        Map<String, Integer> map = new HashMap<>();
        for (String str : tastes) {
            Integer num = map.get(str);
            map.put(str, num == null ? 1 : num + 1);
        }
        System.out.println(map);
        map=sortByValueDescending(map);
        for(String key:map.keySet()){
            if(count<3){
                rec_taste[count]=key;
                System.out.println(rec_taste[count]);
                count++;
            }
        }
        for(int i=0;i<3;i++){
        food.addAll(foodService.getFoodByTaste(rec_taste[i]));
        }
        Set<Food> setData = new HashSet<Food>();
        setData.addAll(food);
        List<Food> rec_food = new ArrayList<>(setData);
        System.out.println(ReturnJSONUtils.successJson(rec_food));
        return ReturnJSONUtils.successJson(rec_food);
    }

    @PostMapping("/add")
    public JSONObject addrec(@RequestBody JSONObject requestJson) {
        System.out.println(requestJson);
        Caipin caipin = JSON.parseObject(requestJson.getJSONObject("caipin").toJSONString(), Caipin.class);
        System.out.println("a");
        System.out.println(caipin);
        System.out.println("b");
        System.out.println(caipinService);
        //boolean isSave = requestJson.getBoolean("isSave");
        caipinService.addCaipin(caipin);

        return ReturnJSONUtils.successJson();
    }
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescending(Map<K, V> map)
    {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>()
        {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2)
            {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}