package com.shiyuan.controller.entity;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shiyuan.mapper.ScopeMapper;
import com.shiyuan.model.Scope;
import com.shiyuan.model.ScopeExample;
import com.shiyuan.util.JsonUtil;
import com.shiyuan.util.RandomUtil;

//跨域传输
@CrossOrigin
@Controller
@RequestMapping(produces = {"application/json;charset=UTF-8"})
public class ScopeController {
	@Autowired
	ScopeMapper scopeMapper;
	ScopeExample scopeExample = new ScopeExample();
	
	/**
	 * 获取所有营业类别
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getscopes")
	public String getScopes() {
		return JsonUtil.basic(scopeMapper.selectByExample(null));
	}
	
	/**
	 * 添加营业类别
	 * @param scope  scopeName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addscope")
	public String addScope(@RequestBody Map<String, String> person) {
		String scopeName = person.get("scopeName").toString();
		scopeExample.clear();
		scopeExample.createCriteria().andScopeNameEqualTo(scopeName);
		List<Scope> selectByExample = scopeMapper.selectByExample(scopeExample);
		if (selectByExample == null ||selectByExample.size() == 0) {
			Scope scope = new Scope();
			scope.setScopeId(RandomUtil.randomNumber(16));
			scope.setScopeName(scopeName);
			int insert = scopeMapper.insert(scope);
			return insert==1?JsonUtil.basic(scope):JsonUtil.basicError(3, "操作数据库出现问题");
		}
		return JsonUtil.basic(selectByExample.get(0));
		
	}
	
	/**
	 * 删除营业类别
	 * @param person 可选 scopeId  scopeName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("removescope")
	public String removeScope(@RequestBody Map<String, String> person) {
		Long scopeId = person.get("scopeId")==null?null:Long.valueOf(person.get("scopeId").toString());
		String scopeName = person.get("scopeName").toString();
		
		if (scopeId != null && scopeMapper.selectByPrimaryKey(scopeId) != null) {
			return scopeMapper.deleteByPrimaryKey(scopeId)==1?JsonUtil.basicError(0, "删除完成"):JsonUtil.basicError(3, "错误操作");
		}
		scopeExample.clear();
		scopeExample.createCriteria().andScopeNameEqualTo(scopeName);
		List<Scope> selectByExample = scopeMapper.selectByExample(scopeExample);
		if (selectByExample == null ||selectByExample.size() == 0) {
			return JsonUtil.basicError(0, "没有该数据");
		}
		int i = scopeMapper.deleteByPrimaryKey(selectByExample.get(0).getScopeId());
		return i==1?JsonUtil.basicError(0, "已删除"):JsonUtil.basicError(3, "操作数据库出现问题");
		
	}
	
	/**
	 * 查询
	 * @param person  可选 scopeId  scopeName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("queryscope")
	public String queryScope(@RequestBody Map<String, String> person) {
		Long scopeId = person.get("scopeId")==null?null:Long.valueOf(person.get("scopeId").toString());
		String scopeName = person.get("scopeName").toString();
		
		if (scopeId != null && scopeMapper.selectByPrimaryKey(scopeId) != null) {
			return JsonUtil.basic(scopeMapper.selectByPrimaryKey(scopeId));
		}
		scopeExample.clear();
		scopeExample.createCriteria().andScopeNameEqualTo(scopeName);
		List<Scope> selectByExample = scopeMapper.selectByExample(scopeExample);
		if (selectByExample == null ||selectByExample.size() == 0) {
			return JsonUtil.basicError(1, "没有该数据");
		}		
		return JsonUtil.basic(selectByExample.get(0));
	}
}
