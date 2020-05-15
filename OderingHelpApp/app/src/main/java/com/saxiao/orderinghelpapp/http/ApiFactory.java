package com.saxiao.orderinghelpapp.http;


public class ApiFactory {
	private static Api api;

	public static Api getApi() {
		if (api == null) {
			api = HttpClient.getInstance("http://192.168.31.253:8080/BootSpringBoot/",true,null).createService(Api.class);
		}
		return api;
	}
}
