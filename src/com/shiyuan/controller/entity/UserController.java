package com.shiyuan.controller.entity;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.shiyuan.mapper.UserMapper;
import com.shiyuan.model.User;
import com.shiyuan.model.UserExample;
import com.shiyuan.model.UserExample.Criteria;
import com.shiyuan.util.FileUtil;
import com.shiyuan.util.JsonUtil;
import com.shiyuan.util.RandomUtil;

//������
@CrossOrigin
@Controller
@RequestMapping(produces = {"application/json;charset=UTF-8"})
public class UserController {
	@Autowired
	UserMapper userMapper;
	UserExample userExample = new UserExample();
	
	@ResponseBody
	@RequestMapping("register")
	public String register(@RequestBody User user) {
		Long userAccount = user.getUserAccount();
		String userPassword = user.getUserPassword();
		System.out.println(userAccount + "-" + userPassword );
		if (userAccount == null || userAccount.toString().length() > 11 || userAccount.toString().length() == 0) {
			return JsonUtil.basicError(2,"length --- 0 < userAccount < 12   userAccount is " + userAccount);
		}
		if (userPassword == null || userPassword.isEmpty() || userPassword.length() > 16 || userPassword.length() == 0) {
			return JsonUtil.basicError(2,"length --- 0 < userPassword < 16  userPassword is " + userPassword);
		}
		Criteria criteria = userExample.createCriteria();
		criteria.andUserAccountEqualTo(userAccount);
		if (!userMapper.selectByExample(userExample).isEmpty()) {
			return JsonUtil.basicError(2, "�û��Ѵ���");
		}
		return createUser(user.getUserName(), user.getUserAccount(), user.getUserPassword(), user.getUserIcon());
	}
	
	@ResponseBody
	@RequestMapping(value = "login")
	public String login(@RequestBody Map<String, String> person) {
		userExample.clear();
		if (person == null ||person.isEmpty()) {
			System.out.println("δ���ܵ�����");
			return JsonUtil.basicError(1, "δ���ܵ�����");
		}
		String userAccount = person.get("userAccount").toString();
		String userPassword = person.get("userPassword").toString();
		System.out.println(userAccount + "-" + userPassword );
		if (userAccount == null || userAccount.toString().length() > 11 || userAccount.toString().length() == 0) {
			return JsonUtil.basicError(2,"length --- 0 < userAccount < 12   userAccount is " + userAccount);
		}
		if (userPassword == null || userPassword.isEmpty() || userPassword.length() > 16 || userPassword.length() == 0) {
			return JsonUtil.basicError(2,"length --- 0 < userPassword < 17  userPassword is " + userPassword);
		}
		Criteria criteria = userExample.createCriteria();
		criteria.andUserAccountEqualTo(Long.valueOf(userAccount));
		if (userMapper.selectByExample(userExample).isEmpty()) {
			return JsonUtil.basicError(1, "�û�������");
		}
		criteria.andUserPasswordEqualTo(userPassword);
		List<User> selectByExample = userMapper.selectByExample(userExample);
		return selectByExample.isEmpty()?JsonUtil.basicError(1, "�û��������벻��ȷ") : JsonUtil.basic(selectByExample.get(0));
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
		userExample = new UserExample();
		userExample.createCriteria().andUserIdEqualTo(userid).andUserPasswordEqualTo(userpassword);
		List<User> result = userMapper.selectByExample(userExample);
		if (result.isEmpty()) {
			return JsonUtil.basicError(1,"�Ҳ����û�����ȷ��id��password�Ƿ���ȷ");
		} else {

		//�����ļ���  һ�û�һͷ���Ƽ�ʹ�� userId.png ��ʽ
//			String filename = new Date().getTime() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
		//file.getOriginalFilename() ��ȡ���ǽ��յ��ļ���  ���� dsadsads.png  ʹ��String.split()  �� .  �ָ��ȡ�ļ���׺  ��ϳ� �� userId.png ��ʽ
		String[] split = file.getOriginalFilename().split("\\.");
		String filename = new Date().getTime() + "." + split[split.length - 1];  
//		String filename = userid + "." + split[split.length - 1];  
		
		User user = result.get(0);
		String  originalFilePath = "resource/" + user.getUserIcon();
		user.setUserIcon("image/usericon/" + filename);
		userMapper.updateByPrimaryKey(user);

		String filepathString = "resource/image/usericon/" + filename;
		String tempPath = request.getServletContext().getRealPath("/WEB-INF/") + filepathString;
		final String projectPath = FileUtil.getPath(request) + filepathString;
		
		//���ļ�
		String orginalFileFillPath = request.getServletContext().getRealPath("/WEB-INF/") + originalFilePath;
		final String orginalFileFillProjectPath = FileUtil.getPath(request) + originalFilePath;
		
		final File f = FileUtil.creatFileAndDir(tempPath);
		file.transferTo(f);
		FileUtil.copyFile(f, projectPath);// File,String д�� 2	
		//ɾ�����ļ�
		FileUtil.deleteFile(orginalFileFillPath);
		FileUtil.deleteFile(orginalFileFillProjectPath);
		return JsonUtil.basic(user);
		}
	}
	
