# API接口安全方案之Hessian
hessian版本：4.0.38

安全方案：自带的HTTP basic authentication认证方案
## 服务端HessianServerProxyExporter组件 ##
该组件继承HessianServiceExporter，重写handleRequest方法。在header中获取到认证信息，解码该认证信息，校验用户名密码。

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
## 客户端 ##

使用HessianProxyFactory创建客户端连接的时候，必须设置用户名和密码，缺一不可。

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
## 应用说明 ##
实际应用的时候，最起码密码是密文；

可以扩展安全性更高的token方式，在此不做示例。

