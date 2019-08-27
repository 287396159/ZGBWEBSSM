package com.dmatek.zgb.file.update;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	@Autowired
    private FileUploadProperteis fileUploadProperteis;
	@Autowired
	private StaffUploadProperties staffUploadProperties;
	@Autowired
	private TempUploadProperties tempUploadProperties;
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("1024MB");
		factory.setMaxRequestSize("1024MB");
		return factory.createMultipartConfig();
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(fileUploadProperteis.getStaticAccessPath())
				.addResourceLocations("file:" + fileUploadProperteis.getUploadFolder() + "/");
		registry.addResourceHandler(staffUploadProperties.getStaticAccessPath())
				.addResourceLocations("file:" + staffUploadProperties.getUploadFolder() + "/");
		registry.addResourceHandler(tempUploadProperties.getStaticAccessPath())
				.addResourceLocations("file:" + tempUploadProperties.getUploadFolder() + "/");
	}
}
