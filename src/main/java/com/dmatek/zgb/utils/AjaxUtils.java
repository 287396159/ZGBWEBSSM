package com.dmatek.zgb.utils;

import javax.servlet.http.HttpServletRequest;
/**
 * ajax工具类
 * @author zhangfu
 * @data 2019年4月12日 下午3:50:25
 * @Description
 */
public class AjaxUtils {
	/**
	 * 判断是否是ajax请求
	 * @param req
	 * @return
	 */
	public static boolean IsAjax(HttpServletRequest req) {
		String contentTypeHeader = req.getHeader("Content-Type");
		String acceptHeader = req.getHeader("Accept");
		String xRequestedWith = req.getHeader("X-Requested-With");
		if ((contentTypeHeader != null && contentTypeHeader.contains("application/json")) ||
		    (acceptHeader != null && acceptHeader.contains("application/json")) || 
		    "XMLHttpRequest".equalsIgnoreCase(xRequestedWith)) {
			return true;
		}
		return false;
	}
}
