package com.dmatek.zgb.comparator;

import java.util.Comparator;

public abstract class BaseComparator<T> implements Comparator<T>{
	private boolean isAscending;// 是否是升序
	public abstract int iCompare(T t1,T t2);
	@Override
	public int compare(T o1, T o2) {
		if(null != o1 && null != o2){
			int o = iCompare(o1,o2);
			return isAscending?o:-o;
		}
		return 0;
	}
	public boolean isAscending() {
		return isAscending;
	}
	public void setAscending(boolean isAscending) {
		this.isAscending = isAscending;
	}
}
