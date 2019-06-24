package com.joinsoft.demo.client;

import java.io.IOException;

import org.apache.commons.logging.Log;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Client {

	/**
	 * 第一步：先执行getToken()方法获取token；
	 * 第二步：根据getToken获取到的token，测试API接口验证；
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		//获取token
		//getToken();
		// 测试异步请求，get
		String access_token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpZXNsYWIiLCJleHAiOjE1NjEzNTYyMzJ9.e4aPpnChg-6gq_Yxc15ybe6hSaEnKCZI27_4DcO36us";
		getAsync(access_token);
	}
	
	// 获取token
	public static void getToken() {
		String url = "http://127.0.0.1/demo-rest/getAccessToken/ieslab/balsei";
		OkHttpClient okHttpClient = new OkHttpClient();
		final Request request = new Request.Builder()
		        .url(url)
		        .get()//默认就是GET请求，可以不写
		        .build();
		
		
		final Call call = okHttpClient.newCall(request);
		new Thread(new Runnable() {
		    @Override
		    public void run() {
		        try {
		            Response response = call.execute();
		            System.out.println("access_token: " + response.body().string());
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}).start();
		
	}
	
	// get异步请求
	public static void getAsync(String access_token) {
		String url = "http://127.0.0.1/demo-rest/test/sayOk?username=李中新";
		OkHttpClient okHttpClient = new OkHttpClient();
		final Request request = new Request.Builder()
		        .url(url)
		        .addHeader("access_token", access_token)
		        .get()//默认就是GET请求，可以不写
		        .build();
		
		Call call = okHttpClient.newCall(request);
		call.enqueue(new Callback() {
		    @Override
		    public void onFailure(Call call, IOException e) {
		        System.out.println("");
		    }

		    @Override
		    public void onResponse(Call call, Response response) throws IOException {
		    	System.out.println("onResponse: " + response.body().string());
		    }
		});
	}

}
