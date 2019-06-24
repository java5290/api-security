package com.joinsoft.demo.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

import com.joinsoft.demo.server.HessianServerProxyExporter;
import com.joinsoft.demo.server.SayHello;
import com.joinsoft.demo.server.SayHelloService;

@Configuration
public class HessianConfig {

	@Autowired
	private SayHelloService sayHelloService;
	
	@Bean("/sayHello")
	public HessianServiceExporter exportSayHello() {
		//HessianServiceExporter exporter = new HessianServiceExporter();
		HessianServerProxyExporter exporter = new HessianServerProxyExporter();
		exporter.setService(sayHelloService);
		exporter.setServiceInterface(SayHello.class);
		return exporter;
	}
}
