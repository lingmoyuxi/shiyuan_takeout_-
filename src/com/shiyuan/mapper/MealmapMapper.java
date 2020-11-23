package com.shiyuan.mapper;

import com.shiyuan.model.Mealmap;
import com.shiyuan.model.MealmapExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MealmapMapper {
    long countByExample(MealmapExample example);

    int deleteByExample(MealmapExample example);

    int deleteByPrimaryKey(Long mealmapId);

    int insert(Mealmap record);

    int insertSelective(Mealmap record);

    List<Mealmap> selectByExample(MealmapExample example);

    Mealmap selectByPrimaryKey(Long mealmapId);

    int updateByExampleSelective(@Param("record") Mealmap record, @Param("example") MealmapExample example);

    int updateByExample(@Param("record") Mealmap record, @Param("example") MealmapExample example);

    int updateByPrimaryKeySelective(Mealmap record);

    int updateByPrimaryKey(Mealmap record);
}