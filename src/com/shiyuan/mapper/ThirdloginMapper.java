package com.shiyuan.mapper;

import com.shiyuan.model.Thirdlogin;
import com.shiyuan.model.ThirdloginExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ThirdloginMapper {
    long countByExample(ThirdloginExample example);

    int deleteByExample(ThirdloginExample example);

    int deleteByPrimaryKey(Long thirdloginId);

    int insert(Thirdlogin record);

    int insertSelective(Thirdlogin record);

    List<Thirdlogin> selectByExample(ThirdloginExample example);

    Thirdlogin selectByPrimaryKey(Long thirdloginId);

    int updateByExampleSelective(@Param("record") Thirdlogin record, @Param("example") ThirdloginExample example);

    int updateByExample(@Param("record") Thirdlogin record, @Param("example") ThirdloginExample example);

    int updateByPrimaryKeySelective(Thirdlogin record);

    int updateByPrimaryKey(Thirdlogin record);
}