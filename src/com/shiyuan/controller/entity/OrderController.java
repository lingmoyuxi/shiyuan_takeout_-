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

//������
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
	 * �ύ����
	 * @param ʾ��Json {\"userId\":213,\"orderAddress\":\"sad\",\"userName\":\"dsads\",\"userPhone\":13768111231,\"commodityList\":[{\"commodity\":{\"commodityId\":7590887542288737,\"commodityName\":\"���Ϸ�\",\"icon\":\"image/commodity/123/7590887542288737.png\",\"price\":18.5,\"discountPrice\":null,\"introduction\":\"��ζ�����Ϸ�\"},\"Number\":2},{\"commodity\":{\"commodityId\":7590887542288737,\"commodityName\":\"���Ϸ�\",\"icon\":\"image/commodity/123/7590887542288737.png\",\"price\":18.5,\"discountPrice\":null,\"introduction\":\"��ζ�����Ϸ�\"},\"Number\":1}],\"totalPrice\":28.2,\"remarks\":\"��ע--�����\"}
	 * @return
	 */
	@ResponseBody
	@PostMapping("pullorder")
	public String pullOrder(@RequestBody String orderstring) {
		PullOder order = null;
		try {
			order = JsonUtil.toJavaBean(orderstring, PullOder.class);
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			return JsonUtil.basicError(2, "����ʧ�� ��ʽΪ\n "+"{\"userId\":213,\"orderAddress\":\"sad\",\"userName\":\"dsads\",\"userPhone\":13768111231,\"commodityList\":[{\"commodity\":{\"commodityId\":7590887542288737,\"commodityName\":\"���Ϸ�\",\"icon\":\"image/commodity/123/7590887542288737.png\",\"price\":18.5,\"discountPrice\":null,\"introduction\":\"��ζ�����Ϸ�\"},\"Number\":2},{\"commodity\":{\"commodityId\":7590887542288737,\"commodityName\":\"���Ϸ�\",\"icon\":\"image/commodity/123/7590887542288737.png\",\"price\":18.5,\"discountPrice\":null,\"introduction\":\"��ζ�����Ϸ�\"},\"Number\":1}],\"totalPrice\":28.2,\"remarks\":\"��ע--�����\"}");
		}
		if (order.userId == null||order.userId ==0||userMapper.selectByPrimaryKey(order.userId) == null) {
			return JsonUtil.basicError(2, "�û�userId--"+ order.userId +"������");
		}
		if (order.orderAddress == null||order.orderAddress.isEmpty()) {
			return JsonUtil.basicError(2, "�û�orderAddress--"+ order.orderAddress +"����Ϊ��");
		}
		if (order.userName == null||order.userName.isEmpty()) {
			return JsonUtil.basicError(2, "�û�userName--"+ order.userName +"����Ϊ��");
		}
		if (order.userPhone == null) {
			return JsonUtil.basicError(2, "�û�userPhone--"+ order.userPhone +"����Ϊ��");
		}
		if (order.commodityList == null||order.commodityList.isEmpty()||order.commodityList.size()==0) {
			return JsonUtil.basicError(2, "��Ʒ�б�commodityList--"+ order.commodityList +"����Ϊ��");
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
		return insert==1?JsonUtil.basic(order2):JsonUtil.basicError(3, "��������������ʧ��");
	}
	
	/**
	 * ��ȡ���ж���
	 * @return
	 */
	@ResponseBody
	@GetMapping("getorders")
	public String getorders() {
		return JsonUtil.basic(ordersMapper.selectByExample(null));
	}
	
	/**
	 * ��ȡ�û������ж���
	 * @return
	 */
	@ResponseBody
	@GetMapping("getuserorders")
	public String getUserorders(@RequestParam("userId") Long userId,@RequestParam("type") Long type) {
		if (type == 0) { //��ǩ��
			ordersExample.clear();
			ordersExample.createCriteria().andUserIdEqualTo(userId).andOrderStatuEqualTo(0);
		} else if (type == 1) { //δǩ��
			ordersExample.clear();
			ordersExample.createCriteria().andUserIdEqualTo(userId).andOrderStatuEqualTo(1);
		} else { //����
			ordersExample.clear();
			ordersExample.createCriteria().andUserIdEqualTo(userId);
		}
		return JsonUtil.basic(ordersMapper.selectByExample(ordersExample));
	}
	
	/**
	 * ȷ���ջ�
	 * @param orderId ����id
	 * @param userId �û�id
	 * @return
	 */
	@ResponseBody
	@GetMapping("receiveorder")
	public String receiveOrder(@RequestParam("orderId") Long orderId,@RequestParam("userId") Long userId) {
		Orders orders = ordersMapper.selectByPrimaryKey(orderId);
		if (orders == null) {
			return JsonUtil.basicError(1, "û�иö���");
		}
		User user = userMapper.selectByPrimaryKey(userId);
		if (user == null) {
			return JsonUtil.basicError(1, "û�и��û�");
		} 
		ordersExample.clear();
		ordersExample.createCriteria().andOrderIdEqualTo(orderId).andUserIdEqualTo(userId);
		List<Orders> selectByExample = ordersMapper.selectByExample(ordersExample);
		if (selectByExample == null||selectByExample.isEmpty()) {
			return JsonUtil.basicError(1, "�Ǹ��û���������");
		}
		Orders orders2 = selectByExample.get(0);
		if (orders2.getOrderStatu() == 0) {
			return JsonUtil.basic(0,orders2,"�Ѿ�ȷ���ջ��������ظ�ȷ��");
		}
		orders2.setOrderStatu(0);
		int i = ordersMapper.updateByPrimaryKey(orders2);
		return i==1?JsonUtil.basic(0,orders2,"��ȷ���ջ�"):JsonUtil.basicError(3, "�������������ݳ���");
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
