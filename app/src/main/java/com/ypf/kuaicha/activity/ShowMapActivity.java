package com.ypf.kuaicha.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.SnapshotReadyCallback;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;
import com.ypf.kuaicha.R;
import com.ypf.kuaicha.entity.Location;
import com.ypf.kuaicha.util.Const;
import com.ypf.kuaicha.util.Tools;


public class ShowMapActivity extends Activity implements Const {
	MapView mapview;
	private BaiduMap baibumap;
	private LocationClient locationClient;
	private  String city;
	private String street;
	private String province;
	private String country;
	private Intent intent;
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what==LOCATION_SUCCESS_BACK) {
				Location location=(Location) msg.getData().getSerializable("location");
				Location.Result result = location.getResult();
				Location.Result.Ext ext = result.getExt();
				country = ext.getCountry();
				province = ext.getProvince();
				city=ext.getCity();
				street = ext.getStreet();
				intent = getIntent();
				intent.putExtra("country", country);
				intent.putExtra("province", province);
				intent.putExtra("city", city);
				intent.putExtra("street", street);
				Log.i("intent", "" + intent);
				setResult(2000, intent);
			}
		};
	};
	private MyLocationListener myLocationListener;
	private BDLocation bdlocation;
	private double lat;
	private double lng;
	private String strlng;
	private String strlat;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_map);
		setViews();

		getCurrentLocation_X_Y();

		getNetLoationData();
		setMapListener();
	}
	
	public void doClick(View v){

		baibumap.snapshot(new SnapshotReadyCallback() {

			@Override
			public void onSnapshotReady(Bitmap bitmap) {
				showToast(bitmap);
			}

			public void showToast(Bitmap bitmap) {
				Toast toast = new Toast(ShowMapActivity.this);
				ImageView view = new ImageView(ShowMapActivity.this);
				view.setImageBitmap(bitmap);
				toast.setDuration(Toast.LENGTH_LONG);
				toast.setGravity(0, 0, 0);
				toast.setView(view);
				toast.show();
			}

		});
	}

	private void getCurrentLocation_X_Y() {
		bdlocation=new BDLocation();
		lng = bdlocation.getLongitude();
		lat=bdlocation.getLatitude();
		if (lat == 4.9E-324) {
			lat = 39.882348;
			lng = 116.471556;
		}
		strlng=String.valueOf(lng);
		strlat=String.valueOf(lat);
		Tools.log("lng+lat", strlng + "" + strlat);

	}

	private void getNetLoationData() {
		Tools.log("getNetLoationData", "getNetLoationData");
		Parameters params = new Parameters();
		params.add("lng",strlng);
		params.add("lat", strlat);
		params.add("type", "2");
		JuheData.executeWithAPI(this, LOCATION_ID, LOCATION_URI, METHORD_TYPE, params, new DataCallBack() {

			@Override
			public void onSuccess(int arg0, String arg1) {
				Location location = JSON.parseObject(arg1, Location.class);
				String resultcode = location.getResultcode();
				String reason = location.getReason();
				if (resultcode.equals(RESULT_CODE) && reason.equals("Successed!")) {
					Message msg = Message.obtain();
					msg.what = LOCATION_SUCCESS_BACK;
					Bundle data = new Bundle();
					data.putSerializable("location", location);
					msg.setData(data);
					handler.sendMessage(msg);
				}
			}

			@Override
			public void onFinish() {

			}

			@Override
			public void onFailure(int arg0, String arg1, Throwable arg2) {
				Tools.log("getNetLoationData", "获取位置失败");
			}
		});

	}
	private void setMapListener() {
		baibumap.setOnMapClickListener(new OnMapClickListener() {
			
			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				return false;
			}
			
			@Override
			public void onMapClick(LatLng selectedPosition) {
				baibumap.clear();
				move(selectedPosition);
				addMarker(selectedPosition);
				lat=selectedPosition.latitude;
				lng=selectedPosition.longitude;
				strlng=String.valueOf(lng);
				strlat=String.valueOf(lat);
				getNetLoationData();
			}
		});
		
	}
	
	private void move(LatLng cuurentPotion) {
		MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(
				cuurentPotion, 17);

		mapview.getMap().animateMapStatus(mapStatusUpdate);
	}

	private void addMarker(LatLng cuurentPotion) {
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position(cuurentPotion);
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_location);
		markerOptions.icon(bitmap);
		mapview.getMap().addOverlay(markerOptions);
	}

	private void setViews() {
		mapview=(MapView) findViewById(R.id.mv_baidu_map);
		//�ÿ���еĽӿ�ָ��ʵ����
		MyLocationListener myLocationListener=new MyLocationListener();
		baibumap=mapview.getMap();

		locationClient=new LocationClient(this);

		LocationClientOption option=new LocationClientOption();

		option.setOpenGps(true);

		option.setCoorType("bd09ll");

		option.setScanSpan(1);


		locationClient.setLocOption(option);

		locationClient.registerLocationListener(myLocationListener);
		locationClient.start();
	}

	class MyLocationListener implements BDLocationListener
	{

		@Override
		public void onReceiveLocation(BDLocation bDLocation) {
			try {

				double latitude=bDLocation.getLatitude();

				double longitude=bDLocation.getLongitude();


				if (latitude == 4.9E-324) {
					latitude = 39.882348;
					longitude = 116.471556;
				}

				LatLng cuurentPotion=new LatLng(latitude, longitude);
				MapStatusUpdate mapStatusUpdate= MapStatusUpdateFactory.newLatLngZoom(cuurentPotion, 17);

				mapview.getMap().animateMapStatus(mapStatusUpdate);


				MarkerOptions markerOptions=new MarkerOptions();
				markerOptions.position(cuurentPotion);
				BitmapDescriptor bitmap= BitmapDescriptorFactory.fromResource(R.drawable.icon_location);
				markerOptions.icon(bitmap);
				mapview.getMap().addOverlay(markerOptions);

			} catch (Exception e) {
				e.printStackTrace();;
			}		
		}
	}
}

