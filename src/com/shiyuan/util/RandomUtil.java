package com.shiyuan.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class RandomUtil {
	/**
	 * ��������̶����ȵ��ַ���
	 * @param length �ַ�������
	 * @return ����ַ���
	 */
	public static String randomString(int length) {
		String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; //����ȡ�ַ���
		Random random=new Random();
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(str.charAt(random.nextInt(str.length())));
		}
		return sb.toString();
	}
	
	/**
	 * ��������̶����ȵ������ַ���
	 * @param length �����ַ�������
	 * @return ��������ַ���
	 */
	public static Long randomNumber(int length) {
		String str="0123456789"; //����ȡ�ַ���
		Random random=new Random();
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(str.charAt(random.nextInt(str.length())));
		}
		return Long.valueOf(sb.toString());
	}
	
	/**
	 *  �����ȡ�����ڵĲ�������
	 * @param <T> ����
	 * @param arrayList ��������
	 * @param length ��ȡ�����ݳ��ȣ�С���������ݳ��ȣ�
	 * @return �����ȡ����Ԫ��
	 */
	public static <T> List<T> randomList(List<T> list, int length) {
		List<T> initList = new ArrayList<>();
		initList.addAll(list);
		List<T> resultList = new ArrayList<T>();
		Random random=new Random();
		while(resultList.size() < length) {
			int number = random.nextInt(initList.size());
			resultList.add(initList.get(number));
			if (list.size() >= length) {  //������鳤�ȴ��ڵ���length�� ��֤���ص����鲻�ᱻ�ظ���ȡ
				initList.remove(number);
			}
		}
		return resultList;
	}
}
