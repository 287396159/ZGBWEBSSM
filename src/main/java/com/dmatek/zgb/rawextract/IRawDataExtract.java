package com.dmatek.zgb.rawextract;
import java.util.List;

public interface IRawDataExtract<T> {
	public List<T> ExtractOrigin(Object...objects) throws Exception;
}
