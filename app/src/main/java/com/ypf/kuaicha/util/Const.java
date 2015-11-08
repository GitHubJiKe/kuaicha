package com.ypf.kuaicha.util;

import com.thinkland.sdk.android.JuheData;

public interface Const {
	String REGIZTER_FAILURE="REGIZTER_FAILURE";
	String REGISTER_SUCCESS="REGISTER_SUCCESS";
	
	String REGISTER="REGISTER";
	
	String LOGIN_SUCCESS="LOGIN_SUCCESS";
	String LOGIN_FAILURE="LOGIN_FAILURE";
	
	String LOGIN="LOGIN";
	
	int STATUS_SEARCH_SUCCESS=200;
	int STATUS_SEARCH_FAILURE=201;
	
	int STATUS_CONNECT_SUCCESS=1110;
	int STATUS_CONNECT_FAILURE=1111;
	
	
	int HTTP_ID=43;
	String HTTP_COM_URL="http://v.juhe.cn/exp/com";
	String HTTP_INF_URL="http://v.juhe.cn/exp/index";
	String HTTP_METHORD=JuheData.GET;
	
	
	String RESULT_CODE="200";
	String RETURN_SUCCESSED="Return Successd!";
	String RETURN_SUCCESSED_X_Y="Return Successed";
	
	int Location_X_Y=2;
	int LOCATION_ID=15;
	String METHORD_TYPE=JuheData.GET;
	String METHORD_TYPE_POST=JuheData.POST;
	String LOCATION_URI="http://apis.juhe.cn/geo/";
	int LOCATION_SUCCESS_BACK=100;
	
	int SERVICE_ID=10;
	String SERVICE_URI="http://apis.juhe.cn/baidu/getCategory";
	int SERVICE_SUCCESS_BACK=101;
	int AROUND_SUCCESS_BACK=102;
	
	String SERVICE_FROM_CITY="http://apis.juhe.cn/baidu/getData";
	String SERVICE_FROM_X_Y ="http://apis.juhe.cn/baidu/getLocate";
	
}
