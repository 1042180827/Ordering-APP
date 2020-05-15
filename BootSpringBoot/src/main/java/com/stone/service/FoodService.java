package com.stone.service;

import com.stone.bean.Food;
import com.stone.bean.OrderBean;

import java.util.List;

public interface FoodService {


	List<Food> getStore(int id);

	List<Food> getAllFood();

	List<Food> getFood(int id,String type);

	int deleteFood(int id);

	int addFood(Food food,boolean isSave);

	List<Food> searchFood(int id,String  con);

	Food getFoodById(int id);

	List<Food> getFoodByTaste(String taste);
}
