package com.shiyuan.controller.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shiyuan.mapper.MealMapper;
import com.shiyuan.model.Meal;
import com.shiyuan.model.MealExample;
import com.shiyuan.util.JsonUtil;
import com.shiyuan.util.RandomUtil;

//¿çÓò´«Êä
@CrossOrigin
@Controller
@RequestMapping(produces = { "application/json;charset=UTF-8" })
public class MealController {
	@Autowired
	MealMapper mealMapper;
	MealExample mealExample = new MealExample();
	

	@ResponseBody
	@PostMapping("getmeals")
	public String getMeals() {
		return JsonUtil.basic(mealMapper.selectByExample(null));
		
	}
	
	@ResponseBody
	@PostMapping("addmeal")
	public String addMeal(@RequestParam("storeId") Long storeId,@RequestParam("mealName") String mealName) {
		Meal meal = new Meal();
		meal.setMealId(RandomUtil.randomNumber(16));
		meal.setMealName(mealName);
		meal.setStoreId(storeId);
		int insert = mealMapper.insert(meal);
		return insert ==1 ? JsonUtil.basic(meal) : JsonUtil.basicError(3, "Ìí¼Ó´íÎó");
	}
	
	@ResponseBody
	@PostMapping("getmeal")
	public String getMeal(@RequestParam("storeId") Long storeId) {
		mealExample.clear();
		mealExample.createCriteria().andStoreIdEqualTo(storeId);
		return JsonUtil.basic(mealMapper.selectByExample(mealExample));
		
	}
}
