package com.wushaoning.www.util;

/**
 * ���ִ���Ĺ�����
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
	 * �ж��Ƿ�Ϊ�ַ���
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
	 * �õ��Ǽ�
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
