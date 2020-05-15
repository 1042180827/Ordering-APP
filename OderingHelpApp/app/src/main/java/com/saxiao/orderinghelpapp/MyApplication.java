package com.saxiao.orderinghelpapp;

import android.app.Application;
import android.content.Context;


public class MyApplication extends Application {
	private static Context context;

	@Override public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		//x.Ext.init(this);
	}

	/**
	 * 全局上下文
	 * */
	public static Context getAppContext() {
		return context;
	}
}
