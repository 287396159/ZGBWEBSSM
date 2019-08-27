package com.dmatek.zgb.history.track;

import java.util.Date;
import java.util.List;

/**
 * 轨迹查询的抽象类
 * @author zf
 * @data 2019年2月12日 上午11:45:37
 * @Description
 */
public abstract class BaseTrack<T> {
	// 设置基础路径
	private String path;
	// 获取所有的卡片数据记录
	public abstract List<T> getAllTagTrace(Date start,Date end) throws Exception;
	// 获取单个的卡片数据记录
	public abstract List<T> getSingleTagTrace(Date start, Date end, String tagId);
	public BaseTrack(String path) {
		super();
		this.path = path;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
