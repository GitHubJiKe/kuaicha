//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : KuaiDiChaXun
//  @ File Name : MyOrderFragmentAdapter.java
//  @ Date : 2015/10/11
//  @ Author : 
//
//



package com.ypf.kuaicha.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ypf.kuaicha.R;
import com.ypf.kuaicha.entity.Result;

import java.util.ArrayList;


public class MyRecordFragmentAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<Result>list;
	private int resource;
	private LayoutInflater inflater;
	public MyRecordFragmentAdapter(Context context, ArrayList<Result> list,
			int resource) {
		super();
		this.context = context;
		this.list = list;
		this.resource = resource;
		this.inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=new ViewHolder();
		if (convertView==null) {
			holder=new ViewHolder();
			convertView=inflater.inflate(resource, null);
			holder.com=(TextView) convertView.findViewById(R.id.company);
			holder.num=(TextView)convertView.findViewById(R.id.number);
			holder.sta=(TextView)convertView.findViewById(R.id.state);
			convertView.setTag(holder);
		}
		holder=(ViewHolder) convertView.getTag();
		Result result=list.get(position);
		Log.i("Result", result+"");
		holder.com.setText("公司："+result.getCompany().toString());
		holder.num.setText("单号："+result.getNo().toString());
		if (result.getStatus().toString().equals("1")) {
			holder.sta.setText("已签收");
		}else{
			holder.sta.setText("在途中……");
		}
		
		return convertView;
	}
	
	class ViewHolder{
		TextView com;
		TextView num;
		TextView sta;
	}

}
