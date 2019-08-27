package com.dmatek.zgb.controller.tool.page;

import java.util.List;

public class PageTool implements IPagingTool {
	@Override
	public <T> List<T> operate(List<T> totals, int page, int limit)
			throws Exception {
		// 2. page : 第几页， limit：每一页数量
		int fromIndex = (page * limit > totals.size())?totals.size():page * limit;
		return totals.subList((page - 1) * limit, fromIndex);
	}
	public <T> int getPages(List<T> totals, int limit) throws Exception{
		// 获取总页数
		int total = totals.size();
		if(limit == 0){
			return -1;
		}
		return total%limit == 0?(total/limit):((total/limit) + 1);
	}
}
