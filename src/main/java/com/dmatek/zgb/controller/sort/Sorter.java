package com.dmatek.zgb.controller.sort;
import java.util.Arrays;
import java.util.List;

import com.dmatek.zgb.comparator.BaseComparator;

public class Sorter<T> implements ISort<T> {
	private BaseComparator<T> comparator;
	public Sorter(){
		super();
	}
	@Override
	public List<T> sort(T[] datas, boolean isAscending) throws Exception {
		comparator.setAscending(isAscending);
		Arrays.sort(datas, comparator);
		return Arrays.asList(datas);
	}
	public BaseComparator<T> getComparator() {
		return comparator;
	}
	public void setComparator(BaseComparator<T> comparator) {
		this.comparator = comparator;
	}
}
