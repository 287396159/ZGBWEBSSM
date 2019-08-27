package com.dmatek.zgb.report.statistical.warn.bean;

import java.util.LinkedList;
import java.util.List;
import com.dmatek.zgb.warn.bean.base.BaseWarnMessage;
/**
 * 統計警告訊息的抽象類
 * @author zhangfu
 * @data 2019年4月15日 下午3:37:41
 * @Description
 */
public abstract class BaseStatisticalWarn<T extends BaseWarnMessage> {
	private String id;
	private List<T> statisticalWarns = null;
	public BaseStatisticalWarn(String id) {
		super();
		this.id = id;
		this.statisticalWarns = new LinkedList<T>();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<T> getStatisticalWarns() {
		return statisticalWarns;
	}
	public void setStatisticalWarns(List<T> statisticalWarns) {
		this.statisticalWarns = statisticalWarns;
	}
}
