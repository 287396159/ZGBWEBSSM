package com.dmatek.zgb.tool.Inetaddress;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class InetAddressTools {
	public static  String getRequestIp(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	/**
	 * 获取本地所有的IPv4地址，包括多网卡，虚拟网卡等出现的多IP情况
	 * @return
	 * @throws SocketException 
	 */
	public static List<String> getAllLocalIps() throws SocketException{
		List<String> ips = new ArrayList<String>();
		Enumeration<NetworkInterface> inetaddress = null;
		NetworkInterface networkInterface = null;
		Enumeration<InetAddress> inetaddresses = null;
		inetaddress = NetworkInterface.getNetworkInterfaces();
		while (inetaddress.hasMoreElements()) {
			networkInterface = inetaddress.nextElement();
			inetaddresses = networkInterface.getInetAddresses();
			while (inetaddresses.hasMoreElements()) {
				InetAddress inetAddress = inetaddresses.nextElement();
				if(null != inetAddress && inetAddress instanceof Inet4Address 
				   && !inetAddress.isLoopbackAddress() && inetAddress.getHostAddress().indexOf(":") < 0){
					String ip = inetAddress.getHostAddress();
					ips.add(ip);
				}
			}
		}
		return ips;
	}
	/**
	 * 获取一个本地IPv4地址
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getLocalIp() throws UnknownHostException{
		return InetAddress.getLocalHost().getHostAddress();
	}
	/**
	 * 判断参数ip是否属于本机的IP地址
	 * @param ip
	 * @return
	 * @throws SocketException
	 */
	public static boolean isLocalIp(String ip) throws SocketException{
		List<String> allIps = getAllLocalIps();
		return null != allIps?allIps.contains(ip):false;
	}
	/**
	 * 已经IP判断这个IP是否是本机的IP,
	 * 若是则返回这个IP,
	 * 若不是则返回一个本机IP
	 * @param ip
	 * @return
	 * @throws SocketException
	 * @throws UnknownHostException
	 */
	public static String getLocalIp(String ip) throws SocketException, UnknownHostException {
		if(isLocalIp(ip)){
			return ip;
		} else {
			return getLocalIp();
		}
	}
}
