package com.joinsoft.demo.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;

import com.joinsoft.demo.server.Print;


public class ProxyClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
		factory.setServiceClass(Print.class);
		factory.setAddress("http://localhost:9000/print");
		
		factory.getFeatures().add(new LoggingFeature());
		factory.getInInterceptors().add(new WSS4JInInterceptor(setInMap()));
		factory.getOutInterceptors().add(new WSS4JOutInterceptor(setOutMap()));
		
		Print client = (Print) factory.create();
		
		String reply = client.print("proxy test");
		System.out.println("Server said: " + reply); 
	}
	
	
	public static Map<String, Object> setInMap(){
		Map<String, Object> inProps = new HashMap<String, Object>();
        //  Timestamp Signature Encrypt
		inProps.put("action", "UsernameToken");

		inProps.put("passwordType", "PasswordText");

		inProps.put("user", "abcd");
		inProps.put("passwordCallbackClass", "com.joinsoft.demo.client.UTPasswordCallback");
		
		return inProps;
	}
	
	public static Map<String, Object> setOutMap(){
		Map<String, Object> outProps = new HashMap<String, Object>();
        //  Timestamp Signature Encrypt
        outProps.put("action", "UsernameToken");

        outProps.put("passwordType", "PasswordDigest");

        outProps.put("user", "abcd");
        outProps.put("passwordCallbackClass", "com.joinsoft.demo.client.UTPasswordCallback");
        
        return outProps;
	}

}
