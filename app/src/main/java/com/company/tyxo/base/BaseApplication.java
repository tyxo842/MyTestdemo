package com.company.tyxo.base;

import android.app.Application;

public class BaseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}

	private static BaseApplication instance;

	public static BaseApplication getInstance() {
		return instance;
	}
}
