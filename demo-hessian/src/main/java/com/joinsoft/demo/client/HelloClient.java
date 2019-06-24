package com.joinsoft.demo.client;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.client.HessianProxyFactory;
import com.joinsoft.demo.server.SayHello;

public class HelloClient {

	public static Logger log = LoggerFactory.getLogger(HelloClient.class);
			
	public static void main(String[] args) {
		String url = "http://127.0.0.1/demo-hessian/sayHello";
		
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setUser("lzx");
		factory.setPassword("xzl");
		
		try {
			SayHello sayHello = (SayHello) factory.create(SayHello.class, url);
			String msg = sayHello.say("李中新");
			
			log.info(msg);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
