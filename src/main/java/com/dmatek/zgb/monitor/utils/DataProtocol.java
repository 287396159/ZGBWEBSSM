package com.dmatek.zgb.monitor.utils;

public class DataProtocol {
	public static final byte tag_head = (byte) 0xFE;
	public static final byte tag_type1 = 0x04;//警报类型
	public static final byte tag_type2 = 0x03;//一般数据包
	public static final byte tag_end = (byte) 0xFD;
	public static final byte refer_type = 0x02;
	public static final byte refer_end = (byte)0xFD;
	public static final byte node_type = 0x01;
	public static final byte RssiThreshold = 3;/*信号阀值*/
	
	public static final int ZGB_MINPACK_SIZE = 4;
	public static final int SINGLE_NODEPACK_SIZE = 12;// 数据节点上报数据包的长度
	public static final int SINGLE_REFERPACK_SIZE = 14;// 参考点上报数据包的长度
	
	public static final byte tag_setting_head = (byte) 0xFA;
	public static final byte tag_setting_end = (byte) 0xFB;
	
	public static final byte type_reset = 0x00;
	
	public static final byte type_read_ip = 0x02;
	
	public static final byte type_read_port = 0x04;
	
	public static final byte type_read_wifiName = 0x06;
	
	public static final byte type_read_wifiPsw = 0x08;
	
	public static final byte type_read_ipMode = 0x0A;
	
	public static final byte type_read_nodeip = 0x0C;
	
	public static final byte type_read_nodemask = 0x0E;
	
	public static final byte type_read_nodegateway = 0x10;
	// 查詢設備的類型及固件版本
	public static final byte type_read_dev_type_version = 0x11;
	
	public static final byte type_read_nodechannel = 0x14;
	
	public static final byte type_read_nodearroundrssi = 0x15;
	
	public static final byte type_read_nodewifiipmode = 0x17;
	
	public static final byte type_read_nodewifistaticip = 0x19;
	
	public static final byte type_set_serverip = 0x01;// 设置server的ip
	
	public static final byte type_set_serverport = 0x03;// 设置server的port
	
	public static final byte type_set_wifiname = 0x05;// 设置Wifi名称
	
	public static final byte type_set_wifipsw = 0x07;// 设置wifi密码
	
	public static final byte type_set_ipmode = 0x09;// 设置ip模式
	
	public static final byte type_set_nodeselfip = 0x0B;// 设置节点IP
	
	public static final byte type_set_nodeselfmask = 0x0D;// 设置节点掩码
	
	public static final byte type_set_nodeselfgateway = 0x0F;// 设置节点网关
	
	public static final byte type_set_wifiipmode = 0x16;// 设置节点wifi时的ip模式
	
	public static final byte type_set_wifistaticip = 0x18;// 设置节点wifi时的静态Ip
	
	public static final byte type_refer_reset = 0x27;
	
	public static final byte type_read_refertypeversion = 0x24;
	
	public static final byte type_read_referreceivesig = 0x21;
	
	public static final byte type_read_refersigk = 0x23;
	
	public static final byte type_set_referreceivesig = 0x20;
	
	public static final byte type_set_refersigk = 0x22;
	
	public static final byte type_arround_setting_head = (byte) 0xFC;
	
	public static final byte type_arround_setting_end = (byte) 0xFB;
	
	public static final byte type_arround_searchnodes = 0x01;// 搜索節點設備
	
	public static final byte type_arround_searchrefers = 0x41;// 搜索参考点设备
	
	public static final byte type_arround_readrefertypeversion = 0x42;
	
	public static final byte type_arround_readrefersigthreshold = 0x46;
	
	public static final byte type_arround_readrefersigK = 0x48;
	
	public static final byte type_arround_readbetweenrssi = 0x4A;
	
	public static final byte type_arround_resetrefer = 0x49;
	
	public static final byte type_arround_setrefersigthreshold = 0x45;
	
	public static final byte type_arround_setrefersigk = 0x47;
	// 讀取節點類型和版本
	public static final byte type_arround_readnodetypeversion = 0x02;
	
	public static final byte type_arround_readnodeserverip = 0x06;
	
	public static final byte type_arround_readnodeserverport = 0x08;
	
	public static final byte type_arround_readnodewifiname = 0x0A;
	
	public static final byte type_arround_readnodewifipsw = 0x0C;
	
	public static final byte type_arround_readnodelanipmode = 0x0F;
	
	public static final byte type_arround_readnodelanstaticip = 0x11;
	
	public static final byte type_arround_readnodelanmask = 0x13;
	
	public static final byte type_arround_readnodelangateway = 0x15;
	
	public static final byte type_arround_readnodelaststate = 0x17;
	
	public static final byte type_arround_readnodewifiipmode = 0x19;
	
	public static final byte type_arround_readnodewifistaticip = 0x1B;
	
	public static final byte type_arround_readnodereset = 0x0D;
	
	public static final byte type_arround_setnodeserverip = 0x05;
	
	public static final byte type_arround_setnodeserverport = 0x07;
	
	public static final byte type_arround_setnodelanipmode = 0x0E;
	
	public static final byte type_arround_setnodesubmask = 0x12;
	
	public static final byte type_arround_setnodegateway = 0x14;
	
	public static final byte type_arround_setnodelanip = 0x10;
	
	public static final byte type_arround_setnodewifiname = 0x09;
	
	public static final byte type_arround_setnodewifipsw = 0x0B;
	
	public static final byte type_arround_setnodewifiipmodel = 0x18;
	
	public static final byte type_arround_setnodewifistaticip = 0x1A;
	
	public static final byte type_arround_searchnodewifirssi = 0x16;
	
	public static final byte type_arround_readatcommand = 0x1C;
	
	/**
	 * 
	 * @param bytes
	 * @return
	 */
  	public static String bytesToHex(byte[] bytes) 
	{
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for(byte b : bytes)
        {
            buf.append(String.format("%02X", new Integer(b & 0xff)));
        }
        return buf.toString();
    }
	/**
	 * 
	 * @param bytes
	 * @param ch
	 * @param start
	 * @return
	 */
	public static int FindChar(byte[] bytes,byte ch,int start,int len){
		if (start >= len) {
			return -1;
		}
		for (int i = start; i < len; i++) {
			if(bytes[i] == ch){
				return i;
			}
		}
		return -1;
	}
}
