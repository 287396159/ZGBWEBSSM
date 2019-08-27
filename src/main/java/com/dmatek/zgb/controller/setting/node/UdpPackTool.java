package com.dmatek.zgb.controller.setting.node;

import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeAtCommandUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeGateWayUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeLanIpModeUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeLanStaticIpUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeLastState;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeMaskUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeServerIpUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeServerPortUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeTypeVersionUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeWifiIpModeUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeWifiNameUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeWifiPswUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeWifiRssiUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeWifiStaticIpUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ResetArroundNodeUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.SearchArroundNodesUdpPack;
import com.dmatek.zgb.setting.arround.node.setting.pack.SetArroundNodeGateWayUdpPack;
import com.dmatek.zgb.setting.arround.node.setting.pack.SetArroundNodeLanIpModeUdpPack;
import com.dmatek.zgb.setting.arround.node.setting.pack.SetArroundNodeLanIpUdpPack;
import com.dmatek.zgb.setting.arround.node.setting.pack.SetArroundNodeServerIpUdpPack;
import com.dmatek.zgb.setting.arround.node.setting.pack.SetArroundNodeServerPortUdpPack;
import com.dmatek.zgb.setting.arround.node.setting.pack.SetArroundNodeSubMaskUdpPack;
import com.dmatek.zgb.setting.arround.node.setting.pack.SetArroundNodeWifiIpModelUdpPack;
import com.dmatek.zgb.setting.arround.node.setting.pack.SetArroundNodeWifiNameUdpPack;
import com.dmatek.zgb.setting.arround.node.setting.pack.SetArroundNodeWifiPswUdpPack;
import com.dmatek.zgb.setting.arround.node.setting.pack.SetArroundNodeWifiStaticIpUdpPack;
import com.dmatek.zgb.setting.arround.refer.read.pack.ReadArroundBetweenSigUdpPack;
import com.dmatek.zgb.setting.arround.refer.read.pack.ReadArroundReferSigKUdpPack;
import com.dmatek.zgb.setting.arround.refer.read.pack.ReadArroundReferSigThresholdUdpPack;
import com.dmatek.zgb.setting.arround.refer.read.pack.ReadArroundReferTypeVersionUdpPack;
import com.dmatek.zgb.setting.arround.refer.read.pack.ResetArroundReferUdpPack;
import com.dmatek.zgb.setting.arround.refer.read.pack.SearchArroundRefersUdpPack;
import com.dmatek.zgb.setting.arround.refer.setting.pack.SetArroundReferSigKUdpPack;
import com.dmatek.zgb.setting.arround.refer.setting.pack.SetArroundReferSigThresholdUdp;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadIPUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadIpModeUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadNodeArroundWifiRssiUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadNodeChannelUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadNodeGateWayUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadNodeIpUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadNodeMaskUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadNodeTypeVersionUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadNodeWifiIpModeUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadNodeWifiStaticIpUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadPortUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadWifiNameUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadWifiPswUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ResetNodePack;
import com.dmatek.zgb.setting.directly.node.set.pack.SetNodeIpModeUdpPack;
import com.dmatek.zgb.setting.directly.node.set.pack.SetNodeSelfGateWayUdpPack;
import com.dmatek.zgb.setting.directly.node.set.pack.SetNodeSelfIPUdpPack;
import com.dmatek.zgb.setting.directly.node.set.pack.SetNodeSelfMaskUdpPack;
import com.dmatek.zgb.setting.directly.node.set.pack.SetNodeServerIpUdpPack;
import com.dmatek.zgb.setting.directly.node.set.pack.SetNodeServerPortUdpPack;
import com.dmatek.zgb.setting.directly.node.set.pack.SetWifiIpModeUdpPack;
import com.dmatek.zgb.setting.directly.node.set.pack.SetWifiNameUdpPack;
import com.dmatek.zgb.setting.directly.node.set.pack.SetWifiPswUdpPack;
import com.dmatek.zgb.setting.directly.node.set.pack.SetWifiStaticIpUdpPack;
import com.dmatek.zgb.setting.directly.refer.read.pack.ReadReferReceiveSignUdpPack;
import com.dmatek.zgb.setting.directly.refer.read.pack.ReadReferSignKUdpPack;
import com.dmatek.zgb.setting.directly.refer.read.pack.ReadReferTypeVersionUdpPack;
import com.dmatek.zgb.setting.directly.refer.read.pack.ResetReferUdpPack;
import com.dmatek.zgb.setting.directly.refer.set.pack.SetReferReceiveSignUdpPack;
import com.dmatek.zgb.setting.directly.refer.set.pack.SetReferSignKUdpPack;
import com.dmatek.zgb.setting.pack.abstract_.BaseUdpPack;
/**
 * Udp数据包的工具类
 * @author zhangfu
 * @data 2019年4月26日 上午10:01:06
 * @Description
 */
