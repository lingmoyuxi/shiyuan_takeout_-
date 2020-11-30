package com.shiyuan.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	/**
	 * ��������Json���ݸ�ʽ
	 * @param data ����
	 * @return Json�ַ���
	 */
	public static String basic(Object data) {
		return basic(0, data, null);
	}
	
	public static String basic(String data) {
		return basic(0, data, null);
	}
	
	/**
	 * ��������Json���ݸ�ʽ(���ڴ���ʱ)
	 * @param status ״̬��
	 * @param message ��Ϣ
	 * @return Json�ַ���
	 */
	public static String basicError(int status, String message) {
		return basic(status, null, message);
	}

	/**
	 * ��������Json���ݸ�ʽ
	 * @param status ״̬��
	 * @param data ����
	 * @param message ��Ϣ
	 * @return Json�ַ���
	 */
	public static String basic(int status, Object data, String message) {
		return "{\"status\":" + status + ",\"message\": \"" + (message==null?"":message) + "\",\"data\":" + (data==null?"\"\"":toJson(data)) + "}";
	}
	public static String basic(int status, String data, String message) {
		return "{\"status\":" + status + ",\"message\": \"" + (message==null?"":message) + "\",\"data\":" + (data==null?"\"\"":data) + "}";
	}
	
	
	
	/**
	 * objectת����Json�ַ���
	 * @param object ת������
	 * @return
	 */
	public static String toJson(Object object) {
		StringBuffer sBuffer = new StringBuffer(); //����
		if (object instanceof List) {  //�Ƿ���List
			sBuffer.append("[");
			List<?> list = (List<?>) object;  //ǿ��ת��
			for (int i = 0; i < list.size(); i++) {  //��ȡ�����ڵ�������
				sBuffer.append(classToJson(list.get(i)));  //����ת��Json
				if (i >= list.size() - 1) {   //���һ�������   ,
					continue;
				}
				sBuffer.append(",");
			}
			sBuffer.append("]");
		} else { //��������ֱ��ת��
			sBuffer.append(classToJson(object));
		}
		return sBuffer.toString();
	}
	
	/**
	 * jsonתJavaBean����
	 * @param <T>
	 * @param json json����
	 * @param valueType JavaBean.class
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> T toJavaBean(String json,Class<T> valueType) throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper().readValue(json,valueType);
	}
	
	/**
	 * classת����Json�ַ���
	 * @param object object����
	 * @return json�ַ���
	 */
	private static String classToJson(Object object) {
		StringBuffer sBuffer = new StringBuffer();
		// ������ƻ�ȡClass�ڵ�����
		Class<?> cla = object.getClass(); //��ȡclass
		Field[] fields = cla.getDeclaredFields();//��ȡ�������ֶ�
		
		sBuffer.append("{");
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setAccessible(true);  //�������private�������ֶ�
			String type = field.getGenericType().toString(); //�ֶ�����
			try {
				//�����ֶ����Ͳ�ͬ׷������
				if (type.equals("class java.lang.String")) {  //�ַ����������
					if (i !=0 ) {
						sBuffer.append(",");
					}
					sBuffer.append("\""+ field.getName() + "\":");  //�ֶ���
					sBuffer.append("\""+ (field.get(object) == null?"":field.get(object)) + "\"");
				} else if (type.equals("long")||type.equals("int")||type.equals("class java.lang.Long")||type.equals("class java.lang.Integer")||type.equals("class java.math.BigDecimal")||type.equals("class java.lang.Float")) { //��ֵ�Ϳ�ֵ����
					if (i !=0 ) {
						sBuffer.append(",");
					}
					sBuffer.append("\""+ field.getName() + "\":");  //�ֶ���
					sBuffer.append(field.get(object) == null?"null":field.get(object));
				} else if (type.equals("interface java.util.List")||type.equals("class java.util.ArrayList")||type.contains("java.util.List")) { //�б�תtoJson()��һ������
					if (i !=0 ) {
						sBuffer.append(",");
					}
					sBuffer.append("\""+ field.getName() + "\":");  //�ֶ���
					sBuffer.append(field.get(object) == null?"[]":toJson(field.get(object)));
				} else if (type.equals("class java.util.Date")) { //����Date 
					if (i !=0 ) {
						sBuffer.append(",");
					}
					sBuffer.append("\""+ field.getName() + "\":");  //�ֶ���
					SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
					sBuffer.append("\""+ (field.get(object) == null?"":ft.format(field.get(object))) + "\"");
				}else if (type.startsWith("class com.shiyuan.model")) { 
					if (i !=0 ) {
						sBuffer.append(",");
					}
					sBuffer.append("\""+ field.getName() + "\":");  //�ֶ���
					sBuffer.append(field.get(object) == null?"{}":toJson(field.get(object)));
				}
				else { //δ��Ӵ�����
					System.out.println("δָ�����ͣ�������");
					System.out.println(field.getName() + " --" + field.getGenericType() + "---" + field.get(object));
					continue;
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		sBuffer.append("}");
		return sBuffer.toString();
	}
}
