package com.shiyuan.mapper;

import com.shiyuan.model.Feedback;
import com.shiyuan.model.FeedbackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FeedbackMapper {
    long countByExample(FeedbackExample example);

    int deleteByExample(FeedbackExample example);

    int deleteByPrimaryKey(Long feedbackId);

    int insert(Feedback record);

    int insertSelective(Feedback record);

    List<Feedback> selectByExample(FeedbackExample example);
    
    // 添加根据用户id获取反馈历史记录
    List<Feedback> selectByuserId(Long userId);

    Feedback selectByPrimaryKey(Long feedbackId);

    int updateByExampleSelective(@Param("record") Feedback record, @Param("example") FeedbackExample example);

    int updateByExample(@Param("record") Feedback record, @Param("example") FeedbackExample example);

    int updateByPrimaryKeySelective(Feedback record);

    int updateByPrimaryKey(Feedback record);
}