	/**
	 * �����û�����
	 * @param person �������� userId ��userPassword
	 * @return ���û�����
	 */
	@ResponseBody
	@PostMapping("changepassword")
	public String changePassword(@RequestBody Map<String, String> person) {
		String userId = person.get("userId").toString();
		String userPassword = person.get("userPassword").toString();
		System.out.println(String.format("�û�   %s  ���Ը�������Ϊ  %s",userId,userPassword));
		if (userPassword == null || userPassword.isEmpty() || userPassword.length() > 16 || userPassword.length() == 0) {
			return JsonUtil.basicError(2,"length --- 0 < userPassword < 17  userPassword is " + userPassword);
		}
		userExample = new UserExample();
		userExample.createCriteria().andUserIdEqualTo(Long.valueOf(userId));
		List<User> result = userMapper.selectByExample(userExample);
		if (result.isEmpty()) {
			return JsonUtil.basicError(1,"�Ҳ����û�");
		} else {
			User user2 = result.get(0);
			if (userPassword.equals(user2.getUserPassword())) {
				return JsonUtil.basic(0,user2,"�����Ѹ���(��������ԭ����һ��)");
			}
			user2.setUserPassword(userPassword);
			
			int i = userMapper.updateByPrimaryKey(user2);
			return i > 0 ? JsonUtil.basic(user2):JsonUtil.basicError(3, "���ݸ����쳣");
		}
	}
	
	/**
	 * �����û��ǳ�
	 * @param person
	 * @return
	 */
	@ResponseBody
	@PostMapping("changename")
	public String changeName(@RequestBody Map<String, String> person) {
		String userId = person.get("userId").toString();
		String userName = person.get("userName").toString();
		System.out.println(String.format("�û�   %s  ���Ը����ǳ�Ϊ  %s",userId,userName));
		userExample = new UserExample();
		userExample.createCriteria().andUserIdEqualTo(Long.valueOf(userId));
		List<User> result = userMapper.selectByExample(userExample);
		if (result.isEmpty()) {
			return JsonUtil.basicError(1,"�Ҳ����û�");
		} else {
			userExample.clear();
			userExample.createCriteria().andUserNameEqualTo(userName).andUserIdNotEqualTo(Long.valueOf(userId));
			List<User> result2 = userMapper.selectByExample(userExample);
			if (!result2.isEmpty()) {
				return JsonUtil.basicError(2,"�ǳ��Ѵ���");
			} 
			User user2 = result.get(0);
			if (userName.equals(user2.getUserName())) {
				return JsonUtil.basic(0,user2,"�ǳ��Ѹ���(���ǳ���ԭ�ǳ�һ��)");
			}
			user2.setUserName(userName);
			
			int i = userMapper.updateByPrimaryKey(user2);
			return i > 0 ? JsonUtil.basic(user2):JsonUtil.basicError(3, "���ݸ����쳣");
		}
	}
	
