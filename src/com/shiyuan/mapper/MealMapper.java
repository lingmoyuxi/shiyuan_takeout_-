package com.shiyuan.mapper;

import com.shiyuan.model.Meal;
import com.shiyuan.model.MealExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MealMapper {
    long countByExample(MealExample example);

    int deleteByExample(MealExample example);

    int deleteByPrimaryKey(Long mealId);

    int insert(Meal record);

    int insertSelective(Meal record);

    List<Meal> selectByExample(MealExample example);

    Meal selectByPrimaryKey(Long mealId);

    int updateByExampleSelective(@Param("record") Meal record, @Param("example") MealExample example);

    int updateByExample(@Param("record") Meal record, @Param("example") MealExample example);

    int updateByPrimaryKeySelective(Meal record);

    int updateByPrimaryKey(Meal record);
}