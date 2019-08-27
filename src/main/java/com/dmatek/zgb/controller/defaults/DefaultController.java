package com.dmatek.zgb.controller.defaults;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class DefaultController extends WebMvcConfigurerAdapter {
	public void addViewControllers( ViewControllerRegistry registry ) {
	       registry.addViewController( "/" ).setViewName( "index" );
	       registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
	       super.addViewControllers( registry );
	}
}
