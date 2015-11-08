package com.ypf.kuaicha;

import com.baidu.mapapi.SDKInitializer;
import com.thinkland.sdk.android.JuheSDKInitializer;

import android.app.Application;

public class TApplication extends Application {
	@Override
	public void onCreate() {
		JuheSDKInitializer.initialize(getApplicationContext());
		SDKInitializer.initialize(this);
		super.onCreate();
	}
}
