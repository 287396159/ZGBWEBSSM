package com.dmatek.zgb.druid.configuration;
import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 配置容器类
 * @author zf
 * @data 2018年12月7日 上午9:02:16
 * @Description
 */
@Configuration
@PropertySource("classpath:config/spring-mybatis.properties")
@ServletComponentScan(basePackages = { "com.dmatek.zgb.druid" })
public class DruidConfig {
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.druid")
	public DataSource getDruidDataSource() {
		return new DruidDataSource();
	}
}
