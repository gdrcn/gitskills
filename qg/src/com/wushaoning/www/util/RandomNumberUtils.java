package com.wushaoning.www.util;

import java.util.Random;

/**
 * ����������ֵĹ�����
 * @author 10620
 *
 */
public class RandomNumberUtils {
	
	/**
	 * �������6λ����
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
