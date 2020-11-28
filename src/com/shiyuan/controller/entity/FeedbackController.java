package com.shiyuan.controller.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
	
	/** -�û��ύ����-
	 *  �������յ��û��������ɵ��������id
	 *  �û�id
	 *  �û���������
	 *  �û���������
	 *  �����ύ���ɵ�ʱ��**/
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
		int status = 0;
		if (feedbackMapper.insert(feedback) != 0)
			isSuccess = "�ύ�ɹ�";
		else {
			status = 1;
			isSuccess = "�ύʧ��";
		}
		
		return JsonUtil.basicError(status, isSuccess);
	}
	
	/** -�û���ѯ��ʷ������¼- 
	 *  �û�id
	 *  ��������id
	 *  ��������
	 *  �����ύʱ��
	 */
	@ResponseBody
	@RequestMapping("submitHistory")
	public String submitHistory(Long userId) {
		Feedback feedback = new Feedback();
		// ��ȡ������Ϣ�������ɸѡ
		List<Feedback> fbList = feedbackMapper.selectByuserId(userId);
		// System.out.println(JsonUtil.basic(fbList));
		// return JsonUtil.basic(JsonUtil.toJson(fbList));
		
		//toJson() �����ǽ������Ի�ת����Json���ݣ�basic()������basic()�������Լ�����toJson()��������
		//return JsonUtil.basic(fbList); // ����ֱ����basic���������                             basicErrorӦ�������������ģ�
		
		return JsonUtil.basic(fbList);
	}
	
}
