package com.ypf.kuaicha.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ypf.kuaicha.R;
import com.ypf.kuaicha.entity.Information;
import com.ypf.kuaicha.entity.List;
import com.ypf.kuaicha.entity.Result;

import java.util.ArrayList;
import java.util.Collections;

public class DetialActivity extends Activity {
	@ViewInject(R.id.lv_detial)
	ListView lv_detial;
	private java.util.List<List> list1;
	private BaseAdapter adapter;
	private Result result;
	private ArrayList<List> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detial);
		ViewUtils.inject(this);
		setData();
	}
	private void setAdapter() {
		adapter=new DetialShowAdapter();
		lv_detial.setAdapter(adapter);
	}
	@SuppressWarnings("unchecked")
	private void setData() {
		Intent intent=getIntent();
		list=(ArrayList<List>) intent.getSerializableExtra("list");
		if (null!=list) {
			Collections.reverse(list);
			list1=list;
			Log.i("���ܺ�", ""+list1);
			setAdapter();
		}
		Information info = (Information) intent.getSerializableExtra("info");
		if (null!=info) {
			if (null == list1) {
				list1 = info.getResult().getList();
			} else {
				list1.clear();
				adapter.notifyDataSetChanged();
				list1 = info.getResult().getList();
			}
			if (null != list1) {
				Collections.reverse(list1);
				setAdapter();
			}
		}
	}
	
	
	class DetialShowAdapter extends BaseAdapter {
		public DetialShowAdapter() {
		}
		LayoutInflater inflater;
		@Override
		public int getCount() {
			return list1.size();
		}

		@Override
		public List getItem(int position) {
			return list1.get(position);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder=null;
			if (convertView==null) {
				holder=new ViewHolder();
				inflater=LayoutInflater.from(DetialActivity.this);
				convertView=inflater.inflate(R.layout.inf_item, null);
				holder.tv1=(TextView) convertView.findViewById(R.id.textView1);
				holder.tv2=(TextView) convertView.findViewById(R.id.textView2);
				holder.tv3=(TextView) convertView.findViewById(R.id.textView3);
				convertView.setTag(holder);
			}
			holder=(ViewHolder) convertView.getTag();

			List mark2 = list1.get(position);
			holder.tv1.setText(mark2.getDatetime());
			holder.tv2.setText(mark2.getRemark());
			holder.tv3.setText(mark2.getZone());

			return convertView;
		}

		class ViewHolder{
			TextView tv1;
			TextView tv2;
			TextView tv3;
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
	}
	
}
