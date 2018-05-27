package com.wushaoning.www.util;

import java.util.Random;

/**
 * 生成随机数字的工具类
 * @author 10620
 *
 */
public class RandomNumberUtils {
	
	/**
	 * 生成随机6位数字
	 * @param digit
	 * @return
	 */
	public static String getRandomNum(int digit) {
		Random random = new Random();
		double pross = (1 + random.nextDouble()) * Math.pow(10, digit);
		String result = String.valueOf(pross);
		return result.substring(1, digit + 1);
	}
}
