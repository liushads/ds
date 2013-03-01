package com.ppsea.ds.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class MD5 {
	public static String encode(String str, String pswd, String proxyKey) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		if (pswd == null)
			pswd = "";
		byte[] d = md5.digest((pswd + proxyKey + str).getBytes());
		return byte2hex(d, d.length - 4, d.length);
	}
	public static String encode(String param) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] d = md5.digest((param).getBytes());
		return byte2hex(d, d.length - 4, d.length);
	}
	public static String random() {
		return String.valueOf(new Random().nextInt(10000));
	}

	public static String byte2hex(byte[] b, int start, int end) {
		String hs = "";
		String tmp = "";
		for (int n = start; n < end; n++) {
			tmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (tmp.length() == 1) {
				hs = hs + "0" + tmp;
			} else {
				hs = hs + tmp;
			}
		}
		tmp = null;
		return hs;
	}

	public static String encodeAndRandom(String str, String pswd, String proxyKey) throws NoSuchAlgorithmException {
		return encode(str, pswd, proxyKey) + "-" + random() + "-" + getTime();
	}
	
	private static String getTime(){
		return Integer.toHexString((int) (System.currentTimeMillis() / 1000));
		
	}
	
}
