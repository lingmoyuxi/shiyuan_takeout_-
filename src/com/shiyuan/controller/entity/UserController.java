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

import com.shiyuan.mapper.ThirdloginMapper;
import com.shiyuan.mapper.UserMapper;
import com.shiyuan.model.Thirdlogin;
import com.shiyuan.model.ThirdloginExample;
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
	
	@Autowired
	ThirdloginMapper thirdLoginMapper;
	ThirdloginExample thirdloginExample = new ThirdloginExample();
	
	
	/**
	 * �û�ע�� 
	 * @param user  userAccount�˺�  userPassword����
	 * @return
	 */
	@ResponseBody
	@RequestMapping("register")
	public String register(@RequestBody Map<String, String> person) {
		String accountString = person.get("userAccount").toString();
		Long userAccount = Long.valueOf(accountString);
		String userPassword = person.get("userPassword").toString();
		System.out.println("�û�ע��  account-" + userAccount + "     password-" + userPassword );
		if (userAccount == null || userAccount.toString().length() > 11 || userAccount.toString().length() == 0) {
			return JsonUtil.basicError(2,"length --- 0 < userAccount < 12   userAccount is " + userAccount);
		}
		if (userPassword == null || userPassword.isEmpty() || userPassword.length() > 16 || userPassword.length() == 0) {
			return JsonUtil.basicError(2,"length --- 0 < userPassword < 16  userPassword is " + userPassword);
		}
		userExample.clear();
		Criteria criteria = userExample.createCriteria();
		criteria.andUserAccountEqualTo(userAccount);
		if (!userMapper.selectByExample(userExample).isEmpty()) {
			return JsonUtil.basicError(1, "�û��Ѵ���");
		}
		User user = newUser(userAccount, userPassword);
		int insert = userMapper.insert(user);
		return insert==1?JsonUtil.basic(user):JsonUtil.basicError(3, "����û�ʧ��");
	}
	
	/**
	 * QQע��
	 * @param person  userName  userQqKey
	 * @return
	 */
	@ResponseBody
	@RequestMapping("registerqq")
	public String registerQQ(@RequestBody Map<String, String> person) {
		String userName = person.get("userName").toString();
		String userQqKeyString = person.get("userQqKey");
		if (userQqKeyString == null || userQqKeyString.isEmpty()) {
			return JsonUtil.basicError(2,"��QQKey  userQqKeyString is " + userQqKeyString);
		}
		String userQqKey = userQqKeyString.toString();
		
		System.out.println("�û�QQע��  userName-" + userName + "     userQqKey-" + userQqKey );
		
		userExample.clear();
		userExample.createCriteria().andUserQqKeyEqualTo(userQqKey);
		List<User> users = userMapper.selectByExample(userExample);
		if (users.isEmpty()) {  //δ����
			User user = newUser(userName,RandomUtil.randomNumber(11), RandomUtil.randomString(16));
			user.setUserQqKey(userQqKey);
			
			Thirdlogin thirdlogin = new Thirdlogin();
			thirdlogin.setThirdloginId(RandomUtil.randomNumber(16));
			thirdlogin.setThirdloginKey(userQqKey);
			thirdlogin.setUserId(user.getUserId());
			int i = thirdLoginMapper.insert(thirdlogin);
			if (i!=1) {
				return JsonUtil.basicError(3,"δ��¼QQKey������ͼ����������ʧ��");
			}
			int insert = userMapper.insert(user);
			return insert == 1?JsonUtil.basic(user):JsonUtil.basicError(3, "������ʼ���û�ʧ��");
		} else {
			return JsonUtil.basicError(2,"��QQ�Ѿ����󶨹����޷�����ע�� ");
		}
	}
	
	/**
	 * ΢��ע��
	 * @param person  userName  userQqKey
	 * @return
	 */
	@ResponseBody
	@RequestMapping("registerwx")
	public String registerwx(@RequestBody Map<String, String> person) {
		String userName = person.get("userName").toString();
		String userWeixinKeyString = person.get("userWeixinKey");
		if (userWeixinKeyString == null || userWeixinKeyString.isEmpty()) {
			return JsonUtil.basicError(2,"��΢��Key  userWeixinKey is " + userWeixinKeyString);
		}
		String userWeixinKey = userWeixinKeyString.toString();
		
		System.out.println("�û�΢��ע��  userName-" + userName + "     userQqKey-" + userWeixinKey );
		
		userExample.clear();
		userExample.createCriteria().andUserWeixinKeyEqualTo(userWeixinKey);
		List<User> users = userMapper.selectByExample(userExample);
		if (users.isEmpty()) {  //δ����
			User user = newUser(userName,RandomUtil.randomNumber(11), RandomUtil.randomString(16));
			user.setUserWeixinKey(userWeixinKey);
			
			Thirdlogin thirdlogin = new Thirdlogin();
			thirdlogin.setThirdloginId(RandomUtil.randomNumber(16));
			thirdlogin.setThirdloginKey(userWeixinKey);
			thirdlogin.setUserId(user.getUserId());
			int i = thirdLoginMapper.insert(thirdlogin);
			if (i!=1) {
				return JsonUtil.basicError(3,"δ��¼΢��Key������ͼ����������ʧ��");
			}
			int insert = userMapper.insert(user);
			return insert == 1?JsonUtil.basic(user):JsonUtil.basicError(3, "������ʼ���û�ʧ��");
		} else {
			return JsonUtil.basicError(2,"��΢���Ѿ����󶨹����޷�����ע�� ");
		}
	}
	
	/**
	 * �û���½
	 * @param person   userAccount�˺�  userPassword����
	 * @return
	 */
	@ResponseBody
	@RequestMapping("login")
	public String login(@RequestBody Map<String, String> person) {
		userExample.clear();
		if (person == null ||person.isEmpty()) {
			System.out.println("δ���ܵ�����");
			return JsonUtil.basicError(1, "δ���ܵ�����");
		}
		String userAccount = person.get("userAccount").toString();
		String userPassword = person.get("userPassword").toString();
		System.out.println("�û���½  account-" + userAccount + "     password-" + userPassword );
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
	 * QQ��½
	 * @param person userQqKey
	 * @return
	 */
	@ResponseBody
	@RequestMapping("loginqq")
	public String loginqq(@RequestBody Map<String, String> person) {
		if (person == null ||person.isEmpty()) {
			System.out.println("δ���ܵ�����");
			return JsonUtil.basicError(1, "δ���ܵ�����");
		}
		
		String userQqKey = person.get("userQqKey").toString();
		
		System.out.println("�û�QQ��½  qqKey-" + userQqKey);
		if (userQqKey == null || userQqKey.length() == 0) {
			return JsonUtil.basicError(2,"userQqKey is " + userQqKey);
		}
		thirdloginExample.clear();
		thirdloginExample.createCriteria().andThirdloginKeyEqualTo(userQqKey);
		List<Thirdlogin> thirdlogins = thirdLoginMapper.selectByExample(thirdloginExample);
		if (thirdlogins == null|| thirdlogins.size() == 0) {
			User createUser = newUser(RandomUtil.randomNumber(11), RandomUtil.randomString(16));
			createUser.setUserQqKey(userQqKey);
			
			Thirdlogin thirdlogin = new Thirdlogin();
			thirdlogin.setThirdloginId(RandomUtil.randomNumber(16));
			thirdlogin.setThirdloginKey(userQqKey);
			thirdlogin.setUserId(createUser.getUserId());
			int i = thirdLoginMapper.insert(thirdlogin);
			if (i!=1) {
				return JsonUtil.basicError(3,"δ��¼QQKey������ͼ����������ʧ��");
			}
			int insert = userMapper.insert(createUser);
			return insert==1?JsonUtil.basic(createUser):JsonUtil.basicError(3,"δ��¼QQKey������ͼ������ʼ���û�ʧ��");
		} else {
			userExample.clear();
			userExample.createCriteria().andUserIdEqualTo(thirdlogins.get(0).getUserId()).andUserQqKeyEqualTo(userQqKey);
			List<User> users = userMapper.selectByExample(userExample);
			return users.isEmpty()?JsonUtil.basicError(1, "�û�������"):JsonUtil.basic(users.get(0));
		}
	}
	
	/**
	 * ΢�ŵ�½
	 * @param person userWeixinKey
	 * @return
	 */
	@ResponseBody
	@RequestMapping("loginwx")
	public String loginwx(@RequestBody Map<String, String> person) {
		if (person == null ||person.isEmpty()) {
			System.out.println("δ���ܵ�����");
			return JsonUtil.basicError(1, "δ���ܵ�����");
		}
		
		String userWeixinKey = person.get("userWeixinKey").toString();
		
		System.out.println("�û�΢�ŵ�½  wxKey-" + userWeixinKey);
		if (userWeixinKey == null || userWeixinKey.length() == 0) {
			return JsonUtil.basicError(2,"userQqKey is " + userWeixinKey);
		}
		thirdloginExample.clear();
		thirdloginExample.createCriteria().andThirdloginKeyEqualTo(userWeixinKey);
		List<Thirdlogin> thirdlogins = thirdLoginMapper.selectByExample(thirdloginExample);
		if (thirdlogins == null|| thirdlogins.size() == 0) {
			User createUser = newUser(RandomUtil.randomNumber(11), RandomUtil.randomString(16));
			createUser.setUserWeixinKey(userWeixinKey);
			
			Thirdlogin thirdlogin = new Thirdlogin();
			thirdlogin.setThirdloginId(RandomUtil.randomNumber(16));
			thirdlogin.setThirdloginKey(userWeixinKey);
			thirdlogin.setUserId(createUser.getUserId());
			int i = thirdLoginMapper.insert(thirdlogin);
			if (i!=1) {
				return JsonUtil.basicError(3,"δ��¼wxKey������ͼ����������ʧ��");
			}
			int insert = userMapper.insert(createUser);
			return insert==1?JsonUtil.basic(createUser):JsonUtil.basicError(3,"δ��¼wxKey������ͼ������ʼ���û�ʧ��");
		} else {
			userExample.clear();
			userExample.createCriteria().andUserIdEqualTo(thirdlogins.get(0).getUserId()).andUserWeixinKeyEqualTo(userWeixinKey);
			List<User> users = userMapper.selectByExample(userExample);
			return users.isEmpty()?JsonUtil.basicError(1, "�û�������"):JsonUtil.basic(users.get(0));
		}
	}
	
	
	/**
	 * �����û�ͷ��
	 * 
	 * @param file         �ļ�
	 * @param userid       �û�id
	 * @param userpassword �û�����
	 * @return �û�����
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@PostMapping("changeicon")
	public String changeIcon(HttpServletRequest request, @RequestParam("icon") CommonsMultipartFile file, Long userid,
			String userpassword) throws IllegalStateException, IOException {
		System.out.println("�û�     " + userid + "  ����ͷ��");
		userExample = new UserExample();
		userExample.createCriteria().andUserIdEqualTo(userid).andUserPasswordEqualTo(userpassword);
		List<User> result = userMapper.selectByExample(userExample);
		if (result.isEmpty()) {
			System.out.println("�û�     " + userid + "  ����ͷ��   ---�û�������");
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
		

		user.setUserIcon("image/usericon/" + filename);
		userMapper.updateByPrimaryKey(user);
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
		String newPassword = person.get("newPassword").toString();
		System.out.println(String.format("�û�   %s  ���Ը�������Ϊ  %s",userId,newPassword));
		if (userPassword == null || userPassword.isEmpty() || userPassword.length() > 16 || userPassword.length() == 0) {
			return JsonUtil.basicError(2,"length --- 0 < userPassword < 17  userPassword is " + userPassword);
		}
		userExample.clear();
		userExample.createCriteria().andUserIdEqualTo(Long.valueOf(userId)).andUserPasswordEqualTo(userPassword);
		List<User> result = userMapper.selectByExample(userExample);
		if (result.isEmpty()) {
			System.out.println("�������");
			return JsonUtil.basicError(1,"���������");
		} else {
			User user2 = result.get(0);
			if (newPassword.equals(user2.getUserPassword())) {
				return JsonUtil.basic(0,user2,"�����Ѹ���(��������ԭ����һ��)");
			}
			user2.setUserPassword(newPassword);
			System.out.println("��������Ϊ" + newPassword);
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
	 * �û���QQ
	 * @param person  userId  userQqKey
	 * @return
	 */
	@ResponseBody
	@PostMapping("userbindqq")
	public String qqBind(@RequestBody Map<String, String> person) {
		String userId = person.get("userId").toString();
		String userQqKey = person.get("userQqKey").toString();
		System.out.println(String.format("�û�   %s  ���Ը��İ�QQ  %s",userId,userQqKey));
		userExample.clear();
		userExample.createCriteria().andUserIdEqualTo(Long.valueOf(userId));
		List<User> result = userMapper.selectByExample(userExample);
		if (result.isEmpty()) {
			return JsonUtil.basicError(1,"�Ҳ����û�");
		} else {
			User user = result.get(0);
			
			userExample.clear();
			userExample.createCriteria().andUserQqKeyEqualTo(userQqKey);
			List<User> users = userMapper.selectByExample(userExample);
			if (!(users == null|| users.isEmpty())) {
				for (User user2 : users) {
					if (!String.valueOf(user2.getUserId()).equals(String.valueOf(user.getUserId()))) {
						return JsonUtil.basicError(2, "��QQ�ѱ������˻�����");
					}
				}
				return JsonUtil.basic(0,user,"δ���ģ��¾�QQ��һ�£�");
			}
			
			String qqKeyString = user.getUserQqKey();
			if (qqKeyString == null || qqKeyString.isEmpty() || qqKeyString.length() == 0) {
				Thirdlogin thirdlogin = new Thirdlogin();
				thirdlogin.setThirdloginId(RandomUtil.randomNumber(16));
				thirdlogin.setThirdloginKey(userQqKey);
				thirdlogin.setUserId(user.getUserId());
				thirdLoginMapper.insert(thirdlogin);
			} else {
				thirdloginExample.clear();
				thirdloginExample.createCriteria().andUserIdEqualTo(user.getUserId()).andThirdloginKeyEqualTo(qqKeyString);
				List<Thirdlogin> selectByExample = thirdLoginMapper.selectByExample(thirdloginExample);
				Thirdlogin thirdlogin = selectByExample.get(0);
				thirdlogin.setThirdloginKey(userQqKey);
				thirdLoginMapper.updateByPrimaryKey(thirdlogin);
			}
			user.setUserQqKey(userQqKey);
			
			int i = userMapper.updateByPrimaryKey(user);
			return i > 0 ? JsonUtil.basic(user):JsonUtil.basicError(3, "���ݸ����쳣");
		}
	}
	
	
	/**
	 * �û���΢��
	 * @param person  userId  userWeixinKey
	 * @return
	 */
	@ResponseBody
	@PostMapping("userbindwx")
	public String wxBind(@RequestBody Map<String, String> person) {
		String userId = person.get("userId").toString();
		String userWeixinKey = person.get("userWeixinKey").toString();
		System.out.println(String.format("�û�   %s  ���Ը��İ�΢��  %s",userId,userWeixinKey));
		userExample.clear();
		userExample.createCriteria().andUserIdEqualTo(Long.valueOf(userId));
		List<User> result = userMapper.selectByExample(userExample);
		if (result.isEmpty()) {
			return JsonUtil.basicError(1,"�Ҳ����û�");
		} else {
			User user = result.get(0);
			
			userExample.clear();
			userExample.createCriteria().andUserWeixinKeyEqualTo(userWeixinKey);
			List<User> users = userMapper.selectByExample(userExample);
			System.out.println("ʹ�ø�wxKey���û�"+users.size());
			if (!(users == null|| users.isEmpty())) {
				for (User user2 : users) {
					System.out.println("ʹ�ø�wxKey���û�"+user2.getUserId());
					if (!String.valueOf(user2.getUserId()).equals(String.valueOf(user.getUserId()))) {
						System.out.println("�û��ȶ� "+user2.getUserId()+"--"+user.getUserId());
						return JsonUtil.basicError(2, "��΢���ѱ������˻�����");
					}
				}
				return JsonUtil.basic(0,user,"δ���ģ��¾�΢�Ű�һ�£�");
			}
			
			
			
			String wxKeyString = user.getUserWeixinKey();
			if (wxKeyString == null || wxKeyString.isEmpty() || wxKeyString.length() == 0) {
				Thirdlogin thirdlogin = new Thirdlogin();
				thirdlogin.setThirdloginId(RandomUtil.randomNumber(16));
				thirdlogin.setThirdloginKey(userWeixinKey);
				thirdlogin.setUserId(user.getUserId());
				thirdLoginMapper.insert(thirdlogin);
			} else {
				thirdloginExample.clear();
				thirdloginExample.createCriteria().andUserIdEqualTo(user.getUserId()).andThirdloginKeyEqualTo(wxKeyString);
				List<Thirdlogin> selectByExample = thirdLoginMapper.selectByExample(thirdloginExample);
				Thirdlogin thirdlogin = selectByExample.get(0);
				thirdlogin.setThirdloginKey(userWeixinKey);
				thirdLoginMapper.updateByPrimaryKey(thirdlogin);
			}
			user.setUserWeixinKey(userWeixinKey);
			
			int i = userMapper.updateByPrimaryKey(user);
			return i > 0 ? JsonUtil.basic(user):JsonUtil.basicError(3, "���ݸ����쳣");
		}
	}
	
	
	
	/**
	 * �û������QQ
	 * @param person  userId  userQqKey
	 * @return
	 */
	@ResponseBody
	@PostMapping("usernobindqq")
	public String qqNoBind(@RequestBody Map<String, String> person) {
		String userId = person.get("userId").toString();
		System.out.println(String.format("�û�   %s  ���Խ����QQ",userId));
		userExample.clear();
		userExample.createCriteria().andUserIdEqualTo(Long.valueOf(userId));
		List<User> result = userMapper.selectByExample(userExample);
		if (result.isEmpty()) {
			return JsonUtil.basicError(1,"�Ҳ����û�");
		} else {
			User user = result.get(0);
			String qqKeyString = user.getUserQqKey();
			thirdloginExample.clear();
			thirdloginExample.createCriteria().andUserIdEqualTo(user.getUserId()).andThirdloginKeyEqualTo(qqKeyString);
			List<Thirdlogin> selectByExample = thirdLoginMapper.selectByExample(thirdloginExample);
			Thirdlogin thirdlogin = selectByExample.get(0);
			thirdLoginMapper.deleteByPrimaryKey(thirdlogin.getThirdloginId());
			user.setUserQqKey(null);
			int i = userMapper.updateByPrimaryKey(user);
			return i > 0 ? JsonUtil.basic(user):JsonUtil.basicError(3, "���ݸ����쳣");
		}
	}
	
	
	/**
	 * �û���QQ
	 * @param person  userId  userWeixinKey
	 * @return
	 */
	@ResponseBody
	@PostMapping("usernobindwx")
	public String wxNoBind(@RequestBody Map<String, String> person) {
		String userId = person.get("userId").toString();
		System.out.println(String.format("�û�   %s  ���Խ����΢��",userId));
		userExample.clear();
		userExample.createCriteria().andUserIdEqualTo(Long.valueOf(userId));
		List<User> result = userMapper.selectByExample(userExample);
		if (result.isEmpty()) {
			return JsonUtil.basicError(1,"�Ҳ����û�");
		} else {
			User user = result.get(0);
			String wxKeyString = user.getUserWeixinKey();
			thirdloginExample.clear();
			thirdloginExample.createCriteria().andUserIdEqualTo(user.getUserId()).andThirdloginKeyEqualTo(wxKeyString);
			List<Thirdlogin> selectByExample = thirdLoginMapper.selectByExample(thirdloginExample);
			Thirdlogin thirdlogin = selectByExample.get(0);
			thirdLoginMapper.deleteByPrimaryKey(thirdlogin.getThirdloginId());
			user.setUserWeixinKey(null);
			
			int i = userMapper.updateByPrimaryKey(user);
			return i > 0 ? JsonUtil.basic(user):JsonUtil.basicError(3, "���ݸ����쳣");
		}
	}
	
	
	@ResponseBody
	@PostMapping("changedromroom")
	public String userDromRoom(@RequestBody Map<String, String> person) {
		String userId = person.get("userId").toString();
		String userDromRoom = person.get("userDromRoom").toString();
		System.out.println(String.format("�û�   %s  ���Ը�������¥��Ϊ %s",userId,userDromRoom));
		userExample.clear();
		userExample.createCriteria().andUserIdEqualTo(Long.valueOf(userId));
		List<User> result = userMapper.selectByExample(userExample);
		if (result.isEmpty()) {
			return JsonUtil.basicError(1,"�Ҳ����û�");
		} else {
			User user = result.get(0);
			user.setUserDromRoom(userDromRoom);
			int i = userMapper.updateByPrimaryKey(user);
			return i > 0 ? JsonUtil.basic(user):JsonUtil.basicError(3, "���ݸ����쳣");
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
	 * ��ȡ  �û� (������user�г�ȡ����)
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("getusersoflength")
	public String getUsers(int length) {
		return JsonUtil.basic(RandomUtil.randomList(userMapper.selectByExample(null), length));
	}
	
	/**
	 * ����û�
	 * @param user �û�
	 * @return
	 */
	@ResponseBody
	@RequestMapping("adduser")
	public String addUser(@RequestBody User user) {
		return createUser(user.getUserName(), user.getUserAccount(), user.getUserPassword());
//		int insert = userMapper.insert(user);
//		return insert == 1?JsonUtil.basic(0, user, null):JsonUtil.basic(-1,null ,"���ʧ��  --   Class UserController|method addUser");
	}
	
	
	
	
	
	
	public String createUser(String name,Long account,String password) {
		if (name == null ||name.isEmpty() ||name.length() == 0) {
			name = String.format("ʳԷ%d", RandomUtil.randomNumber(6));
		}
		if (account == null || account.toString().length() > 11 || account.toString().length() == 0) {
			return JsonUtil.basicError(2,"length --- 0 < userAccount < 12");
		}
		if (password == null || password.isEmpty() || password.length() > 16 || password.length() == 0) {
			return JsonUtil.basicError(2,"length --- 0 < userPassword < 16");
		}
		User user = newUser(name, account, password);
		int insert = userMapper.insert(user);
		return JsonUtil.basic(0, insert == 1?user:null, null);
	}
	
	public User newUser(String name,Long account,String password) {
		if (name == null ||name.isEmpty() ||name.length() == 0) {
			name = String.format("ʳԷ%d", RandomUtil.randomNumber(6));
		}
		User user = new User();
		user.setUserId(RandomUtil.randomNumber(16));
		user.setUserName(name);
		user.setUserAccount(account);
		user.setUserPassword(password);
		user.setUserIcon("/image/default/defaultIcon.jpeg");
		return user;
	}
	public User newUser(Long account,String password) {
		return newUser(null,account, password);
	}
}
