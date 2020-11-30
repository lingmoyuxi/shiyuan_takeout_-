package com.shiyuan.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.List;

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
				sBuffer.append("\""+ field.getName() + "\":");  //�ֶ���
				//�����ֶ����Ͳ�ͬ׷������
				if (type.equals("class java.lang.String")) {  //�ַ����������
					sBuffer.append("\""+ (field.get(object) == null?"":field.get(object)) + "\"");
				} else if (type.equals("class java.lang.Long")||type.equals("class java.lang.Integer")||type.equals("class java.math.BigDecimal")||type.equals("class java.lang.Float")) { //��ֵ�Ϳ�ֵ����
					sBuffer.append(field.get(object) == null?"null":field.get(object));
				} else if (type.equals("interface java.util.List")||type.equals("class java.util.ArrayList")) { //�б�תtoJson()��һ������
					sBuffer.append(field.get(object) == null?"[]":toJson(field.get(object)));
				} else if (type.equals("class java.util.Date")) { //����Date 
					SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
					sBuffer.append("\""+ (field.get(object) == null?"":ft.format(field.get(object))) + "\"");
				} else { //δ��Ӵ�����
					System.out.println("δָ�����ͣ�������");
					System.out.println(field.getName() + " --" + field.getGenericType() + "---" + field.get(object));
				}
				if (i >= fields.length - 1) {  //���һ�׷�� ,
					continue;
				}
				sBuffer.append(",");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		sBuffer.append("}");
		return sBuffer.toString();
	}
}
