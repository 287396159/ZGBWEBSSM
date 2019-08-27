package com.dmatek.zgb.controller.setting.node;

import com.dmatek.zgb.setting.pack.abstract_.BaseUdpPack;

public interface IPackTool {
	public BaseUdpPack createUdpPack(String keyName);
	public boolean validate(String keyName);
	public String validateValue(String keyName, String keyValue);
	public byte[] parseParamBytes(String keyName, String keyValue);
}
