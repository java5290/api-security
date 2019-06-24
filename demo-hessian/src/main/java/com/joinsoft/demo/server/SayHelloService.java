package com.joinsoft.demo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SayHelloService implements SayHello {

	private Logger log = LoggerFactory.getLogger(SayHelloService.class);
	
	@Override
	public String say(String username) {
		log.info("say called, 接收参数：" + username);;
		return "Hello, " + username;
	}

}
