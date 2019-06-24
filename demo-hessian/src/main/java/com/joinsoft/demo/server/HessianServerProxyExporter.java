package com.joinsoft.demo.server;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.web.util.NestedServletException;

public class HessianServerProxyExporter extends HessianServiceExporter {

	private Logger log = LoggerFactory.getLogger(HessianServerProxyExporter.class);
			
	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取认证header头信息，数据格式：Basic bHp4Onh6bA==
		String authorization = request.getHeader("authorization");
		
		if (authorization == null || "".equals(authorization)) {
			throw new NestedServletException("用户名或者密码为空!");
		}
		// 拆解header头信息，第二个元素为用户名和密码的Base64编码后的密文
		String[] _basicAuth = authorization.split(" ");
		String auth = _basicAuth[1];
		
		// 解码为明文 用户名:密码
		auth = new String(Base64.getDecoder().decode(auth));
		log.info(auth);
		
		// 获取密码
		String password = auth.split(":")[1];
		// 校验密码
		if(!"xzl".equals(password)) {
			throw new NestedServletException("用户名、密码校验错误!");
		}

		super.handleRequest(request, response);
	}
}
