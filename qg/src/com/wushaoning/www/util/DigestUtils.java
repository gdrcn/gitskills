package com.wushaoning.www.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
* MD5加密工具
* @author 10620
* @param plainText
* @return
*/
public class DigestUtils {

	public static String md5(String plainText) {
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(
					plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有md5这个算法！");
		}
		return new BigInteger(1, secretBytes).toString(16);
	}
}
