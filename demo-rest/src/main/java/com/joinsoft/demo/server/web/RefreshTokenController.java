package com.joinsoft.demo.server.web;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.joinsoft.demo.util.JJWTUtils;

/**
 * access_token字符串获取
 * @author admin
 */
@RestController
public class RefreshTokenController {

	/**
	 * 根据appid和secret获取access_token
	 * @param appid
	 * @param secret
	 * @return
	 */
	@GetMapping("/getAccessToken/{appid}/{secret}")
	public String getAccessToken(@PathVariable String appid, @PathVariable String secret) {
		try {
			// 生成token
			String access_token = JJWTUtils.createToken(appid, secret, 120*60);
			// 成功返回数据格式
			return "{\"access_token\":\""+access_token+"\",\"expires_in\":7200}";
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 刷新失败，返回数据格式
		return "{\"errcode\":40013,\"errmsg\":\"invalid appid\"}";
	}
	
}
