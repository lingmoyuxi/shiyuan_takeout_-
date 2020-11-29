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

		System.out.println("�ܹ��ж���" + fileMap.size() + "��");
		// �жϽ��յ��ļ��ǲ��ǿգ��Ǿ����������Ǿ�ȡ��
		if (fileMap.size() > 0) {
			// ·����������������
			uploadpic.append("[");
			int s = 0;
			// ��������ļ�
			for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
				System.out.println(entry.getKey()); // �ļ��ؼ���
				System.out.println(entry.getValue()); // �ļ���
				System.out.println(entry.getValue().getOriginalFilename()); // �ļ�ԭʼ�ļ���
				// ��ȡ�ļ����ؼ���
				String fileKeyString = entry.getKey();

				// ��ȡ�ļ�
				MultipartFile tmpFile = entry.getValue();
				try {
					// ��ʽ���ļ���
					System.out.println("����ǰ���ļ�����" + tmpFile.getOriginalFilename()); // ����ǰ���ļ���
					String[] split = tmpFile.getOriginalFilename().split("\\.");
					String filenameFormat = s + "." + split[split.length - 1];
					System.out.println("��������ļ�����" + filenameFormat);

					String filePath = "resource/image/feedback/" + userId + "/" + feedback.getFeedbackId().toString() + "/"
							+ filenameFormat;

					// �ļ�����
					final File tempfile = FileUtil
							.creatFileAndDir(request.getServletContext().getRealPath("/WEB-INF/" + filePath));

					// ת���ļ�
					final File targetfile = new File(FileUtil.getPath(request) + filePath);

					// ���ȱ��浽����Ŀ¼�������ٱ��浽�������ļ�Ŀ¼��
					tmpFile.transferTo(tempfile);
					FileUtil.copyFile(tempfile, targetfile);

					// ɾ��������ļ�
					FileUtil.deleteFile(tempfile);

					// ��������ļ�·��
					// �жϣ����Ϊ��0��ʱ������Ҫ���","
					if (s != 0)
						uploadpic.append(",");
					uploadpic.append(
							"\"image/feedback/" + userId + "/" + feedback.getFeedbackId().toString() + "/" + filenameFormat + "\"");

					System.out.println(tempfile.getPath());
					s++;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}
			// ���ĩβ�������
			uploadpic.append("]");
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
