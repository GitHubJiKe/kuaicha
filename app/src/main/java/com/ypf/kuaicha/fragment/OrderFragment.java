//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : KuaiDiChaXun
//  @ File Name : OrderFragment.java
//  @ Date : 2015/10/11
//  @ Author : 
//
//

package com.ypf.kuaicha.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.ypf.kuaicha.R;
import com.ypf.kuaicha.util.Const;


public class OrderFragment extends Fragment implements Const {
	private EditText currentposition;
	private EditText targetposition;
	private ListView listview;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.activity_order, null);
		setViews(view);
		setListener();
		return view;
	}
	private void setListener() {
		currentposition.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=getActivity().getPackageManager().getLaunchIntentForPackage("com.ypf.baidumapdemo");
				startActivityForResult(intent, 1000);
//				Intent intent=new Intent(getActivity(), ShowMapActivity.class);
//				startActivityForResult(intent, 1000);
			}
		});
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("Onactivity", "Onactivity"+requestCode+" "+resultCode);
		if (1000==requestCode&&2000==resultCode) {
//			ContentResolver cr = getActivity().getContentResolver();
//			Uri uri=data.getData();
//			try {
//				InputStream ois = cr.openInputStream(uri);
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
			String province = data.getStringExtra("province");
			String country = data.getStringExtra("country");
			String city = data.getStringExtra("city");
			String street = data.getStringExtra("street");
			currentposition.setText(country+","+province+","+city+","+street);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	private void setViews(View view) {
		currentposition=(EditText) view.findViewById(R.id.current);
		targetposition=(EditText) view.findViewById(R.id.targetposition);
		listview=(ListView) view.findViewById(R.id.listView1);
	}
	
}