	/**
	 * �����û��ֻ���
	 * @param person
	 * @return
	 */
	@ResponseBody
	@PostMapping("changephone")
	public String changePhone(@RequestBody Map<String, String> person) {
		String userId = person.get("userId").toString();
		String userPhoneKey = person.get("userPhoneKey").toString();
		System.out.println(String.format("�û�   %s  ���Ը����ֻ���Ϊ  %s",userId,userPhoneKey));
		if (userPhoneKey == null || userPhoneKey.length() != 11 ) {
			return JsonUtil.basicError(2,"�ֻ�����Ч���� ---" + userPhoneKey);
		}
		userExample = new UserExample();
		userExample.createCriteria().andUserIdEqualTo(Long.valueOf(userId));
		List<User> result = userMapper.selectByExample(userExample);
		if (result.isEmpty()) {
			return JsonUtil.basicError(1,"�Ҳ����û�");
		} else {
			userExample.clear();
			userExample.createCriteria().andUserPhoneKeyEqualTo(Long.valueOf(userPhoneKey)).andUserIdNotEqualTo(Long.valueOf(userId));
			List<User> result2 = userMapper.selectByExample(userExample);
			if (!result2.isEmpty()) {
				return JsonUtil.basicError(2,"�ֻ����Ѵ���");
			} 
			User user2 = result.get(0);
			if (Long.valueOf(userPhoneKey) == user2.getUserPhoneKey()) {
				return JsonUtil.basic(0,user2,"�ֻ����Ѹ���(���ֻ�����ԭ�ֻ���һ��)");
			}
			user2.setUserPhoneKey(Long.valueOf(userPhoneKey));
			
			int i = userMapper.updateByPrimaryKey(user2);
			return i > 0 ? JsonUtil.basic(user2):JsonUtil.basicError(3, "���ݸ����쳣");
		}
	}
	
	
	/**
	 * ��ȡ�����û�
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("getusers")
	public String getUsers() {
		return JsonUtil.basic(userMapper.selectByExample(null));
	}
	
	/**
	 * ����û�
	 * @param user �û�
	 * @return
	 */
	@ResponseBody
	@RequestMapping("adduser")
	public String addUser(@RequestBody User user) {
		return createUser(user.getUserName(), user.getUserAccount(), user.getUserPassword(), user.getUserIcon());
//		int insert = userMapper.insert(user);
//		return insert == 1?JsonUtil.basic(0, user, null):JsonUtil.basic(-1,null ,"���ʧ��  --   Class UserController|method addUser");
	}
	
	
	
	
	
	
	public String createUser(String name,Long account,String password,String icon) {
		if (name == null ||name.isEmpty() ||name.length() == 0) {
			name = String.format("ʳԷ%d", RandomUtil.randomNumber(6));
		}
		if (account == null || account.toString().length() > 11 || account.toString().length() == 0) {
			return JsonUtil.basicError(2,"length --- 0 < userAccount < 12");
		}
		if (password == null || password.isEmpty() || password.length() > 16 || password.length() == 0) {
			return JsonUtil.basicError(2,"length --- 0 < userPassword < 16");
		}
		User user = new User();
		user.setUserId(RandomUtil.randomNumber(16));
		user.setUserName(name);
		user.setUserAccount(account);
		user.setUserPassword(password);
		user.setUserIcon("http://localhost:8080/background-server/image/usericon/1606212168222.79059953_p0.png");
//		user.setUserIcon(icon);
		int insert = userMapper.insert(user);
		return JsonUtil.basic(0, insert == 1?user:null, null);
	}
	
}
