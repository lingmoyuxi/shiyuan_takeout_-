package com.shiyuan.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

//import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.shiyuan.mapper.UserMapper;
import com.shiyuan.model.User;
import com.shiyuan.model.UserExample;
import com.shiyuan.util.FileUtil;
import com.shiyuan.util.JsonUtil;

//������
@CrossOrigin
@Controller
@RequestMapping(produces = { "application/json;charset=UTF-8" })
public class MainConllect {
	@Autowired
	UserMapper userMapper;
	UserExample userExample = new UserExample();

	/**
	 * �ϴ��ļ� �����ļ�����ʾ��
	 * 
	 * @param file �ļ�
	 * @return ���ļ���Ϣ
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@PostMapping("upload")
	public String fileUpload(HttpServletRequest request, @RequestParam("upfile") CommonsMultipartFile file)
			throws IllegalStateException, IOException {

		String filetypeString = FileUtil.getFileTypePath(file);

		// ���ݿ��¼���ڷ��ʵ��ļ�·��
		String filepathString = filetypeString + file.getOriginalFilename(); // �洢�ļ�·�����ļ���
		// ���ر�����ļ�·��
		String tempPath = request.getServletContext().getRealPath("/WEB-INF/") + filepathString; // ��������ʱ����Ŀ¼��ַ
																									// �������ڴ��Ա���ʵʱ������Դ��
		final String projectPath = FileUtil.getPath(request) + filepathString; // ��Ŀ����Ŀ¼��ַ �������ڴ��Ա��ܷ�����Դ����һ������

		// �ļ�1�洢�ڷ���������Ŀ¼��
		final File f = FileUtil.creatFileAndDir(tempPath);
		file.transferTo(f);
		//���߳̿����ļ�
		File f2 = new File(projectPath);
		FileUtil.copyFile(f, f2); // File,File д��1
//		FileUtil.copyFile(f, projectPath);// File,String д�� 2
		//���ؽ����ļ�����Ϣ
		return JsonUtil.basic("{\"fileType\":\"" + file.getContentType() + "\",\"fileSize\":" + file.getSize()
				+ ",\"filePath\":\"" + "resource/" + filetypeString + file.getOriginalFilename() + "\"}");
	}

	/**
	 * �ϴ��û�ͷ�� ʾ���� ������ʵ����;
	 * 
	 * @param file         �ļ�
	 * @param userid       �û�id
	 * @param userpassword �û�����
	 * @return �û�����
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@PostMapping("uploadicon")
	public String fileUpload(HttpServletRequest request, @RequestParam("icon") CommonsMultipartFile file, Long userid,
			String userpassword) throws IllegalStateException, IOException {
//		userExample = new UserExample();
//		userExample.createCriteria().andUserIdEqualTo(userid).andUserPasswordEqualTo(userpassword);
//		List<User> result = userMapper.selectByExample(userExample);
//		if (result.isEmpty()) {
//			return JsonUtil.basicError(1,"�Ҳ����û�����ȷ��id��password�Ƿ���ȷ");
//		} else {

		//�����ļ���  һ�û�һͷ���Ƽ�ʹ�� userId.png ��ʽ
//			String filename = new Date().getTime() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
		//file.getOriginalFilename() ��ȡ���ǽ��յ��ļ���  ���� dsadsads.png  ʹ��String.split()  �� .  �ָ��ȡ�ļ���׺  ��ϳ� �� userId.png ��ʽ
		String[] split = file.getOriginalFilename().split("\\.");
		String filename = new Date().getTime() + "." + split[split.length - 1];  
		
		User user = new User();
		user.setUserIcon("resource/image/usericon/" + filename);
//			userMapper.updateByPrimaryKey(user);

//		String filepathString = "resource/image/usericon/" + filename;
//		String tempPath = request.getServletContext().getRealPath("/WEB-INF/") + filepathString;
//		final String projectPath = FileUtil.getPath(request) + filepathString;
//
//		final File f = FileUtil.creatFileAndDir(tempPath);
//		file.transferTo(f);
//		FileUtil.copyFile(f, projectPath);// File,String д�� 2		
		return JsonUtil.basic(user);
//		}
	}

}
