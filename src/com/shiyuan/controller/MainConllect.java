package com.shiyuan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.shiyuan.mapper.UserMapper;
import com.shiyuan.model.UserExample;

@Controller
@RequestMapping(produces = {"application/json;charset=UTF-8"})
public class MainConllect {
	@Autowired
	UserMapper userMapper;
	UserExample userExample = new UserExample();
	
	
	
}
