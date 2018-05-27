package com.wushaoning.www.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
* MD5���ܹ���
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
			throw new RuntimeException("û��md5����㷨��");
		}
		return new BigInteger(1, secretBytes).toString(16);
	}
}
