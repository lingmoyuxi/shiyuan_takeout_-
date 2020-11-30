package com.shiyuan.controller.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shiyuan.mapper.SchoolMapper;
import com.shiyuan.model.School;
import com.shiyuan.model.SchoolExample;
import com.shiyuan.util.JsonUtil;
import com.shiyuan.util.RandomUtil;

//跨域传输
@CrossOrigin
@Controller
@RequestMapping(produces = { "application/json;charset=UTF-8" })
public class SchoolController {
	@Autowired
	SchoolMapper schoolMapper;
	SchoolExample schoolExample = new SchoolExample();
	
	@ResponseBody
	@PostMapping("getschools")
	public String getSchools() {
		return JsonUtil.basic(schoolMapper.selectByExample(null));
	}
	
	@ResponseBody
	@PostMapping("addschool")
	public String addSchool(@RequestBody School school) {
		school.setSchoolId(RandomUtil.randomNumber(16));
		int insert = schoolMapper.insert(school);
		return insert ==1 ? JsonUtil.basic(school):JsonUtil.basicError(3, "添加失败");
	}
	
	@ResponseBody
	@PostMapping("removeschool")
	public String removeSchool(Long schoolId) {
		int i = schoolMapper.deleteByPrimaryKey(schoolId);
		return i==1?JsonUtil.basic(schoolMapper.selectByExample(null)):JsonUtil.basicError(3, "删除失败 接收到的id为" + schoolId);
	}
}
