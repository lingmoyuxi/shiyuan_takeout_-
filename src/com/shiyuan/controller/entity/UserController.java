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

//跨域传输
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
	 * 用户注册 
	 * @param user  userAccount账号  userPassword密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("register")
	public String register(@RequestBody Map<String, String> person) {
		String accountString = person.get("userAccount").toString();
		Long userAccount = Long.valueOf(accountString);
		String userPassword = person.get("userPassword").toString();
		System.out.println("用户注册  account-" + userAccount + "     password-" + userPassword );
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
			return JsonUtil.basicError(1, "用户已存在");
		}
		User user = newUser(userAccount, userPassword);
		int insert = userMapper.insert(user);
		return insert==1?JsonUtil.basic(user):JsonUtil.basicError(3, "添加用户失败");
	}
	
	/**
	 * QQ注册
	 * @param person  userName  userQqKey
	 * @return
	 */
	@ResponseBody
	@RequestMapping("registerqq")
	public String registerQQ(@RequestBody Map<String, String> person) {
		String userName = person.get("userName").toString();
		String userQqKeyString = person.get("userQqKey");
		if (userQqKeyString == null || userQqKeyString.isEmpty()) {
			return JsonUtil.basicError(2,"空QQKey  userQqKeyString is " + userQqKeyString);
		}
		String userQqKey = userQqKeyString.toString();
		
		System.out.println("用户QQ注册  userName-" + userName + "     userQqKey-" + userQqKey );
		
		userExample.clear();
		userExample.createCriteria().andUserQqKeyEqualTo(userQqKey);
		List<User> users = userMapper.selectByExample(userExample);
		if (users.isEmpty()) {  //未被绑定
			User user = newUser(userName,RandomUtil.randomNumber(11), RandomUtil.randomString(16));
			user.setUserQqKey(userQqKey);
			
			Thirdlogin thirdlogin = new Thirdlogin();
			thirdlogin.setThirdloginId(RandomUtil.randomNumber(16));
			thirdlogin.setThirdloginKey(userQqKey);
			thirdlogin.setUserId(user.getUserId());
			int i = thirdLoginMapper.insert(thirdlogin);
			if (i!=1) {
				return JsonUtil.basicError(3,"未记录QQKey，且试图创建绑定依赖失败");
			}
			int insert = userMapper.insert(user);
			return insert == 1?JsonUtil.basic(user):JsonUtil.basicError(3, "创建初始化用户失败");
		} else {
			return JsonUtil.basicError(2,"该QQ已经被绑定过，无法重新注册 ");
		}
	}
	
	/**
	 * 微信注册
	 * @param person  userName  userQqKey
	 * @return
	 */
	@ResponseBody
	@RequestMapping("registerwx")
	public String registerwx(@RequestBody Map<String, String> person) {
		String userName = person.get("userName").toString();
		String userWeixinKeyString = person.get("userWeixinKey");
		if (userWeixinKeyString == null || userWeixinKeyString.isEmpty()) {
			return JsonUtil.basicError(2,"空微信Key  userWeixinKey is " + userWeixinKeyString);
		}
		String userWeixinKey = userWeixinKeyString.toString();
		
		System.out.println("用户微信注册  userName-" + userName + "     userQqKey-" + userWeixinKey );
		
		userExample.clear();
		userExample.createCriteria().andUserWeixinKeyEqualTo(userWeixinKey);
		List<User> users = userMapper.selectByExample(userExample);
		if (users.isEmpty()) {  //未被绑定
			User user = newUser(userName,RandomUtil.randomNumber(11), RandomUtil.randomString(16));
			user.setUserWeixinKey(userWeixinKey);
			
			Thirdlogin thirdlogin = new Thirdlogin();
			thirdlogin.setThirdloginId(RandomUtil.randomNumber(16));
			thirdlogin.setThirdloginKey(userWeixinKey);
			thirdlogin.setUserId(user.getUserId());
			int i = thirdLoginMapper.insert(thirdlogin);
			if (i!=1) {
				return JsonUtil.basicError(3,"未记录微信Key，且试图创建绑定依赖失败");
			}
			int insert = userMapper.insert(user);
			return insert == 1?JsonUtil.basic(user):JsonUtil.basicError(3, "创建初始化用户失败");
		} else {
			return JsonUtil.basicError(2,"该微信已经被绑定过，无法重新注册 ");
		}
	}
	
	/**
	 * 用户登陆
	 * @param person   userAccount账号  userPassword密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("login")
	public String login(@RequestBody Map<String, String> person) {
		userExample.clear();
		if (person == null ||person.isEmpty()) {
			System.out.println("未接受到参数");
			return JsonUtil.basicError(1, "未接受到参数");
		}
		String userAccount = person.get("userAccount").toString();
		String userPassword = person.get("userPassword").toString();
		System.out.println("用户登陆  account-" + userAccount + "     password-" + userPassword );
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
	 * QQ登陆
	 * @param person userQqKey
	 * @return
	 */
	@ResponseBody
	@RequestMapping("loginqq")
	public String loginqq(@RequestBody Map<String, String> person) {
		if (person == null ||person.isEmpty()) {
			System.out.println("未接受到参数");
			return JsonUtil.basicError(1, "未接受到参数");
		}
		
		String userQqKey = person.get("userQqKey").toString();
		
		System.out.println("用户QQ登陆  qqKey-" + userQqKey);
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
				return JsonUtil.basicError(3,"未记录QQKey，且试图创建绑定依赖失败");
			}
			int insert = userMapper.insert(createUser);
			return insert==1?JsonUtil.basic(createUser):JsonUtil.basicError(3,"未记录QQKey，且试图创建初始化用户失败");
		} else {
			userExample.clear();
			userExample.createCriteria().andUserIdEqualTo(thirdlogins.get(0).getUserId()).andUserQqKeyEqualTo(userQqKey);
			List<User> users = userMapper.selectByExample(userExample);
			return users.isEmpty()?JsonUtil.basicError(1, "用户不存在"):JsonUtil.basic(users.get(0));
		}
	}
	
	/**
	 * 微信登陆
	 * @param person userWeixinKey
	 * @return
	 */
	@ResponseBody
	@RequestMapping("loginwx")
	public String loginwx(@RequestBody Map<String, String> person) {
		if (person == null ||person.isEmpty()) {
			System.out.println("未接受到参数");
			return JsonUtil.basicError(1, "未接受到参数");
		}
		
		String userWeixinKey = person.get("userWeixinKey").toString();
		
		System.out.println("用户微信登陆  wxKey-" + userWeixinKey);
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
				return JsonUtil.basicError(3,"未记录wxKey，且试图创建绑定依赖失败");
			}
			int insert = userMapper.insert(createUser);
			return insert==1?JsonUtil.basic(createUser):JsonUtil.basicError(3,"未记录wxKey，且试图创建初始化用户失败");
		} else {
			userExample.clear();
			userExample.createCriteria().andUserIdEqualTo(thirdlogins.get(0).getUserId()).andUserWeixinKeyEqualTo(userWeixinKey);
			List<User> users = userMapper.selectByExample(userExample);
			return users.isEmpty()?JsonUtil.basicError(1, "用户不存在"):JsonUtil.basic(users.get(0));
		}
	}
	
	
	/**
	 * 更改用户头像
	 * 
	 * @param file         文件
	 * @param userid       用户id
	 * @param userpassword 用户密码
	 * @return 用户对象
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@PostMapping("changeicon")
	public String changeIcon(HttpServletRequest request, @RequestParam("icon") CommonsMultipartFile file, Long userid,
			String userpassword) throws IllegalStateException, IOException {
		System.out.println("用户     " + userid + "  更换头像");
		userExample = new UserExample();
		userExample.createCriteria().andUserIdEqualTo(userid).andUserPasswordEqualTo(userpassword);
		List<User> result = userMapper.selectByExample(userExample);
		if (result.isEmpty()) {
			System.out.println("用户     " + userid + "  更换头像   ---用户不存在");
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
		

		user.setUserIcon("image/usericon/" + filename);
		userMapper.updateByPrimaryKey(user);
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
		String newPassword = person.get("newPassword").toString();
		System.out.println(String.format("用户   %s  尝试更改密码为  %s",userId,newPassword));
		if (userPassword == null || userPassword.isEmpty() || userPassword.length() > 16 || userPassword.length() == 0) {
			return JsonUtil.basicError(2,"length --- 0 < userPassword < 17  userPassword is " + userPassword);
		}
		userExample.clear();
		userExample.createCriteria().andUserIdEqualTo(Long.valueOf(userId)).andUserPasswordEqualTo(userPassword);
		List<User> result = userMapper.selectByExample(userExample);
		if (result.isEmpty()) {
			System.out.println("密码错误");
			return JsonUtil.basicError(1,"旧密码错误");
		} else {
			User user2 = result.get(0);
			if (newPassword.equals(user2.getUserPassword())) {
				return JsonUtil.basic(0,user2,"密码已更改(新密码与原密码一致)");
			}
			user2.setUserPassword(newPassword);
			System.out.println("更改密码为" + newPassword);
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
	 * 用户绑定QQ
	 * @param person  userId  userQqKey
	 * @return
	 */
	@ResponseBody
	@PostMapping("userbindqq")
	public String qqBind(@RequestBody Map<String, String> person) {
		String userId = person.get("userId").toString();
		String userQqKey = person.get("userQqKey").toString();
		System.out.println(String.format("用户   %s  尝试更改绑定QQ  %s",userId,userQqKey));
		userExample.clear();
		userExample.createCriteria().andUserIdEqualTo(Long.valueOf(userId));
		List<User> result = userMapper.selectByExample(userExample);
		if (result.isEmpty()) {
			return JsonUtil.basicError(1,"找不到用户");
		} else {
			User user = result.get(0);
			
			userExample.clear();
			userExample.createCriteria().andUserQqKeyEqualTo(userQqKey);
			List<User> users = userMapper.selectByExample(userExample);
			if (!(users == null|| users.isEmpty())) {
				for (User user2 : users) {
					if (!String.valueOf(user2.getUserId()).equals(String.valueOf(user.getUserId()))) {
						return JsonUtil.basicError(2, "该QQ已被其它账户关联");
					}
				}
				return JsonUtil.basic(0,user,"未更改（新旧QQ绑定一致）");
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
			return i > 0 ? JsonUtil.basic(user):JsonUtil.basicError(3, "数据更新异常");
		}
	}
	
	
	/**
	 * 用户绑定微信
	 * @param person  userId  userWeixinKey
	 * @return
	 */
	@ResponseBody
	@PostMapping("userbindwx")
	public String wxBind(@RequestBody Map<String, String> person) {
		String userId = person.get("userId").toString();
		String userWeixinKey = person.get("userWeixinKey").toString();
		System.out.println(String.format("用户   %s  尝试更改绑定微信  %s",userId,userWeixinKey));
		userExample.clear();
		userExample.createCriteria().andUserIdEqualTo(Long.valueOf(userId));
		List<User> result = userMapper.selectByExample(userExample);
		if (result.isEmpty()) {
			return JsonUtil.basicError(1,"找不到用户");
		} else {
			User user = result.get(0);
			
			userExample.clear();
			userExample.createCriteria().andUserWeixinKeyEqualTo(userWeixinKey);
			List<User> users = userMapper.selectByExample(userExample);
			System.out.println("使用该wxKey的用户"+users.size());
			if (!(users == null|| users.isEmpty())) {
				for (User user2 : users) {
					System.out.println("使用该wxKey的用户"+user2.getUserId());
					if (!String.valueOf(user2.getUserId()).equals(String.valueOf(user.getUserId()))) {
						System.out.println("用户比对 "+user2.getUserId()+"--"+user.getUserId());
						return JsonUtil.basicError(2, "该微信已被其它账户关联");
					}
				}
				return JsonUtil.basic(0,user,"未更改（新旧微信绑定一致）");
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
			return i > 0 ? JsonUtil.basic(user):JsonUtil.basicError(3, "数据更新异常");
		}
	}
	
	
	
	/**
	 * 用户解除绑定QQ
	 * @param person  userId  userQqKey
	 * @return
	 */
	@ResponseBody
	@PostMapping("usernobindqq")
	public String qqNoBind(@RequestBody Map<String, String> person) {
		String userId = person.get("userId").toString();
		System.out.println(String.format("用户   %s  尝试解除绑定QQ",userId));
		userExample.clear();
		userExample.createCriteria().andUserIdEqualTo(Long.valueOf(userId));
		List<User> result = userMapper.selectByExample(userExample);
		if (result.isEmpty()) {
			return JsonUtil.basicError(1,"找不到用户");
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
			return i > 0 ? JsonUtil.basic(user):JsonUtil.basicError(3, "数据更新异常");
		}
	}
	
	
	/**
	 * 用户绑定QQ
	 * @param person  userId  userWeixinKey
	 * @return
	 */
	@ResponseBody
	@PostMapping("usernobindwx")
	public String wxNoBind(@RequestBody Map<String, String> person) {
		String userId = person.get("userId").toString();
		System.out.println(String.format("用户   %s  尝试解除绑定微信",userId));
		userExample.clear();
		userExample.createCriteria().andUserIdEqualTo(Long.valueOf(userId));
		List<User> result = userMapper.selectByExample(userExample);
		if (result.isEmpty()) {
			return JsonUtil.basicError(1,"找不到用户");
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
			return i > 0 ? JsonUtil.basic(user):JsonUtil.basicError(3, "数据更新异常");
		}
	}
	
	
	@ResponseBody
	@PostMapping("changedromroom")
	public String userDromRoom(@RequestBody Map<String, String> person) {
		String userId = person.get("userId").toString();
		String userDromRoom = person.get("userDromRoom").toString();
		System.out.println(String.format("用户   %s  尝试更改宿舍楼栋为 %s",userId,userDromRoom));
		userExample.clear();
		userExample.createCriteria().andUserIdEqualTo(Long.valueOf(userId));
		List<User> result = userMapper.selectByExample(userExample);
		if (result.isEmpty()) {
			return JsonUtil.basicError(1,"找不到用户");
		} else {
			User user = result.get(0);
			user.setUserDromRoom(userDromRoom);
			int i = userMapper.updateByPrimaryKey(user);
			return i > 0 ? JsonUtil.basic(user):JsonUtil.basicError(3, "数据更新异常");
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
	 * 获取  用户 (从所有user中抽取而来)
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("getusersoflength")
	public String getUsers(int length) {
		return JsonUtil.basic(RandomUtil.randomList(userMapper.selectByExample(null), length));
	}
	
	/**
	 * 添加用户
	 * @param user 用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping("adduser")
	public String addUser(@RequestBody User user) {
		return createUser(user.getUserName(), user.getUserAccount(), user.getUserPassword());
//		int insert = userMapper.insert(user);
//		return insert == 1?JsonUtil.basic(0, user, null):JsonUtil.basic(-1,null ,"添加失败  --   Class UserController|method addUser");
	}
	
	
	
	
	
	
	public String createUser(String name,Long account,String password) {
		if (name == null ||name.isEmpty() ||name.length() == 0) {
			name = String.format("食苑%d", RandomUtil.randomNumber(6));
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
			name = String.format("食苑%d", RandomUtil.randomNumber(6));
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
