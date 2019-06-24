# API接口安全方案之CXF
## JAX-WS ##
协议：SOAP

安全标准：认证令牌方式

action：UsernameToken，传递一个用户名和加密密码到另一端

passwordType：使用UsernameToken认证的密码编码设置，输出默认PasswordDigest，输入没有默认值

### 服务端WSS4JInInterceptor构造器参数 ###
    Map<String, Object> inProps = new HashMap<String, Object>();
    // 设置action
    inProps.put("action", "UsernameToken");
    
    inProps.put("passwordType", "PasswordDigest");
    inProps.put("passwordCallbackClass", "com.joinsoft.demo.server.UTPasswordCallback");
    
    inProps.put("user", "Alice");
    return inProps;
### 服务端WSS4JOutInterceptor构造器参数 ###
    Map<String, Object> outProps = new HashMap<String, Object>();
    outProps.put("action", "UsernameToken");
    
    outProps.put("passwordType", "PasswordText");
    outProps.put("passwordCallbackClass", "com.joinsoft.demo.server.UTPasswordCallback");
    
    outProps.put("user", "Alice");
    return outProps;
### 服务端UTPasswordCallback ###

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; i++) {
            WSPasswordCallback pc = (WSPasswordCallback)callbacks[i];
            String pass = passwords.get(pc.getIdentifier());
            if (pass != null) {
                pc.setPassword(pass);
                return;
            }
        }
    }

### 创建服务 ###
    // Create our Server
    ServerFactoryBean svrFactory = new ServerFactoryBean();
    svrFactory.setServiceClass(Print.class);
    svrFactory.setAddress("http://localhost:9000/print");
    svrFactory.setServiceBean(printService);
    svrFactory.getFeatures().add(new LoggingFeature());
    
    svrFactory.getInInterceptors().add(new WSS4JInInterceptor(setInMap()));
    svrFactory.getOutInterceptors().add(new WSS4JOutInterceptor(setOutMap()));
    svrFactory.create();

### 客户端WSS4JInInterceptor构造器参数 ###
    Map<String, Object> inProps = new HashMap<String, Object>();
    //  Timestamp Signature Encrypt
    inProps.put("action", "UsernameToken");
    
    inProps.put("passwordType", "PasswordText");
    
    inProps.put("user", "abcd");
    inProps.put("passwordCallbackClass", "com.joinsoft.demo.client.UTPasswordCallback");
    return inProps;

### 客户端WSS4JOutInterceptor构造器参数 ###
    Map<String, Object> outProps = new HashMap<String, Object>();
    //  Timestamp Signature Encrypt
    outProps.put("action", "UsernameToken");
    
    outProps.put("passwordType", "PasswordDigest");
    
    outProps.put("user", "abcd");
    outProps.put("passwordCallbackClass", "com.joinsoft.demo.client.UTPasswordCallback");
    return outProps;

### 创建客户端 ###
    ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
    factory.setServiceClass(Print.class);
    factory.setAddress("http://localhost:9000/print");
    
    factory.getFeatures().add(new LoggingFeature());
    factory.getInInterceptors().add(new WSS4JInInterceptor(setInMap()));
    factory.getOutInterceptors().add(new WSS4JOutInterceptor(setOutMap()));
    
    Print client = (Print) factory.create();
    String reply = client.print("proxy test");

