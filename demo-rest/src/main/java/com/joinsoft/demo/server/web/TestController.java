package com.joinsoft.demo.server.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@PostMapping("/test/sayHello")
	public String sayHello(String username) {
		
		return "Hello, " + username;
	}
	
	@PostMapping("/test/sayByebye")
	public String sayByebye(String username) {
		
		return "Byebye, " + username;
	}
	
	@GetMapping("/test/sayOk")
	public String sayOk(String username) {
		
		return "Ok, " + username;
	}
}
