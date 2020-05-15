package com.stone.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stone.bean.Food;
import com.stone.bean.UserBean;
import com.stone.conf.constant.error.ErrorEnum;
import com.stone.service.FoodService;
import com.stone.service.PCService;
import com.stone.util.ReturnJSONUtils;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;

@Controller
public class PCController {
    @Resource
    private PCService pcService ;
    @Resource
    private FoodService foodService;

    @RequestMapping("/admin")
    public Object loginOut() {
        return "login";
    }

    @RequestMapping("/admin/login")
    @ResponseBody
    public Object userLogin(@RequestParam("userName") String userName, @RequestParam("pwd") String pwd, HttpSession session) {
        if(userName !=null){
            if (pwd !=null){
                System.out.println(pwd);
                UserBean user = pcService.getUserByName(userName, pwd);
//        return user != null ? ReturnJSONUtils.successJson(user) : ReturnJSONUtils.errorJson(ErrorEnum.E_10009);
                if (user != null){
                    session.setAttribute("userid", user.getId());
                    session.setAttribute("user", user);
                    return ReturnJSONUtils.successJson(user);
                }
                else {
                    return ReturnJSONUtils.errorJson(ErrorEnum.E_10009);
                }
            }
            else {
                return ReturnJSONUtils.errorJson(ErrorEnum.E_10009);
            }
        }
        else {
            return ReturnJSONUtils.errorJson(ErrorEnum.E_10009);
        }
    }
    @RequestMapping("/index")
    public Object getOrder(HttpServletRequest request, ModelMap map) {
        HttpSession session=request.getSession();
        int id = Integer.parseInt(session.getAttribute("userid").toString());
        List<Food> list = foodService.getStore(id);

//      Map<String,Object> params = new HashMap<>();
        session.setAttribute("foodlist",list);
        //map.addAttribute("foodlist",typeList);
        return "index";
    }
    @PostMapping("/admin/get_id")
    public JSONObject getFoodById(@RequestParam int id) {
        System.out.println(ReturnJSONUtils.successJson(foodService.getFoodById(id)).toJSONString());
        return ReturnJSONUtils.successJson(foodService.getFoodById(id));
    }

    @PostMapping("/admin/delete")
    public Object deleteFood(@RequestParam int id) {
        foodService.deleteFood(id);
        return "index";
    }
    @GetMapping("/admin/food/input")
    public String input(Model model) {
        model.addAttribute("food", new Food());
        return "foodinput";
    }

    @GetMapping("/admin/food/{id}/input")
    public String editInput(@PathVariable Integer id, Model model) {
        Food food = foodService.getFoodById(id);
        model.addAttribute("food",food);
        return "foodinput";
    }



    @PostMapping("/index")
    public String post( Food food,  HttpSession session, MultipartFile file) {
        //Food food = JSON.parseObject(requestJson.getJSONObject("food").toJSONString(), Food.class);
        //session.setAttribute("food",food);
        //boolean isSave = requestJson.getBoolean("isSave");
        //foodService.addFood(food,isSave);
//        try {
//            InputStream ins = file.getInputStream();
//            byte[] buffer=new byte[1024];
//            int len=0;
//            ByteArrayOutputStream bos=new ByteArrayOutputStream();
//            while((len=ins.read(buffer))!=-1){
//                bos.write(buffer,0,len);
//            }
//            bos.flush();
//            byte data[] = bos.toByteArray();
//            String imgStr = new String(data);
//            food.setImage(imgStr);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        boolean a;
        System.out.println(food.getId());
        String img = food.getImage();
        System.out.println(img);
        String[] array= img.split(",");
        if(array.length>1){
            String newimg = array[1];
            food.setImage(newimg);
        }
        if (food.getId() == null) {
            a=true;
            foodService.addFood(food,a);
        } else {
            a=false;
            foodService.addFood(food,a);
        }
        return "redirect:/index";
    }
}
