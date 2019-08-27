package com.dmatek.zgb.packages.iscan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PackageScannerConfig {
	/**
	 * 将类路径包扫描器放入到Spring的IOC容器中
	 * @return
	 */
	@Bean
	public ClassPathPackageScanner getClassPathPackageScanner(){
		return new ClassPathPackageScanner();
	}
}
