package com.stone.service.Impl;

import com.stone.bean.Food;
import com.stone.bean.OrderBean;
import com.stone.mapper.FoodMapper;
import com.stone.mapper.OrderMapper;
import com.stone.service.FoodService;
import com.stone.service.OrderService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {
    @Resource
    private FoodMapper foodMapper;

    @Override
    public List<Food> getStore(int id) {
        Example example = new Example(Food.class);
        example.createCriteria().andEqualTo("storeId", id);

        return foodMapper.selectByExample(example);
    }

    @Override
    public List<Food> getAllFood() {
        Example example = new Example(Food.class);
        return foodMapper.selectByExample(example);
    }

    @Override
    public List<Food> getFood(int id,String name) {
        Example example = new Example(Food.class);
        example.createCriteria().andEqualTo("storeId", id).andEqualTo("type",name);
        return foodMapper.selectByExample(example);
    }

    @Override
    public int deleteFood(int id) {
        Example example = new Example(Food.class);
        example.createCriteria().andEqualTo("id",id);
        return foodMapper.deleteByExample(example);
    }

    @Override
    public int addFood(Food food, boolean isSave) {
        if(isSave){
            return foodMapper.insertUseGeneratedKeys(food);
        }else{
            return foodMapper.updateByPrimaryKey(food);
        }
    }

    @Override
    public List<Food> searchFood(int id,String con) {
        Example example = new Example(Food.class);
        if(!con.equals("")){
            example.createCriteria().andEqualTo("storeId",id).andLike("name","%"+con+"%");
        }else {
            example.createCriteria().andEqualTo("storeId",id);
        }
        return foodMapper.selectByExample(example);
    }

    @Override
    public Food getFoodById(int id) {
        Example example = new Example(Food.class);
        example.createCriteria().andEqualTo("id",id);
        return foodMapper.selectOneByExample(example);
    }
    @Override
    public List<Food> getFoodByTaste(String taste){
        Example example = new Example(Food.class);
        example.createCriteria().andEqualTo("taste",taste);
        return foodMapper.selectByExample(example);
    }


}
