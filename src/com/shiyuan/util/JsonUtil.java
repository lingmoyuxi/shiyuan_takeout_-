package com.shiyuan.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.List;

public class JsonUtil {
	/**
	 * 基本返回Json数据格式
	 * @param data 数据
	 * @return Json字符串
	 */
	public static String basic(Object data) {
		return basic(0, data, null);
	}
	
	public static String basic(String data) {
		return basic(0, data, null);
	}
	
	/**
	 * 基本返回Json数据格式(用于错误时)
	 * @param status 状态码
	 * @param message 信息
	 * @return Json字符串
	 */
	public static String basicError(int status, String message) {
		return basic(status, null, message);
	}

	/**
	 * 基本返回Json数据格式
	 * @param status 状态码
	 * @param data 数据
	 * @param message 信息
	 * @return Json字符串
	 */
	public static String basic(int status, Object data, String message) {
		return "{\"status\":" + status + ",\"message\": \"" + (message==null?"":message) + "\",\"data\":" + (data==null?"\"\"":toJson(data)) + "}";
	}
	public static String basic(int status, String data, String message) {
		return "{\"status\":" + status + ",\"message\": \"" + (message==null?"":message) + "\",\"data\":" + (data==null?"\"\"":data) + "}";
	}
	
	
	
	/**
	 * object转换成Json字符串
	 * @param object 转换对象
	 * @return
	 */
	public static String toJson(Object object) {
		StringBuffer sBuffer = new StringBuffer(); //缓存
		if (object instanceof List) {  //是否是List
			sBuffer.append("[");
			List<?> list = (List<?>) object;  //强制转换
			for (int i = 0; i < list.size(); i++) {  //抽取数组内单个对象
				sBuffer.append(classToJson(list.get(i)));  //对象转换Json
				if (i >= list.size() - 1) {   //最后一个不添加   ,
					continue;
				}
				sBuffer.append(",");
			}
			sBuffer.append("]");
		} else { //不是数组直接转换
			sBuffer.append(classToJson(object));
		}
		return sBuffer.toString();
	}
	
	/**
	 * class转换成Json字符串
	 * @param object object对象
	 * @return json字符串
	 */
	private static String classToJson(Object object) {
		StringBuffer sBuffer = new StringBuffer();
		// 反射机制获取Class内的属性
		Class<?> cla = object.getClass(); //获取class
		Field[] fields = cla.getDeclaredFields();//获取声明的字段
		
		sBuffer.append("{");
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setAccessible(true);  //允许访问private声明的字段
			String type = field.getGenericType().toString(); //字段类型
			try {
				sBuffer.append("\""+ field.getName() + "\":");  //字段名
				//根据字段类型不同追加数据
				if (type.equals("class java.lang.String")) {  //字符串添加引号
					sBuffer.append("\""+ (field.get(object) == null?"":field.get(object)) + "\"");
				} else if (type.equals("class java.lang.Long")||type.equals("class java.lang.Integer")||type.equals("class java.math.BigDecimal")||type.equals("class java.lang.Float")) { //数值型空值处理
					sBuffer.append(field.get(object) == null?"null":field.get(object));
				} else if (type.equals("interface java.util.List")||type.equals("class java.util.ArrayList")) { //列表转toJson()进一步处理
					sBuffer.append(field.get(object) == null?"[]":toJson(field.get(object)));
				} else if (type.equals("class java.util.Date")) { //处理Date 
					SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
					sBuffer.append("\""+ (field.get(object) == null?"":ft.format(field.get(object))) + "\"");
				} else { //未添加待处理
					System.out.println("未指定类型，待处理");
					System.out.println(field.getName() + " --" + field.getGenericType() + "---" + field.get(object));
				}
				if (i >= fields.length - 1) {  //最后一项不追加 ,
					continue;
				}
				sBuffer.append(",");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		sBuffer.append("}");
		return sBuffer.toString();
	}
}
