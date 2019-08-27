package com.dmatek.zgb.warn.bean;

import com.dmatek.zgb.warn.bean.base.BaseAbnormalNodeWarn;
/**
 * 基站断开警报
 * @author zf
 * @data 2018年12月14日 下午4:52:40
 * @Description
 */
public class AbnormalBaseWarn extends BaseAbnormalNodeWarn {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AbnormalBaseWarn() {
		super();
	}
	public AbnormalBaseWarn(String nodeId, String nodeName, int groupId,
			int regionId, String groupName, String regionName, int curDisTime,
			int sleepTime) {
		super(nodeId, nodeName, groupId, regionId, groupName, regionName, curDisTime,
				sleepTime);
	}
}
