package com.shiyuan.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUtil {
	/**
	 * 生成随机固定长度的字符串
	 * @param length 字符串长度
	 * @return 随机字符串
	 */
	public static String randomString(int length) {
		String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; //待抽取字符串
		Random random=new Random();
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(str.charAt(random.nextInt(str.length())));
		}
		return sb.toString();
	}
	
	/**
	 * 生成随机固定长度的数字字符串
	 * @param length 数字字符串长度
	 * @return 随机数字字符串
	 */
	public static Long randomNumber(int length) {
		String str="0123456789"; //待抽取字符串
		Random random=new Random();
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(str.charAt(random.nextInt(str.length())));
		}
		return Long.valueOf(sb.toString());
	}
	
	/**
	 *  随机获取数组内的部分数据
	 * @param <T> 泛型
	 * @param arrayList 数据数组
	 * @param length 抽取的数据长度（小与数组数据长度）
	 * @return 随机抽取数组元素
	 */
	public static <T> ArrayList<T> randomList(List<T> arrayList, int length) {
		
		
		int[] listIndex =new int[length]; // 此处打算用来排除 随机生成相同的数据   没弄出效果
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
