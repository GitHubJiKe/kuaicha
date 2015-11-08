package com.ypf.kuaicha.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.RadioButton;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ypf.kuaicha.R;
import com.ypf.kuaicha.entity.Result;
import com.ypf.kuaicha.fragment.OrderFragment;
import com.ypf.kuaicha.fragment.RecordFragment;
import com.ypf.kuaicha.fragment.SearchFragment;
import com.ypf.kuaicha.fragment.SearchFragment.refreshDataCallBacks;

import java.util.ArrayList;

public class CenterActivity extends FragmentActivity implements refreshDataCallBacks {
	@ViewInject(R.id.viewPager)
	ViewPager viewpager;
	ArrayList<Fragment>fragmentlist;
	@ViewInject(R.id.radio0)
	private RadioButton r0;
	@ViewInject(R.id.radio1)
	public  RadioButton r1;
	@ViewInject(R.id.radio2)
	private RadioButton r2;
	private MyPagerAdapter adapter;
	private Result result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_center);
		ViewUtils.inject(this);
		setListener();
		setFragment();
		setAdapter();
		setDefault();
	}
	public void setDefault() {
		r0.setChecked(false);//Ĭ����ʾ��ѯ����
		r1.setChecked(true);//Ĭ����ʾ��ѯ����
		viewpager.setCurrentItem(1);//Ĭ����ʾ��ѯ����
	}
	private void setFragment() {
		fragmentlist=new ArrayList<Fragment>();
		fragmentlist.add(new RecordFragment());
		fragmentlist.add(new SearchFragment());
		fragmentlist.add(new OrderFragment());
		
	}
	
	@SuppressWarnings("deprecation")
	private void setListener() {
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				switch (arg0) {
				case 0:
					r0.setChecked(true);
					break;
				case 1:
					r1.setChecked(true);
					break;
				case 2:
					r2.setChecked(true);
					break;
				}
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
	public void doClick(View view){
		switch (view.getId()) {
		case R.id.radio0:
			viewpager.setCurrentItem(0);
			break;
		case R.id.radio1:
			viewpager.setCurrentItem(1);
			break;
		case R.id.radio2:
			viewpager.setCurrentItem(2);
			break;
		}
	}
	
	
	private void setAdapter() {
		adapter=new MyPagerAdapter(getSupportFragmentManager());
		viewpager.setAdapter(adapter);
	}
	
	
	class MyPagerAdapter extends FragmentPagerAdapter{

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		public Fragment getItem(int position) {
			return fragmentlist.get(position);
		}

		public int getCount() {
			return fragmentlist.size();
		}

	}


	@Override
	public void datafreshed(Result result) {
		this.result=result;
	}
	
}
