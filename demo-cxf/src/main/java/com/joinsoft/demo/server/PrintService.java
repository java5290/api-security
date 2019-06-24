package com.joinsoft.demo.server;

import javax.jws.WebService;

@WebService(endpointInterface="com.joinsoft.demo.server.Print", serviceName="Print")
public class PrintService implements Print {

	public String print(String text) {
		System.out.println("print called, " + text);
		return "print " + text;
	}
}
