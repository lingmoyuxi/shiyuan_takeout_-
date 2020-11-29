package com.shiyuan.controller.entity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.shiyuan.mapper.FeedbackMapper;
import com.shiyuan.mapper.UserMapper;
import com.shiyuan.model.Feedback;
import com.shiyuan.model.FeedbackExample;
import com.shiyuan.model.UserExample;
import com.shiyuan.util.FileUtil;
import com.shiyuan.util.JsonUtil;
import com.shiyuan.util.RandomUtil;

@Controller
@RequestMapping(produces = { "application/json;charset=UTF-8" })

public class FeedbackController {
	@Autowired
	FeedbackMapper feedbackMapper;
	FeedbackExample feedbackExample = new FeedbackExample();

	/**
	 * -用户提交反馈- 服务器收到用户反馈生成的随机反馈id 用户id 用户反馈类型 用户反馈内容 反馈提交生成的时间 用户图片的提交
	 **/
	// , @RequestBody Map<String, String> table
	// , @RequestParam("files") MultipartFile[] files
	@ResponseBody
	@RequestMapping("fbSubmit")
	public String fbSubmit(HttpServletRequest request) {
		Feedback feedback = new Feedback();

		String userId = request.getParameter("userId");
		String feedbackType = request.getParameter("feedbackType");
		String feedbackContent = request.getParameter("feedbackContent");

		feedback.setFeedbackId(Long.valueOf(RandomUtil.randomNumber(16)));
		feedback.setUserId(Long.valueOf(userId));
		feedback.setFeedbackType(feedbackType);
		feedback.setFeedbackContent(feedbackContent);

		Date time = new Date();
		// SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		feedback.setFeedbackDate(time);

		/**
		 * 用户反馈图片的提交
		 */

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		Iterator<String> fileNames = multipartRequest.getFileNames();
		// 创建一个图片路径数组
		StringBuilder uploadpic = new StringBuilder();
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
			System.out.println(entry.getValue().getOriginalFilename());
		}

		if (fileNames.hasNext()) {
			// request.getFiles(fileName)通过fileName这个Key，获得文件集合列表
			List<MultipartFile> fileList = multipartRequest.getFiles("images");
			if (fileList.size() > 0) {
				// 遍历文件列表
				Iterator<MultipartFile> fileIte = fileList.iterator();
				int i = 0;
				while (fileIte.hasNext()) {
					// 获得每一个文件
					MultipartFile eachFile = fileIte.next();
					try {
						// 格式化文件名 0.xxx 1.xxx 2.xxx
						System.out.println(eachFile.getOriginalFilename());
						String[] split = eachFile.getOriginalFilename().split("\\.");

						String filename = i + "." + split[split.length - 1];

						// 文件保存路径
						// 格式：路径 + 用户id + 用户反馈生成id
						String filePath = "resource/image/feedback/" + userId + "/"
								+ feedback.getFeedbackId().toString() + "/" + filename;

						// 文件缓存
						final File tempfile = FileUtil
								.creatFileAndDir(request.getServletContext().getRealPath("/WEB-INF/" + filePath));

						// 转存文件
						final File targetfile = new File(FileUtil.getPath(request) + filePath);

						// 首先保存到缓存目录，接着再保存到真正的文件目录中 
						eachFile.transferTo(tempfile);
						FileUtil.copyFile(tempfile, targetfile);

						// 删除缓存的文件 
						FileUtil.deleteFile(tempfile);

						// 数组添加文件路径
						uploadpic.append(",");
						uploadpic.append("image/feedback/" + userId + "/" + feedback.getFeedbackId().toString() + "/"
								+ filename);

						System.out.println(tempfile.getPath());
						i++;
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		}

		// 设置反馈图片路径
		feedback.setFeedbackImage(uploadpic.toString());

		String isSuccess = "";
		int status = 0;
		if (feedbackMapper.insert(feedback) != 0)
			isSuccess = "提交成功";
		else {
			status = 1;
			isSuccess = "提交失败";
		}

		return JsonUtil.basicError(status, isSuccess);
	}

	/**
	 * -用户查询历史反馈记录- 用户id 反馈生成id 反馈类型 反馈提交时间-
	 */
	@ResponseBody
	@RequestMapping("submitHistory")
	public String submitHistory(Long userId) {
		Feedback feedback = new Feedback();
		// 获取所有信息，再逐个筛选
		List<Feedback> fbList = feedbackMapper.selectByuserId(userId);
		// System.out.println(JsonUtil.basic(fbList));
		// return JsonUtil.basic(JsonUtil.toJson(fbList));

		return JsonUtil.basic(fbList);
	}

}
