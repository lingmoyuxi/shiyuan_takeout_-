package com.shiyuan.controller.entity;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiyuan.mapper.OrdersMapper;
import com.shiyuan.mapper.UserMapper;
import com.shiyuan.model.Commodity;
import com.shiyuan.model.Orders;
import com.shiyuan.model.OrdersExample;
import com.shiyuan.model.User;
import com.shiyuan.model.UserExample;
import com.shiyuan.util.JsonUtil;
import com.shiyuan.util.RandomUtil;

//跨域传输
@CrossOrigin
@Controller
@RequestMapping(produces = { "application/json;charset=UTF-8" })
public class OrderController {
	@Autowired
	UserMapper userMapper;
	UserExample userExample = new UserExample();
	@Autowired
	OrdersMapper ordersMapper;
	OrdersExample ordersExample = new OrdersExample();
	
	/**
	 * 提交订单
	 * @param 示例Json {\"userId\":213,\"orderAddress\":\"sad\",\"userName\":\"dsads\",\"userPhone\":13768111231,\"commodityList\":[{\"commodity\":{\"commodityId\":7590887542288737,\"commodityName\":\"螺蛳粉\",\"icon\":\"image/commodity/123/7590887542288737.png\",\"price\":18.5,\"discountPrice\":null,\"introduction\":\"美味的螺蛳粉\"},\"Number\":2},{\"commodity\":{\"commodityId\":7590887542288737,\"commodityName\":\"螺蛳粉\",\"icon\":\"image/commodity/123/7590887542288737.png\",\"price\":18.5,\"discountPrice\":null,\"introduction\":\"美味的螺蛳粉\"},\"Number\":1}],\"totalPrice\":28.2,\"remarks\":\"备注--多加糖\"}
	 * @return
	 */
	@ResponseBody
	@PostMapping("pullorder")
	public String pullOrder(@RequestBody String orderstring) {
		PullOder order = null;
		try {
			order = JsonUtil.toJavaBean(orderstring, PullOder.class);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			return JsonUtil.basicError(2, "解析失败 格式为\n "+"{\"userId\":213,\"orderAddress\":\"sad\",\"userName\":\"dsads\",\"userPhone\":13768111231,\"commodityList\":[{\"commodity\":{\"commodityId\":7590887542288737,\"commodityName\":\"螺蛳粉\",\"icon\":\"image/commodity/123/7590887542288737.png\",\"price\":18.5,\"discountPrice\":null,\"introduction\":\"美味的螺蛳粉\"},\"Number\":2},{\"commodity\":{\"commodityId\":7590887542288737,\"commodityName\":\"螺蛳粉\",\"icon\":\"image/commodity/123/7590887542288737.png\",\"price\":18.5,\"discountPrice\":null,\"introduction\":\"美味的螺蛳粉\"},\"Number\":1}],\"totalPrice\":28.2,\"remarks\":\"备注--多加糖\"}");
		}
		if (order.userId == null||order.userId ==0||userMapper.selectByPrimaryKey(order.userId) == null) {
			return JsonUtil.basicError(2, "用户userId--"+ order.userId +"不存在");
		}
		if (order.orderAddress == null||order.orderAddress.isEmpty()) {
			return JsonUtil.basicError(2, "用户orderAddress--"+ order.orderAddress +"不能为空");
		}
		if (order.userName == null||order.userName.isEmpty()) {
			return JsonUtil.basicError(2, "用户userName--"+ order.userName +"不能为空");
		}
		if (order.userPhone == null) {
			return JsonUtil.basicError(2, "用户userPhone--"+ order.userPhone +"不能为空");
		}
		if (order.commodityList == null||order.commodityList.isEmpty()||order.commodityList.size()==0) {
			return JsonUtil.basicError(2, "商品列表commodityList--"+ order.commodityList +"不能为空");
		}
		order.orderId = RandomUtil.randomNumber(16);
		order.createTime = new Date();
		order.orderStatu = 1;
		Orders order2 = new Orders();
		order2.setOrderId(order.orderId);
		order2.setUserId(order.userId);
		order2.setOrderAddress(order.orderAddress);
		order2.setUserName(order.userName);
		order2.setUserPhone(order.userPhone);
		String json = JsonUtil.toJson(order.commodityList);
		System.out.println(json.length());
		order2.setCommodityList(json);
		order2.setTotalPrice(order.totalPrice);
		order2.setRemarks(order.remarks);
		order2.setCreateTime(order.createTime);
		order2.setOrderStatu(order.orderStatu);
		int insert = ordersMapper.insert(order2);
		return insert==1?JsonUtil.basic(order2):JsonUtil.basicError(3, "服务器操作数据失败");
	}
	
	/**
	 * 获取所有订单
	 * @return
	 */
	@ResponseBody
	@GetMapping("getorders")
	public String getorders() {
		return JsonUtil.basic(ordersMapper.selectByExample(null));
	}
	
	/**
	 * 获取用户的所有订单
	 * @return
	 */
	@ResponseBody
	@GetMapping("getuserorders")
	public String getUserorders(@RequestParam("userId") Long userId,@RequestParam("type") Long type) {
		if (type == 0) { //已签收
			ordersExample.clear();
			ordersExample.createCriteria().andUserIdEqualTo(userId).andOrderStatuEqualTo(0);
		} else if (type == 1) { //未签收
			ordersExample.clear();
			ordersExample.createCriteria().andUserIdEqualTo(userId).andOrderStatuEqualTo(1);
		} else { //所有
			ordersExample.clear();
			ordersExample.createCriteria().andUserIdEqualTo(userId);
		}
		return JsonUtil.basic(ordersMapper.selectByExample(ordersExample));
	}
	
	/**
	 * 确认收货
	 * @param orderId 订单id
	 * @param userId 用户id
	 * @return
	 */
	@ResponseBody
	@GetMapping("receiveorder")
	public String receiveOrder(@RequestParam("orderId") Long orderId,@RequestParam("userId") Long userId) {
		Orders orders = ordersMapper.selectByPrimaryKey(orderId);
		if (orders == null) {
			return JsonUtil.basicError(1, "没有该订单");
		}
		User user = userMapper.selectByPrimaryKey(userId);
		if (user == null) {
			return JsonUtil.basicError(1, "没有该用户");
		} 
		ordersExample.clear();
		ordersExample.createCriteria().andOrderIdEqualTo(orderId).andUserIdEqualTo(userId);
		List<Orders> selectByExample = ordersMapper.selectByExample(ordersExample);
		if (selectByExample == null||selectByExample.isEmpty()) {
			return JsonUtil.basicError(1, "非该用户所属订单");
		}
		Orders orders2 = selectByExample.get(0);
		if (orders2.getOrderStatu() == 0) {
			return JsonUtil.basic(0,orders2,"已经确认收货，无需重复确认");
		}
		orders2.setOrderStatu(0);
		int i = ordersMapper.updateByPrimaryKey(orders2);
		return i==1?JsonUtil.basic(0,orders2,"已确认收货"):JsonUtil.basicError(3, "服务器更新数据出错");
	}
	
	static class PullOder{
		public PullOder() {}
		public Long orderId;
	    public Long userId;
	    public String orderAddress;
	    public String userName;
	    public Long userPhone;
	    public List<CommodityList> commodityList;
	    public BigDecimal totalPrice;
	    public String remarks;
	    public Date createTime;
	    public Integer orderStatu;
	}
	static class CommodityList{
		public CommodityList() {
			
		}
		public Commodity commodity;
		public int Number;
	}
	
	
}
