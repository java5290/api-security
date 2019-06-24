package com.joinsoft.demo.server;

import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.Endpoint;

import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.common.ConfigurationConstants;

public class Server {
	
	protected Server() {
		
		/** TestClient **/
		// START SNIPPET: publish
        System.out.println("Starting Server");
        
//        PrintService implementor = new PrintService();
//		String address = "http://localhost:9000/print";
//		Endpoint.publish(address, implementor);
		
		// END SNIPPET: publish
		
        
        /** DynamicClient **/
		// other publish
//        PrintService implementor = new PrintService(); 
//		JaxWsServerFactoryBean svrFactory = new JaxWsServerFactoryBean(); 
//		svrFactory.setServiceClass(Print.class); 
//		svrFactory.setAddress("http://localhost:9000/print"); 
//		svrFactory.setServiceBean(implementor); 
//		svrFactory.getFeatures().add(new LoggingFeature());
//		svrFactory.create();
        
        /** ProxyClient **/
     // Create our service implementation
        PrintService printService = new PrintService();
         
        // Create our Server
        ServerFactoryBean svrFactory = new ServerFactoryBean();
        svrFactory.setServiceClass(Print.class);
        svrFactory.setAddress("http://localhost:9000/print");
        svrFactory.setServiceBean(printService);
        svrFactory.getFeatures().add(new LoggingFeature());
        
        svrFactory.getInInterceptors().add(new WSS4JInInterceptor(setInMap()));
        svrFactory.getOutInterceptors().add(new WSS4JOutInterceptor(setOutMap()));
        
        svrFactory.create(); 
	}
	
	private Map<String, Object> setInMap(){
		Map<String, Object> inProps = new HashMap<String, Object>();
		// 设置action
		inProps.put("action", "UsernameToken");

		inProps.put("passwordType", "PasswordDigest");
		inProps.put("passwordCallbackClass", "com.joinsoft.demo.server.UTPasswordCallback");

		inProps.put("user", "Alice");
		
		return inProps;
	}
	
	private Map<String, Object> setOutMap(){
		Map<String, Object> outProps = new HashMap<String, Object>();
//		properties.put(ConfigurationConstants.ACTION, ConfigurationConstants.USERNAME_TOKEN);
		outProps.put("action", "UsernameToken");

		outProps.put("passwordType", "PasswordText");
		outProps.put("passwordCallbackClass", "com.joinsoft.demo.server.UTPasswordCallback");

		outProps.put("user", "Alice");
		
		return outProps;
	}

	public static void main(String[] args) throws InterruptedException {
		
		new Server();
		
		System.out.println("Server ready...");

        Thread.sleep(5 * 60 * 1000);
        System.out.println("Server exiting");
        System.exit(0);
	}

}