@Component
public class UdpPackTool implements IPackTool {
	 /**
	 * 创建指定的UdpPack数据包的工厂类
	 * @param keyName
	 * @return
	 */
	@Override
	public BaseUdpPack createUdpPack(String keyName) {
		switch (keyName) {
		case ResetNodePack.KEY_NAME:
			return new ResetNodePack();
		case ReadIPUdpPack.KEY_NAME:
			return new ReadIPUdpPack();
		case ReadPortUdpPack.KEY_NAME:
			return new ReadPortUdpPack();
		case ReadWifiNameUdpPack.KEY_NAME:
			return new ReadWifiNameUdpPack();
		case ReadWifiPswUdpPack.KEY_NAME:
			return new ReadWifiPswUdpPack();
		case ReadIpModeUdpPack.KEY_NAME:
			return new ReadIpModeUdpPack();
		case ReadNodeIpUdpPack.KEY_NAME:
			return new ReadNodeIpUdpPack();
		case ReadNodeMaskUdpPack.KEY_NAME:
			return new ReadNodeMaskUdpPack();
		case ReadNodeGateWayUdpPack.KEY_NAME:
			return new ReadNodeGateWayUdpPack();
		case ReadNodeTypeVersionUdpPack.KEY_NAME:
			return new ReadNodeTypeVersionUdpPack();
		case ReadNodeChannelUdpPack.KEY_NAME:
			return new ReadNodeChannelUdpPack();
		case ReadNodeArroundWifiRssiUdpPack.KEY_NAME:
			return new ReadNodeArroundWifiRssiUdpPack();
		case ReadNodeWifiIpModeUdpPack.KEY_NAME:
			return new ReadNodeWifiIpModeUdpPack();
		case ReadNodeWifiStaticIpUdpPack.KEY_NAME:
			return new ReadNodeWifiStaticIpUdpPack();
		case SetNodeIpModeUdpPack.KEY_NAME:
			return new SetNodeIpModeUdpPack();
		case SetNodeSelfGateWayUdpPack.KEY_NAME:
			return new SetNodeSelfGateWayUdpPack();
		case SetNodeSelfIPUdpPack.KEY_NAME:
			return new SetNodeSelfIPUdpPack();
		case SetNodeSelfMaskUdpPack.KEY_NAME:
			return new SetNodeSelfMaskUdpPack();
		case SetNodeServerIpUdpPack.KEY_NAME:
			return new SetNodeServerIpUdpPack();
		case SetNodeServerPortUdpPack.KEY_NAME:
			return new SetNodeServerPortUdpPack();
		case SetWifiIpModeUdpPack.KEY_NAME:
			return new SetWifiIpModeUdpPack();
		case SetWifiNameUdpPack.KEY_NAME:
			return new SetWifiNameUdpPack();
		case SetWifiPswUdpPack.KEY_NAME:
			return new SetWifiPswUdpPack();
		case SetWifiStaticIpUdpPack.KEY_NAME:
			return new SetWifiStaticIpUdpPack();
		case ResetReferUdpPack.KEY_NAME:// 复位参考点
			return new ResetReferUdpPack();
		case ReadReferTypeVersionUdpPack.KEY_NAME:// 读取参考点类型和版本
			return new ReadReferTypeVersionUdpPack();
		case ReadReferReceiveSignUdpPack.KEY_NAME:// 读取参考点接收的信号强度
			return new ReadReferReceiveSignUdpPack();
		case ReadReferSignKUdpPack.KEY_NAME:// 读取参考点信号强度K值
			return new ReadReferSignKUdpPack();
		case SetReferReceiveSignUdpPack.KEY_NAME:
			return new SetReferReceiveSignUdpPack();
		case SetReferSignKUdpPack.KEY_NAME:
			return new SetReferSignKUdpPack();
		case SearchArroundNodesUdpPack.KEY_NAME:
			return new SearchArroundNodesUdpPack();
		case SearchArroundRefersUdpPack.KEY_NAME:
			return new SearchArroundRefersUdpPack();
		case ReadArroundReferTypeVersionUdpPack.KEY_NAME:
			return new ReadArroundReferTypeVersionUdpPack();
		case ReadArroundReferSigThresholdUdpPack.KEY_NAME:
			return new ReadArroundReferSigThresholdUdpPack();
		case ReadArroundReferSigKUdpPack.KEY_NAME:
			return new ReadArroundReferSigKUdpPack();
		case ReadArroundBetweenSigUdpPack.KEY_NAME:
			return new ReadArroundBetweenSigUdpPack();
		case ResetArroundReferUdpPack.KEY_NAME:
			return new ResetArroundReferUdpPack();
		case SetArroundReferSigKUdpPack.KEY_NAME:
			return new SetArroundReferSigKUdpPack();
		case SetArroundReferSigThresholdUdp.KEY_NAME:
			return new SetArroundReferSigThresholdUdp();
		case ReadArroundNodeTypeVersionUdpPack.KEY_NAME:
			return new ReadArroundNodeTypeVersionUdpPack();
		case ReadArroundNodeServerIpUdpPack.KEY_NAME:
			return new ReadArroundNodeServerIpUdpPack();
		case ReadArroundNodeServerPortUdpPack.KEY_NAME:
			return new ReadArroundNodeServerPortUdpPack();
		case ReadArroundNodeLanIpModeUdpPack.KEY_NAME:
			return new ReadArroundNodeLanIpModeUdpPack();
		case ReadArroundNodeLanStaticIpUdpPack.KEY_NAME:
			return new ReadArroundNodeLanStaticIpUdpPack();
		case ReadArroundNodeLastState.KEY_NAME:
			return new ReadArroundNodeLastState();
		case ResetArroundNodeUdpPack.KEY_NAME:
			return new ResetArroundNodeUdpPack();
		case ReadArroundNodeMaskUdpPack.KEY_NAME:
			return new ReadArroundNodeMaskUdpPack();
		case ReadArroundNodeGateWayUdpPack.KEY_NAME:
			return new ReadArroundNodeGateWayUdpPack();
		case SetArroundNodeServerIpUdpPack.KEY_NAME:
			return new SetArroundNodeServerIpUdpPack();
		case SetArroundNodeServerPortUdpPack.KEY_NAME:
			return new SetArroundNodeServerPortUdpPack();
		case SetArroundNodeSubMaskUdpPack.KEY_NAME:
			return new SetArroundNodeSubMaskUdpPack();
		case SetArroundNodeGateWayUdpPack.KEY_NAME:
			return new SetArroundNodeGateWayUdpPack();
		case SetArroundNodeLanIpModeUdpPack.KEY_NAME:
			return new SetArroundNodeLanIpModeUdpPack();
		case SetArroundNodeLanIpUdpPack.KEY_NAME:
			return new SetArroundNodeLanIpUdpPack();
		case ReadArroundNodeWifiNameUdpPack.KEY_NAME:
			return new ReadArroundNodeWifiNameUdpPack();
		case ReadArroundNodeWifiPswUdpPack.KEY_NAME:
			return new ReadArroundNodeWifiPswUdpPack();
		case ReadArroundNodeWifiIpModeUdpPack.KEY_NAME:
			return new ReadArroundNodeWifiIpModeUdpPack();
		case ReadArroundNodeWifiStaticIpUdpPack.KEY_NAME:
			return new ReadArroundNodeWifiStaticIpUdpPack();
		case SetArroundNodeWifiNameUdpPack.KEY_NAME:
			return new SetArroundNodeWifiNameUdpPack();
		case SetArroundNodeWifiPswUdpPack.KEY_NAME:
			return new SetArroundNodeWifiPswUdpPack();
		case SetArroundNodeWifiIpModelUdpPack.KEY_NAME:
			return new SetArroundNodeWifiIpModelUdpPack();
		case SetArroundNodeWifiStaticIpUdpPack.KEY_NAME:
			return new SetArroundNodeWifiStaticIpUdpPack();
		case ReadArroundNodeWifiRssiUdpPack.KEY_NAME:
			return new ReadArroundNodeWifiRssiUdpPack();
		case ReadArroundNodeAtCommandUdpPack.KEY_NAME:
			return new ReadArroundNodeAtCommandUdpPack();
		}
		return null;
	}
	/**
	 * 校验键名是否有效
	 * @param keyName
	 * @return
	 */
	@Override
	public boolean validate(String keyName) {
		switch (keyName) {
		case ResetNodePack.KEY_NAME:
			return true;
		case ReadIPUdpPack.KEY_NAME:
			return true;
		case ReadPortUdpPack.KEY_NAME:
			return true;
		case ReadWifiNameUdpPack.KEY_NAME:
			return true;
		case ReadWifiPswUdpPack.KEY_NAME:
			return true;
		case ReadIpModeUdpPack.KEY_NAME:
			return true;
		case ReadNodeIpUdpPack.KEY_NAME:
			return true;
		case ReadNodeMaskUdpPack.KEY_NAME:
			return true;
		case ReadNodeGateWayUdpPack.KEY_NAME:
			return true;
		case ReadNodeTypeVersionUdpPack.KEY_NAME:
			return true;
		case ReadNodeChannelUdpPack.KEY_NAME:
			return true;
		case ReadNodeArroundWifiRssiUdpPack.KEY_NAME:
			return true;
		case ReadNodeWifiIpModeUdpPack.KEY_NAME:
			return true;
		case ReadNodeWifiStaticIpUdpPack.KEY_NAME:
			return true;
		case SetNodeIpModeUdpPack.KEY_NAME:
			return true;
		case SetNodeSelfGateWayUdpPack.KEY_NAME:
			return true;
		case SetNodeSelfIPUdpPack.KEY_NAME:
			return true;
		case SetNodeSelfMaskUdpPack.KEY_NAME:
			return true;
		case SetNodeServerIpUdpPack.KEY_NAME:
			return true;
		case SetNodeServerPortUdpPack.KEY_NAME:
			return true;
		case SetWifiIpModeUdpPack.KEY_NAME:
			return true;
		case SetWifiNameUdpPack.KEY_NAME:
			return true;
		case SetWifiPswUdpPack.KEY_NAME:
			return true;
		case SetWifiStaticIpUdpPack.KEY_NAME:
			return true;
		case ResetReferUdpPack.KEY_NAME:
			return true;
		case ReadReferTypeVersionUdpPack.KEY_NAME:
			return true;
		case ReadReferReceiveSignUdpPack.KEY_NAME:
			return true;
		case ReadReferSignKUdpPack.KEY_NAME:
			return true;
		case SetReferReceiveSignUdpPack.KEY_NAME:
			return true;
		case SetReferSignKUdpPack.KEY_NAME:
			return true;
		case SearchArroundRefersUdpPack.KEY_NAME:
			return true;
		case SearchArroundNodesUdpPack.KEY_NAME:
			return true;
		case ReadArroundReferTypeVersionUdpPack.KEY_NAME:
			return true;
		case ReadArroundReferSigThresholdUdpPack.KEY_NAME:
			return true;
		case ReadArroundReferSigKUdpPack.KEY_NAME:
			return true;
		case ReadArroundBetweenSigUdpPack.KEY_NAME:
			return true;
		case ResetArroundReferUdpPack.KEY_NAME:
			return true;
		case SetArroundReferSigKUdpPack.KEY_NAME:
			return true;
		case SetArroundReferSigThresholdUdp.KEY_NAME:
			return true;
		case ReadArroundNodeTypeVersionUdpPack.KEY_NAME:
			return true;
		case ReadArroundNodeServerIpUdpPack.KEY_NAME:
			return true;
		case ReadArroundNodeServerPortUdpPack.KEY_NAME:
			return true;
		case ReadArroundNodeLanIpModeUdpPack.KEY_NAME:
			return true;
		case ReadArroundNodeLanStaticIpUdpPack.KEY_NAME:
			return true;
		case ReadArroundNodeLastState.KEY_NAME:
			return true;
		case ReadArroundNodeMaskUdpPack.KEY_NAME:
			return true;
		case ReadArroundNodeGateWayUdpPack.KEY_NAME:
			return true;
		case ResetArroundNodeUdpPack.KEY_NAME:
			return true;
		case SetArroundNodeServerIpUdpPack.KEY_NAME:
			return true;
		case SetArroundNodeServerPortUdpPack.KEY_NAME:
			return true;
		case SetArroundNodeSubMaskUdpPack.KEY_NAME:
			return true;
		case SetArroundNodeGateWayUdpPack.KEY_NAME:
			return true;
		case SetArroundNodeLanIpUdpPack.KEY_NAME:
			return true;
		case SetArroundNodeLanIpModeUdpPack.KEY_NAME:
			return true;
		case ReadArroundNodeWifiNameUdpPack.KEY_NAME:
			return true;
		case ReadArroundNodeWifiPswUdpPack.KEY_NAME:
			return true;
		case ReadArroundNodeWifiIpModeUdpPack.KEY_NAME:
			return true;
		case ReadArroundNodeWifiStaticIpUdpPack.KEY_NAME:
			return true;
		case SetArroundNodeWifiNameUdpPack.KEY_NAME:
			return true;
		case SetArroundNodeWifiPswUdpPack.KEY_NAME:
			return true;
		case SetArroundNodeWifiIpModelUdpPack.KEY_NAME:
			return true;
		case SetArroundNodeWifiStaticIpUdpPack.KEY_NAME:
			return true;
		case ReadArroundNodeWifiRssiUdpPack.KEY_NAME:
			return true;
		case ReadArroundNodeAtCommandUdpPack.KEY_NAME:
			return true;
		}
		return false;
	}
	/**
	 * 检验键值是否有效，
	 * 有效时返回空，
	 * 无效返回无效原因字符串
	 * @param keyName
	 * @param keyValue
	 * @return
	 */
	@Override
	public String validateValue(String keyName, String keyValue) {
		String ipRegex = "^(([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))\\.)(([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))\\.){2}([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))$";// ip的正则表达式
		String regex1 = "255\\.255\\.255\\.(0|128|192|224|240|248|252|254|255)";
		String regex2= "255\\.255\\.(0|128|192|224|240|248|252|254|255)\\.0";      
		String regex3 = "255\\.(0|128|192|224|240|248|252|254|255)\\.0\\.0";
		Pattern pattern1 = Pattern.compile(regex1);
		Pattern pattern2 = Pattern.compile(regex2);
		Pattern pattern3 = Pattern.compile(regex3);
		Pattern ipPattern = Pattern.compile(ipRegex);
		switch (keyName) {
		case SetNodeServerIpUdpPack.KEY_NAME:// 设置ServerIp
			// 说明keyValue是设置的Ip地址，需要验证Ip地址是否有效
			if(!ipPattern.matcher(keyValue).matches()) {
				return "格式有误";
			}
			break;
		case SetNodeServerPortUdpPack.KEY_NAME:// 设置ServerPort
			int port = 0;
			try {
				port = Integer.parseInt(keyValue);
			} catch (Exception e) {
				return "格式有誤";
			}
			if(port <= 1024 && port > 65535) {
				return "端口範圍為1024~65535";
			}
			break;
		case SetWifiNameUdpPack.KEY_NAME:
			byte[] wifiNameBytes = keyValue.getBytes();
			if(wifiNameBytes.length > 32) {
				return "Wifi名稱長度大於32";
			}
			break;
		case SetWifiPswUdpPack.KEY_NAME:
			byte[] wifiPswBytes = keyValue.getBytes();
			if(wifiPswBytes.length > 32) {
				return "Wifi密碼長度大於32";
			}
			break;
		case SetNodeIpModeUdpPack.KEY_NAME:
			int ipMode = 0;
			try {
				ipMode = Integer.parseInt(keyValue);
			} catch (Exception e) {
				return "格式有誤";
			}
			if(ipMode != 0 && ipMode != 1) {
				return "ip模式只能為0或1";
			}
			break;
		case SetNodeSelfIPUdpPack.KEY_NAME:
			if(!ipPattern.matcher(keyValue).matches()) {
				return "格式有误";
			}
			break;
		case SetNodeSelfMaskUdpPack.KEY_NAME:
			if(!pattern1.matcher(keyValue).matches() 
			&& !pattern2.matcher(keyValue).matches()
			&& !pattern3.matcher(keyValue).matches()) {
				return "格式有误";
			}
			break;
		case SetNodeSelfGateWayUdpPack.KEY_NAME:
			if(!ipPattern.matcher(keyValue).matches()) {
				return "格式有误";
			}
			break;
		case SetWifiIpModeUdpPack.KEY_NAME:
			int wifiIpMode = 0;
			try {
				wifiIpMode = Integer.parseInt(keyValue);
			} catch (Exception e) {
				return "格式有誤";
			}
			if(wifiIpMode != 0 && wifiIpMode != 1) {
				return "ip模式只能為0或1";
			}
			break;
		case SetWifiStaticIpUdpPack.KEY_NAME:
			if(!ipPattern.matcher(keyValue).matches()) {
				return "格式有误";
			}
			break;
		case SetArroundReferSigThresholdUdp.KEY_NAME:
			int arroundReferSig = 0;
			try {
				arroundReferSig = Integer.parseInt(keyValue);
			} catch (Exception e) {
				return "格式有誤";
			}
			if(arroundReferSig <= 0 || arroundReferSig > 255) {
				return "範圍必須為0~255";
			}
			break;
		case SetReferReceiveSignUdpPack.KEY_NAME:
			int referSig = 0;
			try {
				referSig = Integer.parseInt(keyValue);
			} catch (Exception e) {
				return "格式有誤";
			}
			if(referSig <= 0 || referSig > 255) {
				return "範圍必須為0~255";
			}
			break;
		case SetArroundReferSigKUdpPack.KEY_NAME:
			float arroundReferSigK = 0;
			try {
				arroundReferSigK = Float.parseFloat(keyValue);
			} catch (Exception e) {
				return "格式有誤";
			}
			if(arroundReferSigK <= 0 || arroundReferSigK > 2.55) {
				return "範圍必須為0~2.55";
			}
			break;
		case SetReferSignKUdpPack.KEY_NAME:
			float referSigK = 0;
			try {
				referSigK = Float.parseFloat(keyValue);
			} catch (Exception e) {
				return "格式有誤";
			}
			if(referSigK <= 0 || referSigK > 2.55) {
				return "範圍必須為0~2.55";
			}
			break;
		case SetArroundNodeServerIpUdpPack.KEY_NAME:
			if(!ipPattern.matcher(keyValue).matches()) {
				return "格式有误";
			}
			break;
		case SetArroundNodeLanIpModeUdpPack.KEY_NAME:
			int arroundIpMode = 0;
			try {
				arroundIpMode = Integer.parseInt(keyValue);
			} catch (Exception e) {
				return "格式有誤";
			}
			if(arroundIpMode != 0 && arroundIpMode != 1) {
				return "ip模式只能為0或1";
			}
			break;
		case SetArroundNodeLanIpUdpPack.KEY_NAME:
			if(!ipPattern.matcher(keyValue).matches()) {
				return "格式有误";
			}
			break;
		case SetArroundNodeSubMaskUdpPack.KEY_NAME:
			if(!pattern1.matcher(keyValue).matches() 
			&& !pattern2.matcher(keyValue).matches()
			&& !pattern3.matcher(keyValue).matches()) {
				return "格式有误";
			}
			break;
		case SetArroundNodeGateWayUdpPack.KEY_NAME:
			if(!ipPattern.matcher(keyValue).matches()) {
				return "格式有误";
			}
			break;
		case SetArroundNodeWifiNameUdpPack.KEY_NAME:
			byte[] arroundWifiNameBytes = keyValue.getBytes();
			if(arroundWifiNameBytes.length > 32) {
				return "Wifi名稱長度大於32";
			}
			break;
		case SetArroundNodeWifiPswUdpPack.KEY_NAME:
			byte[] arroundWifiPswBytes = keyValue.getBytes();
			if(arroundWifiPswBytes.length > 32) {
				return "Wifi名稱長度大於32";
			}
			break;
		case SetArroundNodeWifiIpModelUdpPack.KEY_NAME:
			int arroundWifiIpMode = 0;
			try {
				arroundWifiIpMode = Integer.parseInt(keyValue);
			} catch (Exception e) {
				return "格式有誤";
			}
			if(arroundWifiIpMode != 0 && arroundWifiIpMode != 1) {
				return "ip模式只能為0或1";
			}
			break;
		case SetArroundNodeWifiStaticIpUdpPack.KEY_NAME:
			if(!ipPattern.matcher(keyValue).matches()) {
				return "格式有误";
			}
			break;
		case ReadArroundNodeWifiRssiUdpPack.KEY_NAME:
			byte[] arroundWifiRssiNameBytes = keyValue.getBytes();
			if(arroundWifiRssiNameBytes.length > 32) {
				return "Wifi名稱長度大於32";
			}
			break;
		case ReadArroundNodeAtCommandUdpPack.KEY_NAME:
			byte[] arroundNodeAtCommandBytes = keyValue.getBytes();
			if(arroundNodeAtCommandBytes.length > 90) {
				return "AT指令長度不能大於90字節";
			}
		default:
			break;
		}
		return null;
	}
	/**
	 * 将指定的键名对应的键值转化为字节数组
	 * @param keyName
	 * @param keyValue
	 * @return
	 */
	@Override
	public byte[] parseParamBytes(String keyName, String keyValue) {
		switch (keyName) {
		case SetNodeServerIpUdpPack.KEY_NAME:// 设置ServerIp
			String[] strIps = keyValue.split("[.]");
			if(null == strIps || strIps.length != 4) {
				return null;
			}
			byte[] serverIpBytes = new byte[4];
			for (int i = 0; i < strIps.length; i++) {
				serverIpBytes[i] = (byte) Integer.parseInt(strIps[i]);
			}
			return serverIpBytes;
		case SetNodeServerPortUdpPack.KEY_NAME:// 设置ServerPort
			int port = 0;
			try {
				port = Integer.parseInt(keyValue);
			} catch (Exception e) {
				return null;
			}
			if(port <= 1024 && port > 65535) {
				return null;
			}
			byte[] serverPortBytes = new byte[2];
			serverPortBytes[0] = (byte) (port >> 8);
			serverPortBytes[1] = (byte) port;
			return serverPortBytes;
		case SetWifiNameUdpPack.KEY_NAME:
			byte[] wifiNameBytes = keyValue.getBytes();
			if(wifiNameBytes.length > 32) {
				return null;
			}
			return wifiNameBytes;
		case SetWifiPswUdpPack.KEY_NAME:
			byte[] wifiPswBytes = keyValue.getBytes();
			if(wifiPswBytes.length > 32) {
				return null;
			}
			return wifiPswBytes;
		case SetNodeIpModeUdpPack.KEY_NAME:
			int ipMode = 0;
			try {
				ipMode = Integer.parseInt(keyValue);
			} catch (Exception e) {
				return null;
			}
			if(ipMode != 0 && ipMode != 1) {
				return null;
			}
			byte[] ipModeBytes = new byte[1];
			ipModeBytes[0] = (byte) ipMode;
			return ipModeBytes;
		case SetNodeSelfIPUdpPack.KEY_NAME:
			String[] strSelfIps = keyValue.split("[.]");
			if (null == strSelfIps || strSelfIps.length != 4) {
				return null;
			}
			byte[] selfIpBytes = new byte[4];
			for (int i = 0; i < strSelfIps.length; i++) {
				selfIpBytes[i] = (byte) Integer.parseInt(strSelfIps[i]);
			}
			return selfIpBytes;
		case SetNodeSelfMaskUdpPack.KEY_NAME:
			String[] strSelfMasks = keyValue.split("[.]");
			if(null == strSelfMasks || strSelfMasks.length != 4) {
				return null;
			}
			byte[] selfMaskBytes = new byte[4];
			for (int i = 0; i < strSelfMasks.length; i++) {
				selfMaskBytes[i] = (byte) Integer.parseInt(strSelfMasks[i]);
			}
			return selfMaskBytes;
		case SetNodeSelfGateWayUdpPack.KEY_NAME:
			String[] strSelfGateWays = keyValue.split("[.]");
			if(null == strSelfGateWays || strSelfGateWays.length != 4) {
				return null;
			}
			byte[] selfGateWayBytes = new byte[4];
			for (int i = 0; i < strSelfGateWays.length; i++) {
				selfGateWayBytes[i] = (byte) Integer
						.parseInt(strSelfGateWays[i]);
			}
			return selfGateWayBytes;
		case SetWifiIpModeUdpPack.KEY_NAME:
			int wifiIpMode = 0;
			try {
				wifiIpMode = Integer.parseInt(keyValue);
			} catch (Exception e) {
				return null;
			}
			if(wifiIpMode != 0 && wifiIpMode != 1) {
				return null;
			}
			byte[] wifiIpModeBytes = new byte[1];
			wifiIpModeBytes[0] = (byte) (wifiIpMode == 0?0x1:0x2);
			return wifiIpModeBytes;
		case SetWifiStaticIpUdpPack.KEY_NAME:
			String[] strWifiStaticIp = keyValue.split("[.]");
			if(null == strWifiStaticIp || strWifiStaticIp.length != 4) {
				return null;
			}
			byte[] wifiStaticIpBytes = new byte[4];
			for (int i = 0; i < wifiStaticIpBytes.length; i++) {
				wifiStaticIpBytes[i] = (byte) Integer
						.parseInt(strWifiStaticIp[i]);
			}
			return wifiStaticIpBytes;
		case SetReferReceiveSignUdpPack.KEY_NAME:
			int signThreshold = 0;
			try {
				signThreshold = Integer.parseInt(keyValue);
			} catch (Exception e) {
				return null;
			}
			byte[] signThresholdBytes = new byte[1];
			signThresholdBytes[0] = (byte) signThreshold;
			return signThresholdBytes;
		case SetReferSignKUdpPack.KEY_NAME:
			float signK = 0;
			try {
				signK = Float.parseFloat(keyValue);
			} catch (Exception e) {
				return null;
			}
			byte[] signKBytes = new byte[1];
			signKBytes[0] = (byte) (signK * 100);
			return signKBytes;
		case SetArroundReferSigThresholdUdp.KEY_NAME:
			int arroundSignThreshold = 0;
			try {
				arroundSignThreshold = Integer.parseInt(keyValue);
			} catch (Exception e) {
				return null;
			}
			byte[] arroundSignThresholdBytes = new byte[1];
			arroundSignThresholdBytes[0] = (byte) arroundSignThreshold;
			return arroundSignThresholdBytes;
		case SetArroundReferSigKUdpPack.KEY_NAME:
			float arroundSignK = 0;
			try {
				arroundSignK = Float.parseFloat(keyValue);
			} catch (Exception e) {
				return null;
			}
			byte[] arroundSignKBytes = new byte[1];
			arroundSignKBytes[0] = (byte) (arroundSignK * 100);
			return arroundSignKBytes;
		case SetArroundNodeServerIpUdpPack.KEY_NAME:
			String[] strArroundServerIps = keyValue.split("[.]");
			if(null == strArroundServerIps || strArroundServerIps.length != 4) {
				return null;
			}
			byte[] arroundServerIpBytes = new byte[4];
			for (int i = 0; i < strArroundServerIps.length; i++) {
				arroundServerIpBytes[i] = (byte) Integer.parseInt(strArroundServerIps[i]);
			}
			return arroundServerIpBytes;
		case SetArroundNodeServerPortUdpPack.KEY_NAME:
			int arroundPort = 0;
			try {
				arroundPort = Integer.parseInt(keyValue);
			} catch (Exception e) {
				return null;
			}
			if(arroundPort <= 1024 && arroundPort > 65535) {
				return null;
			}
			byte[] arroundServerPortBytes = new byte[2];
			arroundServerPortBytes[0] = (byte) (arroundPort >> 8);
			arroundServerPortBytes[1] = (byte) arroundPort;
			return arroundServerPortBytes;
		case SetArroundNodeLanIpModeUdpPack.KEY_NAME:
			int arroundIpMode = 0;
			try {
				arroundIpMode = Integer.parseInt(keyValue);
			} catch (Exception e) {
				return null;
			}
			if(arroundIpMode != 0 && arroundIpMode != 1) {
				return null;
			}
			byte[] arroundIpModeBytes = new byte[1];
			arroundIpModeBytes[0] = (byte) arroundIpMode;
			return arroundIpModeBytes;
		case SetArroundNodeSubMaskUdpPack.KEY_NAME:
			String[] strArroundSelfMasks = keyValue.split("[.]");
			if(null == strArroundSelfMasks || strArroundSelfMasks.length != 4) {
				return null;
			}
			byte[] arroundSelfMaskBytes = new byte[4];
			for (int i = 0; i < strArroundSelfMasks.length; i++) {
				arroundSelfMaskBytes[i] = (byte) Integer.parseInt(strArroundSelfMasks[i]);
			}
			return arroundSelfMaskBytes;
		case SetArroundNodeGateWayUdpPack.KEY_NAME:
			String[] strArroundSelfGateWays = keyValue.split("[.]");
			if(null == strArroundSelfGateWays || strArroundSelfGateWays.length != 4) {
				return null;
			}
			byte[] arroundSelfGateWayBytes = new byte[4];
			for (int i = 0; i < strArroundSelfGateWays.length; i++) {
				arroundSelfGateWayBytes[i] = (byte) Integer
						.parseInt(strArroundSelfGateWays[i]);
			}
			return arroundSelfGateWayBytes;
		case SetArroundNodeLanIpUdpPack.KEY_NAME:
			String[] strArroundLanIps = keyValue.split("[.]");
			if(null == strArroundLanIps || strArroundLanIps.length != 4) {
				return null;
			}
			byte[] arroundLanIpBytes = new byte[4];
			for (int i = 0; i < strArroundLanIps.length; i++) {
				arroundLanIpBytes[i] = (byte) Integer.parseInt(strArroundLanIps[i]);
			}
			return arroundLanIpBytes;
		case SetArroundNodeWifiNameUdpPack.KEY_NAME:
			byte[] arroundWifiNameBytes = keyValue.getBytes();
			if(arroundWifiNameBytes.length > 32) {
				return null;
			}
			return arroundWifiNameBytes;
		case SetArroundNodeWifiPswUdpPack.KEY_NAME:
			byte[] arroundWifiPswBytes = keyValue.getBytes();
			if(arroundWifiPswBytes.length > 32) {
				return null;
			}
			return arroundWifiPswBytes;
		case SetArroundNodeWifiIpModelUdpPack.KEY_NAME:
			int arroundWifiIpMode = 0;
			try {
				arroundWifiIpMode = Integer.parseInt(keyValue);
			} catch (Exception e) {
				return null;
			}
			if(arroundWifiIpMode != 0 && arroundWifiIpMode != 1) {
				return null;
			}
			byte[] arroundWifiIpModeBytes = new byte[1];
			arroundWifiIpModeBytes[0] = (byte) arroundWifiIpMode;
			return arroundWifiIpModeBytes;
		case SetArroundNodeWifiStaticIpUdpPack.KEY_NAME:
			String[] strArroundWifiStaticIp = keyValue.split("[.]");
			if(null == strArroundWifiStaticIp || strArroundWifiStaticIp.length != 4) {
				return null;
			}
			byte[] arroundWifiStaticIpBytes = new byte[4];
			for (int i = 0; i < arroundWifiStaticIpBytes.length; i++) {
				arroundWifiStaticIpBytes[i] = (byte) Integer
						.parseInt(strArroundWifiStaticIp[i]);
			}
			return arroundWifiStaticIpBytes;
		default:
			break;
		}
		return null;
	}
}
