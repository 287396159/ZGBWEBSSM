package com.dmatek.zgb.setting.arround.refer.read.pack;

import java.util.ArrayList;
import java.util.List;

import com.dmatek.zgb.setting.pack.abstract_.BaseSearchArroundUdpPack;
import com.dmatek.zgb.setting.vo.SearchDevicePo;

public class SearchArroundRefersUdpPack extends BaseSearchArroundUdpPack{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 4;// 發送的數據包的尺寸
	private static final byte TYPE = 0x41;// 發送數據包的類型字節
	public static final String KEY_NAME = "SearchArroundRefersUdpPack";
	@Override
	public String keyName() {
		return KEY_NAME;
	}
	@Override
	protected int size() {
		return SIZE;
	}
	@Override
	protected byte type() {
		return TYPE;
	}
	@SuppressWarnings("unchecked")
	@Override
	public SearchDevicePo[] parse() throws Exception {
		List<SearchDevicePo> searchDevices = new ArrayList<SearchDevicePo>();
		byte[] backBytes = getBackBytes();
		for (int i = 1; i < backBytes.length; i += 2) {
			String id = String.format("%02X", backBytes[i]) + 
						String.format("%02X", backBytes[i + 1]);
			searchDevices.add(new SearchDevicePo(id));
		}
		return searchDevices.toArray(new SearchDevicePo[0]);
	}
	
	@Override
	public void replaceVal(byte[] bytes, int pos) {
		if (null != bytes && pos > 0) {
			// 搜索到的設備數量
			int count = bytes[pos];
			int total = count * 2 + 1;
			setBackBytes(new byte[total]);
			System.arraycopy(bytes, pos, getBackBytes(), 0, total);
		}
	}
	@Override
	public int obtainDelayMs() {
		// TODO Auto-generated method stub
		return 6000;
	}
	
}
