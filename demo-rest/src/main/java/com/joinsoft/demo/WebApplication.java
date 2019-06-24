package com.joinsoft.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.joinsoft.demo.server.interceptor.DemoInterceptor;

@SpringBootApplication
@ComponentScan(basePackages={"com.joinsoft"})
public class WebApplication extends SpringBootServletInitializer implements WebMvcConfigurer {
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebApplication.class);
    }
	
	public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 指定拦截匹配模式，限制拦截器的拦截请求
		InterceptorRegistration iRegistration = registry.addInterceptor(new DemoInterceptor());
		// 只对请求中带有test的进行拦截验证
		iRegistration.addPathPatterns("/test/*");
	}
}
