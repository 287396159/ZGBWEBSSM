package com.dmatek.zgb.warn.bean.base;
/**
 * 节点警告讯息
 * @author zf
 * @data 2018年12月14日 下午4:47:37
 * @Description
 */
public abstract class BaseNodeWarnMessage extends BaseWarnMessage{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nodeId, nodeName;// 参考点ID、参考点名称
	public BaseNodeWarnMessage() {
		super();
	}
	public BaseNodeWarnMessage(String nodeId, String nodeName, int groupId,
			int regionId, String groupName, String regionName) {
		super(groupId, regionId, groupName, regionName);
		this.nodeId = nodeId;
		this.nodeName = nodeName;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
}
