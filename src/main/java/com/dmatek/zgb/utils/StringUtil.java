package com.dmatek.zgb.utils;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {
	private static final DateFormat LocalDataFormat = new SimpleDateFormat("yyyyMMddHH_mm_ss.SSS");
	/**
	 * "file:/home/whf/cn/fh" -> "/home/whf/cn/fh"
	 * "jar:file:/home/whf/foo.jar!cn/fh" -> "/home/whf/foo.jar"
	 */
	public static String getRootPath(URL url) {
		String fileUrl = url.getFile();
		int pos = fileUrl.indexOf('!');
		if (-1 == pos) {
			return fileUrl;
		}
		return fileUrl.substring(5, pos);
	}
	/**
	 * "cn.fh.lightning" -> "cn/fh/lightning"
	 * 
	 * @param name
	 * @return
	 */
	public static String dotToSplash(String name) {
		return name.replaceAll("\\.", "/");
	}
	/**
	 * "Apple.class" -> "Apple"
	 */
	public static String trimExtension(String name) {
		int pos = name.indexOf('.');
		if (-1 != pos) {
			return name.substring(0, pos);
		}
		return name;
	}

	/**
	 * /application/home -> /home
	 * 
	 * @param uri
	 * @return
	 */
	public static String trimURI(String uri) {
		String trimmed = uri.substring(1);
		int splashIndex = trimmed.indexOf('/');
		return trimmed.substring(splashIndex);
	}
	
	public static String convertVersion(int ver){
		byte year = (byte) (ver >> 24);
		byte month = (byte) (ver >> 16);
		byte day = (byte) (ver >> 8);
		byte v = (byte) (ver);
		return "V " + String.format("%02X", year)
				+ String.format("%02X", month)
				+ String.format("%02X", day) + Integer.toHexString(v & 0xFF);
	}
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] b = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
	        b[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
	                .digit(s.charAt(i + 1), 16));
	    }
	    return b;
	}
	/***
	 * 获取本地的时间字符串(包括毫秒部分)
	 * @return
	 */
	public static String getLocalTimeAllString() {
		return LocalDataFormat.format(new Date());
	}
}
