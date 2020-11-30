package com.shiyuan.controller.entity;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.shiyuan.mapper.CommodityMapper;
import com.shiyuan.mapper.MealmapMapper;
import com.shiyuan.model.Commodity;
import com.shiyuan.model.CommodityExample;
import com.shiyuan.model.Mealmap;
import com.shiyuan.model.MealmapExample;
import com.shiyuan.util.FileUtil;
import com.shiyuan.util.JsonUtil;
import com.shiyuan.util.RandomUtil;

//跨域传输
@CrossOrigin
@Controller
@RequestMapping(produces = { "application/json;charset=UTF-8" })
public class CommodityController {

	@Autowired
	CommodityMapper commodityMapper;
	CommodityExample commodityExample = new CommodityExample();
	
	@Autowired
	MealmapMapper mealmapExample;
	MealmapExample mealmapMapper = new MealmapExample();
	
	@ResponseBody
	@PostMapping("getcommoditys")
	public String getCommoditys() {
		return JsonUtil.basic(new Commodity());
	}
	
	@ResponseBody
	@PostMapping("addcommodity")
	public String addCommodity(HttpServletRequest request, @RequestParam("icon") CommonsMultipartFile file,
			Long mealId,String commodityName,BigDecimal price,BigDecimal discountPrice,String introduction) {
		Commodity commodity = new Commodity();
		commodity.setCommodityId(RandomUtil.randomNumber(16));
		commodity.setCommodityName(commodityName);
		commodity.setPrice(price);
		commodity.setIntroduction(introduction);
		
		Mealmap mealmap = new Mealmap();
		mealmap.setMealmapId(RandomUtil.randomNumber(16));
		mealmap.setMealId(mealId);
		mealmap.setCommodityId(commodity.getCommodityId());
		int insert2 = mealmapExample.insert(mealmap);
		if (insert2 != 1) {
			return JsonUtil.basicError(3, "添加餐品集映射失败");
		}
		String[] split = file.getOriginalFilename().split("\\.");
		String filename = commodity.getCommodityId() + "." + split[split.length - 1]; 
		String filepathString = "resource/image/commodity/" + mealId + "/" + filename;
		String tempPath = request.getServletContext().getRealPath("/WEB-INF/") + filepathString;
		final String projectPath = FileUtil.getPath(request) + filepathString;
		
		final File f = FileUtil.creatFileAndDir(tempPath);
		try {
			file.transferTo(f);
		} catch (IllegalStateException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		commodity.setIcon("image/commodity/" + mealId + "/" + filename);
		int insert = commodityMapper.insert(commodity);
		
		return insert==1?JsonUtil.basic(commodityMapper.selectByExample(null)):JsonUtil.basicError(3, "商品添加失败");
	}
}
