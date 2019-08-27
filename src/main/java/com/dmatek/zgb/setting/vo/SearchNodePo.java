package com.dmatek.zgb.setting.vo;

public  class SearchNodePo extends SearchDevicePo {
	private int channel;// 信道
	public SearchNodePo(String id, int channel) {
		super(id);
		this.channel = channel;
	}
	public SearchNodePo(String id, int channel, String type, String version) {
		super(id, type, version);
		this.channel = channel;
	}
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
}
