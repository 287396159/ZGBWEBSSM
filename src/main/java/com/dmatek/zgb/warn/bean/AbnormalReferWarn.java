package com.dmatek.zgb.warn.bean;

import com.dmatek.zgb.warn.bean.base.BaseAbnormalNodeWarn;
/**
 * 参考点断开警告
 * @author zf
 * @data 2018年12月14日 下午4:52:24
 * @Description
 */
public class AbnormalReferWarn extends BaseAbnormalNodeWarn{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AbnormalReferWarn() {
		super();
	}
	public AbnormalReferWarn(String nodeId, String nodeName, int groupId,
			int regionId, String groupName, String regionName, int curDisTime,
			int sleepTime) {
		super(nodeId, nodeName, groupId, regionId, groupName, regionName, curDisTime,
				sleepTime);
	}
}
