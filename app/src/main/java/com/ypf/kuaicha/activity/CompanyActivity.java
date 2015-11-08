package com.ypf.kuaicha.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;
import com.ypf.kuaicha.R;
import com.ypf.kuaicha.entity.Company;
import com.ypf.kuaicha.entity.Company.Result;
import com.ypf.kuaicha.util.Const;
import com.ypf.kuaicha.util.Logo;
import com.ypf.kuaicha.util.Tools;

import java.util.ArrayList;
import java.util.List;

public class CompanyActivity extends Activity implements Logo,Const {
	@ViewInject(R.id.gridView1)
	GridView gridview;
	private MyAdapter adapter;
	private String result;
	private LayoutInflater layoutinflater;
	private Company company;
	private List<Result>companylist;
	private String logo[]={SF,STO,YTO,YD,TT,EMS,ZTO,HTO,QF,DB};
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			if (msg.what==STATUS_SEARCH_SUCCESS) {
				setListener();
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company);
		ViewUtils.inject(this);
		getData();
	}
	private void getData() {
		Parameters params=new Parameters();
		JuheData.executeWithAPI(this, 43, "http://v.juhe.cn/exp/com", JuheData.GET, params, new DataCallBack() {

			@Override
			public void onSuccess(int arg0, String arg1) {
				result=arg1;
				//���÷����������
				initdata(result);
				//����������
				setAdapter();
				Message msg=Message.obtain();
				msg.what=STATUS_SEARCH_SUCCESS;
				//���ݳ�����ϣ�����Ϣ���õ�������¼�
				handler.sendMessage(msg);
			}

			@Override
			public void onFinish() {
			
			}
			@Override
			public void onFailure(int arg0, String arg1, Throwable arg2) {
				Tools.showToast("联网失败", CompanyActivity.this);
			}
		});
	}
	
	private void initdata(String result) {
		company=new Company();
		companylist=new ArrayList<Result>();
		//����json�ַ���
		company=JSON.parseObject(result, Company.class);
		companylist=company.getResult();
	}
	
	private void setAdapter() {
		adapter=new MyAdapter();
		gridview.setAdapter(adapter);
		
	}
	private void setListener() {
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Result company=companylist.get(position);
				Tools.showToast(company.getCom().toString(), CompanyActivity.this);
				Intent inetnt=getIntent();
				inetnt.putExtra("result", company);
				setResult(1000, inetnt);
				finish();
			}
		});
	}
	
	
	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return companylist.size();
		}

		@Override
		public Object getItem(int position) {
			return companylist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder=null;
			if (convertView==null) {
				holder=new ViewHolder();
				layoutinflater=LayoutInflater.from(CompanyActivity.this);
				convertView=layoutinflater.inflate(R.layout.com_item, null);
				holder.tv=(TextView) convertView.findViewById(R.id.textView1);
				holder.iv=(ImageView) convertView.findViewById(R.id.imageView1);
				convertView.setTag(holder);
			}
			holder=(ViewHolder) convertView.getTag();
			Result company=companylist.get(position);
			holder.tv.setText(company.getCom());
			/**
			 * ʹ��BitmapUtil��������ͼƬ-��ݹ�˾Logo
			 */
			final BitmapUtils bitmapUtils=new BitmapUtils(CompanyActivity.this);
			bitmapUtils.configDefaultAutoRotation(true);
			bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
			bitmapUtils.configDefaultBitmapMaxSize(80, 80);
			bitmapUtils.display(holder.iv, logo[position], null, new BitmapLoadCallBack<View>() {

				@Override
				public void onLoadCompleted(View arg0, String arg1,
						Bitmap arg2, BitmapDisplayConfig arg3,
						BitmapLoadFrom arg4) {
					ImageView ImageView=(ImageView)arg0;
					ImageView.setImageBitmap(arg2);
				}

				@Override
				public void onLoadFailed(View arg0, String arg1, Drawable arg2) {
					ImageView ImageView=(ImageView)arg0;
					ImageView.setBackgroundResource(R.drawable.ic_launcher);;
				}
			});
			return convertView;
		}
	}
	class ViewHolder{
		ImageView iv;
		TextView tv;
	}
	
}
