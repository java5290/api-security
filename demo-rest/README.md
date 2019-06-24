# API接口安全方案之Rest
appid：第三方用户唯一凭证

secret：第三方用户唯一凭证密钥

access_token：根据appid和secret生成JWTToken

IP白名单：设置调用接口的IP白名单池，起双重保护，必须保证我方能获取到客户端的真实IP，否则此方式不可行
请求：每一次请求必须携带access_token字符串，最好通过使用header传递access_token.
## 服务端token生成 ##
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

## 服务端拦截器 ##
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
