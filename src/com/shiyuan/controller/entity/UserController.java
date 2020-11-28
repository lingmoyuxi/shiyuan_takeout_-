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

//跨域传输
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
			return JsonUtil.basicError(2, "用户已存在");
		}
		return createUser(user.getUserName(), user.getUserAccount(), user.getUserPassword(), user.getUserIcon());
	}
	
	@ResponseBody
	@RequestMapping(value = "login")
	public String login(@RequestBody Map<String, String> person) {
		userExample.clear();
		if (person == null ||person.isEmpty()) {
			System.out.println("未接受到参数");
			return JsonUtil.basicError(1, "未接受到参数");
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
			return JsonUtil.basicError(1, "用户不存在");
		}
		criteria.andUserPasswordEqualTo(userPassword);
		List<User> selectByExample = userMapper.selectByExample(userExample);
		return selectByExample.isEmpty()?JsonUtil.basicError(1, "用户名或密码不正确") : JsonUtil.basic(selectByExample.get(0));
	}
	
	
	/**
	 * 上传用户头像 示例用 ，暂无实际用途
	 * 
	 * @param file         文件
	 * @param userid       用户id
	 * @param userpassword 用户密码
	 * @return 用户对象
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
			return JsonUtil.basicError(1,"找不到用户，请确认id和password是否正确");
		} else {

		//配置文件名  一用户一头像。推荐使用 userId.png 格式
//			String filename = new Date().getTime() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
		//file.getOriginalFilename() 获取的是接收的文件名  例如 dsadsads.png  使用String.split()  以 .  分割，获取文件后缀  组合成 如 userId.png 格式
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
		
		//旧文件
		String orginalFileFillPath = request.getServletContext().getRealPath("/WEB-INF/") + originalFilePath;
		final String orginalFileFillProjectPath = FileUtil.getPath(request) + originalFilePath;
		
		final File f = FileUtil.creatFileAndDir(tempPath);
		file.transferTo(f);
		FileUtil.copyFile(f, projectPath);// File,String 写法 2	
		//删除旧文件
		FileUtil.deleteFile(orginalFileFillPath);
		FileUtil.deleteFile(orginalFileFillProjectPath);
		return JsonUtil.basic(user);
		}
	}
	
	/**
	 * 更改用户密码
	 * @param person 参数包含 userId ，userPassword
	 * @return 该用户对象
	 */
	@ResponseBody
	@PostMapping("changepassword")
	public String changePassword(@RequestBody Map<String, String> person) {
		String userId = person.get("userId").toString();
		String userPassword = person.get("userPassword").toString();
		System.out.println(String.format("用户   %s  尝试更改密码为  %s",userId,userPassword));
		if (userPassword == null || userPassword.isEmpty() || userPassword.length() > 16 || userPassword.length() == 0) {
			return JsonUtil.basicError(2,"length --- 0 < userPassword < 17  userPassword is " + userPassword);
		}
		userExample = new UserExample();
		userExample.createCriteria().andUserIdEqualTo(Long.valueOf(userId));
		List<User> result = userMapper.selectByExample(userExample);
		if (result.isEmpty()) {
			return JsonUtil.basicError(1,"找不到用户");
		} else {
			User user2 = result.get(0);
			if (userPassword.equals(user2.getUserPassword())) {
				return JsonUtil.basic(0,user2,"密码已更改(新密码与原密码一致)");
			}
			user2.setUserPassword(userPassword);
			
			int i = userMapper.updateByPrimaryKey(user2);
			return i > 0 ? JsonUtil.basic(user2):JsonUtil.basicError(3, "数据更新异常");
		}
	}
	
	/**
	 * 更改用户昵称
	 * @param person
	 * @return
	 */
	@ResponseBody
	@PostMapping("changename")
	public String changeName(@RequestBody Map<String, String> person) {
		String userId = person.get("userId").toString();
		String userName = person.get("userName").toString();
		System.out.println(String.format("用户   %s  尝试更改昵称为  %s",userId,userName));
		userExample = new UserExample();
		userExample.createCriteria().andUserIdEqualTo(Long.valueOf(userId));
		List<User> result = userMapper.selectByExample(userExample);
		if (result.isEmpty()) {
			return JsonUtil.basicError(1,"找不到用户");
		} else {
			userExample.clear();
			userExample.createCriteria().andUserNameEqualTo(userName).andUserIdNotEqualTo(Long.valueOf(userId));
			List<User> result2 = userMapper.selectByExample(userExample);
			if (!result2.isEmpty()) {
				return JsonUtil.basicError(2,"昵称已存在");
			} 
			User user2 = result.get(0);
			if (userName.equals(user2.getUserName())) {
				return JsonUtil.basic(0,user2,"昵称已更改(新昵称与原昵称一致)");
			}
			user2.setUserName(userName);
			
			int i = userMapper.updateByPrimaryKey(user2);
			return i > 0 ? JsonUtil.basic(user2):JsonUtil.basicError(3, "数据更新异常");
		}
	}
	
	/**
	 * 更改用户手机号
	 * @param person
	 * @return
	 */
	@ResponseBody
	@PostMapping("changephone")
	public String changePhone(@RequestBody Map<String, String> person) {
		String userId = person.get("userId").toString();
		String userPhoneKey = person.get("userPhoneKey").toString();
		System.out.println(String.format("用户   %s  尝试更改手机号为  %s",userId,userPhoneKey));
		if (userPhoneKey == null || userPhoneKey.length() != 11 ) {
			return JsonUtil.basicError(2,"手机号无效长度 ---" + userPhoneKey);
		}
		userExample = new UserExample();
		userExample.createCriteria().andUserIdEqualTo(Long.valueOf(userId));
		List<User> result = userMapper.selectByExample(userExample);
		if (result.isEmpty()) {
			return JsonUtil.basicError(1,"找不到用户");
		} else {
			userExample.clear();
			userExample.createCriteria().andUserPhoneKeyEqualTo(Long.valueOf(userPhoneKey)).andUserIdNotEqualTo(Long.valueOf(userId));
			List<User> result2 = userMapper.selectByExample(userExample);
			if (!result2.isEmpty()) {
				return JsonUtil.basicError(2,"手机号已存在");
			} 
			User user2 = result.get(0);
			if (Long.valueOf(userPhoneKey) == user2.getUserPhoneKey()) {
				return JsonUtil.basic(0,user2,"手机号已更改(新手机号与原手机号一致)");
			}
			user2.setUserPhoneKey(Long.valueOf(userPhoneKey));
			
			int i = userMapper.updateByPrimaryKey(user2);
			return i > 0 ? JsonUtil.basic(user2):JsonUtil.basicError(3, "数据更新异常");
		}
	}
	
	
	/**
	 * 获取所有用户
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("getusers")
	public String getUsers() {
		return JsonUtil.basic(userMapper.selectByExample(null));
	}
	
	/**
	 * 添加用户
	 * @param user 用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping("adduser")
	public String addUser(@RequestBody User user) {
		return createUser(user.getUserName(), user.getUserAccount(), user.getUserPassword(), user.getUserIcon());
//		int insert = userMapper.insert(user);
//		return insert == 1?JsonUtil.basic(0, user, null):JsonUtil.basic(-1,null ,"添加失败  --   Class UserController|method addUser");
	}
	
	
	
	
	
	
	public String createUser(String name,Long account,String password,String icon) {
		if (name == null ||name.isEmpty() ||name.length() == 0) {
			name = String.format("食苑%d", RandomUtil.randomNumber(6));
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
