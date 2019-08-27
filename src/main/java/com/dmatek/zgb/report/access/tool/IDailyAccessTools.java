package com.dmatek.zgb.report.access.tool;

import java.util.Date;
import java.util.List;

import com.dmatek.zgb.show.vo.AccessView;

public interface IDailyAccessTools<T, V> {
	// 轉化統計工具
	List<V> transform(List<T> records, Date start, Date end) throws Exception;
	// 過濾掉不符合條件的記錄
	List<V> filter(List<V> records) throws Exception;
	// 統計工時
	void statisticalWorkHours(List<V> records) throws Exception;
	// 计算单个工时
	float calcWorkHours(AccessView accessView) throws Exception;
}
