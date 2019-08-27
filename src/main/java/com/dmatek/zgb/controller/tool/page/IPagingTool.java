package com.dmatek.zgb.controller.tool.page;

import java.util.List;
/**
 * 分页工具
 * @author zf
 * @data 2019年2月13日 上午8:56:16
 * @Description
 */
public interface IPagingTool {
	public <T> List<T> operate(List<T> totals,int page,int limit) throws Exception;
}
