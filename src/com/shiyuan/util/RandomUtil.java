package com.shiyuan.util;

import java.util.ArrayList;
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
	public static <T> ArrayList<T> randomList(List<T> arrayList, int length) {
		
		
		int[] listIndex =new int[length]; // �˴����������ų� ���������ͬ������   ûŪ��Ч��
		int count = 0;
		Random random=new Random();
		ArrayList<T> resultList = new ArrayList<T>();
		while (count < length) {
			int number = random.nextInt(arrayList.size());
			for (int s : listIndex) {
	            if (s == number){
	            	continue;
	            }
	        }
			listIndex[count] = number;
			resultList.add(arrayList.get(number));
			count++;
		}
		return resultList;
	}
}
