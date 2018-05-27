package com.wushaoning.www.util;

/**
 * 数字处理的工具类
 * 
 * @author 10620
 *
 */
public class IsDigitUtils {
	public static final String JUDGE_ONE = "one";
	public static final String JUDGE_TWO = "two";
	public static final String JUDGE_THREE = "three";
	public static final String JUDGE_FOUR = "four";
	public static final String JUDGE_FIVE = "five";
	/**
	 * 判断是否为字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDigits(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 得到星级
	 * @param star
	 * @return
	 */
	public static int getStar(String star) {
		if (JUDGE_FIVE.equals(star)) {
			return 5;
		} else if (JUDGE_FOUR.equals(star)) {
			return 4;
		} else if (JUDGE_THREE.equals(star)) {
			return 3;
		} else if (JUDGE_TWO.equals(star)) {
			return 2;
		} else if (JUDGE_ONE.equals(star)) {
			return 1;
		} else {
			return 0;
		}
	}
}
