package com.dmatek.zgb.comparator;

import java.util.Comparator;

public class ComparatorTool implements IComparatorTool {
	private Comparator<?>[] comparators;
	public ComparatorTool(Comparator<?>...comparators) {
		super();
		this.comparators = comparators;
	}
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Comparator<?>> T obtain(Class<T> clazz) throws Exception {
		for (Comparator<?> comparator : getComparators()) {
			if(clazz.isInstance(comparator)) {
				return (T) comparator;
			}
		}
		return null;
	}
	public Comparator<?>[] getComparators() {
		return comparators;
	}
	public void setComparators(Comparator<?>[] comparators) {
		this.comparators = comparators;
	}
}
