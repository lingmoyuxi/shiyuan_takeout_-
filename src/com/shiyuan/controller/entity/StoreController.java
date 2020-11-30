package com.shiyuan.controller.entity;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.shiyuan.mapper.StoreMapper;
import com.shiyuan.model.Store;
import com.shiyuan.model.StoreExample;
import com.shiyuan.util.FileUtil;
import com.shiyuan.util.JsonUtil;
import com.shiyuan.util.RandomUtil;

//跨域传输
@CrossOrigin
@Controller
@RequestMapping(produces = { "application/json;charset=UTF-8" })
public class StoreController {
	@Autowired
	StoreMapper storeMapper;
	StoreExample storeExample = new StoreExample();

	@ResponseBody
	@PostMapping("addstore")
	public String addStore(HttpServletRequest request, @RequestParam("icon") CommonsMultipartFile file,
			String name,String introduction,String fullAddress,Long phone,String businessHours,Long scopeId,String storeSchool) {
		Store store = new Store();
		store.setStoreId(RandomUtil.randomNumber(16));
		store.setName(name);
		store.setIntroduction(introduction);
		store.setFullAddress(fullAddress);
		store.setPhone(phone);
		store.setBusinessHours(businessHours);
		store.setScopeId(scopeId);
		store.setStoreSchool(storeSchool);
		
		String[] split = file.getOriginalFilename().split("\\.");
		String filename = store.getStoreId() + "." + split[split.length - 1]; 
		String filepathString = "resource/image/storeicon/" + filename;
		String tempPath = request.getServletContext().getRealPath("/WEB-INF/") + filepathString;
		final String projectPath = FileUtil.getPath(request) + filepathString;
		
		final File f = FileUtil.creatFileAndDir(tempPath);
		try {
			file.transferTo(f);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		store.setIcon("image/storeicon/" + filename);
		int insert = storeMapper.insert(store);
		FileUtil.copyFile(f, projectPath);// File,String 写法 2	
		return insert==1?JsonUtil.basic(store):JsonUtil.basicError(3,"数据插入错误");

	}

	@ResponseBody
	@PostMapping("getstores")
	public String getStores() {
		return JsonUtil.basic(storeMapper.selectByExample(null));
	}
	
	@ResponseBody
	@PostMapping("store")
	public String Store() {
		return JsonUtil.basic(new Store());
	}
}
