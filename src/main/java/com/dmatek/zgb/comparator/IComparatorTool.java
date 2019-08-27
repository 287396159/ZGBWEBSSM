package com.dmatek.zgb.comparator;

import java.util.Comparator;

public interface IComparatorTool {
	<T extends Comparator<?>> T obtain(Class<T> clazz) throws Exception;
}
