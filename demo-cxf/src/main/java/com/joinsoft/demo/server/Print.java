package com.joinsoft.demo.server;

import javax.jws.WebService;

@WebService
public interface Print {

	String print(String text);
}
