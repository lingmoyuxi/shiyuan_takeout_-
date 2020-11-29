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

//������
@CrossOrigin
@Controller
@RequestMapping(produces = {"application/json;charset=UTF-8"})
public class ScopeController {
	@Autowired
	ScopeMapper scopeMapper;
	ScopeExample scopeExample = new ScopeExample();
	
	/**
	 * ��ȡ����Ӫҵ���
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getscopes")
	public String getScopes() {
		return JsonUtil.basic(scopeMapper.selectByExample(null));
	}
	
	/**
	 * ���Ӫҵ���
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
			return insert==1?JsonUtil.basic(scope):JsonUtil.basicError(3, "�������ݿ��������");
		}
		return JsonUtil.basic(selectByExample.get(0));
		
	}
	
	/**
	 * ɾ��Ӫҵ���
	 * @param person ��ѡ scopeId  scopeName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("removescope")
	public String removeScope(@RequestBody Map<String, String> person) {
		Long scopeId = person.get("scopeId")==null?null:Long.valueOf(person.get("scopeId").toString());
		String scopeName = person.get("scopeName").toString();
		
		if (scopeId != null && scopeMapper.selectByPrimaryKey(scopeId) != null) {
			return scopeMapper.deleteByPrimaryKey(scopeId)==1?JsonUtil.basicError(0, "ɾ�����"):JsonUtil.basicError(3, "�������");
		}
		scopeExample.clear();
		scopeExample.createCriteria().andScopeNameEqualTo(scopeName);
		List<Scope> selectByExample = scopeMapper.selectByExample(scopeExample);
		if (selectByExample == null ||selectByExample.size() == 0) {
			return JsonUtil.basicError(0, "û�и�����");
		}
		int i = scopeMapper.deleteByPrimaryKey(selectByExample.get(0).getScopeId());
		return i==1?JsonUtil.basicError(0, "��ɾ��"):JsonUtil.basicError(3, "�������ݿ��������");
		
	}
	
	/**
	 * ��ѯ
	 * @param person  ��ѡ scopeId  scopeName
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
			return JsonUtil.basicError(1, "û�и�����");
		}		
		return JsonUtil.basic(selectByExample.get(0));
	}
}
