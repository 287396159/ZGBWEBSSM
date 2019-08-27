package com.dmatek.zgb.packages.iscan;

import java.io.IOException;
import java.util.Set;

public interface IPackageScanner {
	public Set<Class<?>> getPackagePathClassNames(String packagePath,
			boolean isFullPath) throws IOException;
}
