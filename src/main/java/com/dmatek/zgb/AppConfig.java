package com.dmatek.zgb;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@MapperScan(basePackages={"com.dmatek.zgb.db.setting.dao",
		"com.dmatek.zgb.db.permission.dao", "com.dmatek.zgb.db.person.dao",
		"com.dmatek.zgb.db.warn.dao", "com.dmatek.zgb.db.access.dao",
		"com.dmatek.zgb.db.log.dao", "com.dmatek.zgb.db.access.work.dao"})
public class AppConfig extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(AppConfig.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		return builder.sources(AppConfig.class);
	}
}
