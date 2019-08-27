package com.dmatek.zgb.pack.parsing;

import java.io.File;

public interface IByteFileParse<T> {
	public T[] ParseByteFile(File bfile) throws Exception;
}
