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
	 * -�û��ύ����- �������յ��û��������ɵ��������id �û�id �û��������� �û��������� �����ύ���ɵ�ʱ�� �û�ͼƬ���ύ
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
		 * �û�����ͼƬ���ύ
		 */

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		Iterator<String> fileNames = multipartRequest.getFileNames();
		// ����һ��ͼƬ·������
		StringBuilder uploadpic = new StringBuilder();
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
			System.out.println(entry.getValue().getOriginalFilename());
		}

		if (fileNames.hasNext()) {
			// request.getFiles(fileName)ͨ��fileName���Key������ļ������б�
			List<MultipartFile> fileList = multipartRequest.getFiles("images");
			if (fileList.size() > 0) {
				// �����ļ��б�
				Iterator<MultipartFile> fileIte = fileList.iterator();
				int i = 0;
				while (fileIte.hasNext()) {
					// ���ÿһ���ļ�
					MultipartFile eachFile = fileIte.next();
					try {
						// ��ʽ���ļ��� 0.xxx 1.xxx 2.xxx
						System.out.println(eachFile.getOriginalFilename());
						String[] split = eachFile.getOriginalFilename().split("\\.");

						String filename = i + "." + split[split.length - 1];

						// �ļ�����·��
						// ��ʽ��·�� + �û�id + �û���������id
						String filePath = "resource/image/feedback/" + userId + "/"
								+ feedback.getFeedbackId().toString() + "/" + filename;

						// �ļ�����
						final File tempfile = FileUtil
								.creatFileAndDir(request.getServletContext().getRealPath("/WEB-INF/" + filePath));

						// ת���ļ�
						final File targetfile = new File(FileUtil.getPath(request) + filePath);

						// ���ȱ��浽����Ŀ¼�������ٱ��浽�������ļ�Ŀ¼�� 
						eachFile.transferTo(tempfile);
						FileUtil.copyFile(tempfile, targetfile);

						// ɾ��������ļ� 
						FileUtil.deleteFile(tempfile);

						// ��������ļ�·��
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

		// ���÷���ͼƬ·��
		feedback.setFeedbackImage(uploadpic.toString());

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

	/**
	 * -�û���ѯ��ʷ������¼- �û�id ��������id �������� �����ύʱ��-
	 */
	@ResponseBody
	@RequestMapping("submitHistory")
	public String submitHistory(Long userId) {
		Feedback feedback = new Feedback();
		// ��ȡ������Ϣ�������ɸѡ
		List<Feedback> fbList = feedbackMapper.selectByuserId(userId);
		// System.out.println(JsonUtil.basic(fbList));
		// return JsonUtil.basic(JsonUtil.toJson(fbList));

		return JsonUtil.basic(fbList);
	}

}
