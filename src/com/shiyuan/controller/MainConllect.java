package com.shiyuan.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

//import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.shiyuan.mapper.CommodityMapper;
import com.shiyuan.mapper.MealMapper;
import com.shiyuan.mapper.MealmapMapper;
import com.shiyuan.mapper.ScopeMapper;
import com.shiyuan.mapper.StoreMapper;
import com.shiyuan.mapper.UserMapper;
import com.shiyuan.model.Commodity;
import com.shiyuan.model.CommodityExample;
import com.shiyuan.model.Meal;
import com.shiyuan.model.MealExample;
import com.shiyuan.model.Mealmap;
import com.shiyuan.model.MealmapExample;
import com.shiyuan.model.Scope;
import com.shiyuan.model.ScopeExample;
import com.shiyuan.model.Store;
import com.shiyuan.model.StoreExample;
import com.shiyuan.model.User;
import com.shiyuan.model.UserExample;
import com.shiyuan.util.FileUtil;
import com.shiyuan.util.JsonUtil;
import com.shiyuan.util.RandomUtil;

//跨域传输
@CrossOrigin
@Controller
@RequestMapping(produces = { "application/json;charset=UTF-8" })
public class MainConllect {
	@Autowired
	UserMapper userMapper;
	UserExample userExample = new UserExample();
	@Autowired
	MealMapper mealMapper;
	MealExample mealExample = new MealExample();
	@Autowired
	MealmapMapper mealMapMapper;
	MealmapExample mealMapExample = new MealmapExample();
	@Autowired
	CommodityMapper commodityMapper;
	CommodityExample commodityExample = new CommodityExample();
	@Autowired
	StoreMapper storeMapper;
	StoreExample storeExample = new StoreExample();
	@Autowired
	ScopeMapper scopeMapper;
	ScopeExample scopeExample = new ScopeExample(); 
	
	/**
	 * 获取店铺内的商品
	 * @param storeId 店铺id
	 * @param type 获取形式，  所有菜品   1菜单分类的菜品
	 * @return
	 */
	@ResponseBody
	@GetMapping("getstoretocommodity")
	public String getStoreToCommodity(@RequestParam("storeId") Long storeId, String type) {
		mealExample.clear();
		mealExample.createCriteria().andStoreIdEqualTo(storeId);
		List<Meal> selectByExample = mealMapper.selectByExample(mealExample);
		if (!(type!=null&&Integer.valueOf(type)==1)) {
			List<Commodity> resultList = new ArrayList<Commodity>();
			for (Meal meal : selectByExample) {
				mealMapExample.clear();
				mealMapExample.createCriteria().andMealIdEqualTo(meal.getMealId());
				List<Mealmap> selectByExample2 = mealMapMapper.selectByExample(mealMapExample);
				for (Mealmap mealmap : selectByExample2) {
					Commodity selectByPrimaryKey = commodityMapper.selectByPrimaryKey(mealmap.getCommodityId());
					if (selectByPrimaryKey != null) {
						resultList.add(selectByPrimaryKey);
					}
				}
			}
			return JsonUtil.basic(resultList);
		} else {
			List<MealmaptoCommodity> resultList = new ArrayList<MealmaptoCommodity>();
			for (Meal meal : selectByExample) {
				mealMapExample.clear();
				mealMapExample.createCriteria().andMealIdEqualTo(meal.getMealId());
				List<Mealmap> selectByExample2 = mealMapMapper.selectByExample(mealMapExample);
				MealmaptoCommodity mealmaptoCommodity = new MealmaptoCommodity();
				mealmaptoCommodity.mealName = meal.getMealName();
				mealmaptoCommodity.mealId = meal.getMealId();
				List<Commodity> commodities = new ArrayList<Commodity>();
				for (Mealmap mealmap : selectByExample2) {
					Commodity selectByPrimaryKey = commodityMapper.selectByPrimaryKey(mealmap.getCommodityId());
					if (selectByPrimaryKey != null) {
						commodities.add(selectByPrimaryKey);
					}
				}
				mealmaptoCommodity.commodities = commodities;
				resultList.add(mealmaptoCommodity);
			}
			return JsonUtil.basic(resultList);
		}
	}

