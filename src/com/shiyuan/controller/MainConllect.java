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

//跨域传输
@CrossOrigin
@Controller
@RequestMapping(produces = { "application/json;charset=UTF-8" })
public class MainConllect {
	@Autowired
	UserMapper userMapper;
	UserExample userExample = new UserExample();

	/**
	 * 上传文件 接收文件基本示例
	 * 
	 * @param file 文件
	 * @return 该文件信息
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@PostMapping("upload")
	public String fileUpload(HttpServletRequest request, @RequestParam("upfile") CommonsMultipartFile file)
			throws IllegalStateException, IOException {

		String filetypeString = FileUtil.getFileTypePath(file);

		// 数据库记录便于访问的文件路径
		String filepathString = filetypeString + file.getOriginalFilename(); // 存储文件路径及文件名
		// 本地保存的文件路径
		String tempPath = request.getServletContext().getRealPath("/WEB-INF/") + filepathString; // 服务器临时缓存目录地址
																									// （保存于此以便能实时访问资源）
		final String projectPath = FileUtil.getPath(request) + filepathString; // 项目工程目录地址 （保存于此以便能方便随源代码一起打包）

		// 文件1存储于服务器缓存目录下
		final File f = FileUtil.creatFileAndDir(tempPath);
		file.transferTo(f);
		//新线程拷贝文件
		File f2 = new File(projectPath);
		FileUtil.copyFile(f, f2); // File,File 写法1
//		FileUtil.copyFile(f, projectPath);// File,String 写法 2
		//返回接收文件的信息
		return JsonUtil.basic("{\"fileType\":\"" + file.getContentType() + "\",\"fileSize\":" + file.getSize()
				+ ",\"filePath\":\"" + "resource/" + filetypeString + file.getOriginalFilename() + "\"}");
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
//		userExample = new UserExample();
//		userExample.createCriteria().andUserIdEqualTo(userid).andUserPasswordEqualTo(userpassword);
//		List<User> result = userMapper.selectByExample(userExample);
//		if (result.isEmpty()) {
//			return JsonUtil.basicError(1,"找不到用户，请确认id和password是否正确");
//		} else {

		//配置文件名  一用户一头像。推荐使用 userId.png 格式
//			String filename = new Date().getTime() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
		//file.getOriginalFilename() 获取的是接收的文件名  例如 dsadsads.png  使用String.split()  以 .  分割，获取文件后缀  组合成 如 userId.png 格式
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
//		FileUtil.copyFile(f, projectPath);// File,String 写法 2		
		return JsonUtil.basic(user);
//		}
	}

}
