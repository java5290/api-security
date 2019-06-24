package com.joinsoft.demo.server.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.joinsoft.demo.util.JJWTUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

public class DemoInterceptor implements HandlerInterceptor {

	private static final Logger log = LoggerFactory.getLogger(DemoInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("处理前方法，校验token开始");
		String access_token = request.getHeader("access_token");
		// 此处第二个参数，测试写死，实际应用环境应该从redis中获取
		try {
			Claims claims = JJWTUtils.parseJWT(access_token, "ieslab");
			log.info("处理前方法，校验token成功");
			return true;
		} catch (ExpiredJwtException e) {
			e.printStackTrace();
		}
		log.info("处理前方法，校验token失败");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("处理后方法");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("处理完成方法");
	}

}
