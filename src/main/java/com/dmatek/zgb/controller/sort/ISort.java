package com.dmatek.zgb.controller.sort;

import java.util.List;

public interface ISort<T> {
	List<T> sort(T[] datas,boolean isAscending) throws Exception;
}