	/**
	 * 获取商品
	 * @param length 数量默认15
	 * @return
	 */
	@ResponseBody
	@GetMapping("commoditylist")
	public String CommodityList(String length) {
		List<Commodity> list = commodityMapper.selectByExample(null);
		int len = 15;
		if (length != null) {
			try {
				len = Integer.valueOf(length);
			} catch (Exception e) {
			}
		}
		if (len > list.size()) {
			return JsonUtil.basic(list);
		} else {
			return JsonUtil.basic(RandomUtil.randomList(list, len));
		}
	}
	
	/**
	 * 获取所有店铺
	 * @param length 获取个数 默认15
	 * @return
	 */
	@ResponseBody
	@GetMapping("storelist")
	public String storeList(String length) {
		List<Store> storeList = storeMapper.selectByExample(null);
		int len = 15;
		if (length != null) {
			try {
				len = Integer.valueOf(length);
			} catch (Exception e) {
			}
		}
		if (len > storeList.size()) {
			return JsonUtil.basic(storeList);
		} else {
			return JsonUtil.basic(RandomUtil.randomList(storeList, len));
		}
	}
	
	/**
	 * 获取营业类别及对应的店铺
	 * @return
	 */
	@ResponseBody
	@GetMapping("scopetostores")
	public String scopeToStores() {
		List<Scope> scopes = scopeMapper.selectByExample(null);
		List<ScopeToStore> resuList = new ArrayList<>();
		for (Scope scope : scopes) {
			storeExample.clear();
			storeExample.createCriteria().andScopeIdEqualTo(scope.getScopeId());
			ScopeToStore scopeToStore = new ScopeToStore();
			scopeToStore.scopeId = scope.getScopeId();
			scopeToStore.scopeName = scope.getScopeName();
			scopeToStore.stores = storeMapper.selectByExample(storeExample);
			resuList.add(scopeToStore);
		}
		return JsonUtil.basic(resuList);
	}
	
	
	
	
//	/**
//	 * 上传文件 接收文件基本示例
//	 * 
//	 * @param file 文件
//	 * @return 该文件信息
//	 * @throws IllegalStateException
//	 * @throws IOException
//	 */
//	@ResponseBody
//	@PostMapping("upload")
//	public String fileUpload(HttpServletRequest request, @RequestParam("upfile") CommonsMultipartFile file)
//			throws IllegalStateException, IOException {
//
//		String filetypeString = FileUtil.getFileTypePath(file);
//
//		// 数据库记录便于访问的文件路径
//		String filepathString = filetypeString + file.getOriginalFilename(); // 存储文件路径及文件名
//		// 本地保存的文件路径
//		String tempPath = request.getServletContext().getRealPath("/WEB-INF/") + filepathString; // 服务器临时缓存目录地址
//																									// （保存于此以便能实时访问资源）
//		final String projectPath = FileUtil.getPath(request) + filepathString; // 项目工程目录地址 （保存于此以便能方便随源代码一起打包）
//
//		// 文件1存储于服务器缓存目录下
//		final File f = FileUtil.creatFileAndDir(tempPath);
//		file.transferTo(f);
//		//新线程拷贝文件
//		File f2 = new File(projectPath);
//		FileUtil.copyFile(f, f2); // File,File 写法1
////		FileUtil.copyFile(f, projectPath);// File,String 写法 2
//		//返回接收文件的信息
//		return JsonUtil.basic("{\"fileType\":\"" + file.getContentType() + "\",\"fileSize\":" + file.getSize()
//				+ ",\"filePath\":\"" + "resource/" + filetypeString + file.getOriginalFilename() + "\"}");
//	}

	class MealmaptoCommodity{
		public String mealName;
		public Long mealId;
		public List commodities;
	}
	
	class ScopeToStore{
		public String scopeName;
		public Long scopeId;
		public List stores;
	}
}
