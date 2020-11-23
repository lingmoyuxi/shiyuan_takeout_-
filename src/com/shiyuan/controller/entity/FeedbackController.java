package com.shiyuan.controller.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shiyuan.mapper.FeedbackMapper;
import com.shiyuan.mapper.UserMapper;
import com.shiyuan.model.Feedback;
import com.shiyuan.model.FeedbackExample;
import com.shiyuan.model.UserExample;
import com.shiyuan.util.JsonUtil;
import com.shiyuan.util.RandomUtil;

@Controller
@RequestMapping(produces = {"application/json;charset=UTF-8"})

public class FeedbackController {
	@Autowired
	FeedbackMapper feedbackMapper;
	FeedbackExample feedbackExample = new FeedbackExample();
	
	@ResponseBody
	@RequestMapping("fbSubmit")
	public String fbSubmit(@RequestBody Map<String, String> table) {
		Feedback feedback = new Feedback();
	
		String userId = table.get("userId").toString();
		String feedbackType = table.get("feedbackType").toString();
		String feedbackContent = table.get("feedbackContent").toString();
		
		feedback.setFeedbackId(Long.valueOf(RandomUtil.randomNumber(16)));
		feedback.setUserId(Long.valueOf(userId));
		feedback.setFeedbackType(feedbackType);
		feedback.setFeedbackContent(feedbackContent);
		
		Date time = new Date();
		// SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
		feedback.setFeedbackDate(time);
		
		String isSuccess = "";
		if (feedbackMapper.insert(feedback) != 0)
			isSuccess = "提交成功";
		else
			isSuccess = "提交失败";
		
		return JsonUtil.basicError(0, isSuccess);
	}
	
	
}
