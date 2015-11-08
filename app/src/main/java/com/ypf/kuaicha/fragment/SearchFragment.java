package com.ypf.kuaicha.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;
import com.ypf.kuaicha.R;
import com.ypf.kuaicha.activity.CaptureActivity;
import com.ypf.kuaicha.activity.CompanyActivity;
import com.ypf.kuaicha.activity.DetialActivity;
import com.ypf.kuaicha.entity.Information;
import com.ypf.kuaicha.entity.List;
import com.ypf.kuaicha.entity.Result;
import com.ypf.kuaicha.entity.User;
import com.ypf.kuaicha.util.Const;
import com.ypf.kuaicha.util.Tools;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements Const {
	EditText et_company;
	EditText et_number;
	Button btn_search;
	ImageView imageview;
	private Information info;
	private Result result;
	private User user;
	String com="";
	private java.util.List<Result> list=new ArrayList<Result>();
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			if (msg.what==STATUS_SEARCH_SUCCESS) {

				info=(Information)msg.getData().getSerializable("info");
				result=info.getResult();
				list.add(result);
				user.setList(list);
				result.setUser(user);
				Tools.showToast("保存成功，可在记录中查看", getActivity());

				rdcb=(refreshDataCallBacks) getActivity();
				rdcb.datafreshed(result);


				DbUtils db=DbUtils.create(getActivity());
				try {
					Result findFirst = db.findFirst(Selector.from(Result.class).where("no", "=", result.getNo().toString()));
					if (null==findFirst) {
						java.util.List<List> list = (java.util.List<List>) result.getList();
						for (int i = 0; i < list.size(); i++) {
							list.get(i).setResult(result);
						}
						db.saveBindingId(result);
						db.saveBindingIdAll(list);
						Result list2 = db.findFirst(Result.class);
					}else {
						Log.i("not save", "not save!!!");
					}
				} catch (DbException e) {
					e.printStackTrace();
				}



				Intent intent=new Intent(getActivity(),DetialActivity.class);
				intent.putExtra("info", info);
				startActivity(intent);
			}else if(msg.what==STATUS_SEARCH_FAILURE) {
				Tools.showToast("查询失败，请检查输入", getActivity());
			}else if(msg.what==STATUS_CONNECT_FAILURE) {
				Tools.showToast("联网失败，请检查网络", getActivity());
			}
		};
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = setViews(inflater);
		setListener();
		user=(User) getActivity().getIntent().getSerializableExtra("user");
		return view;
	}
	public View setViews(LayoutInflater inflater) {
		View view=inflater.inflate(R.layout.fragment_search, null);
		et_company=(EditText) view.findViewById(R.id.et_company);
		et_number=(EditText) view.findViewById(R.id.et_number);
		btn_search=(Button)view.findViewById(R.id.btn_search);
		imageview=(ImageView) view.findViewById(R.id.imageView1);
		return view;
	}
	private refreshDataCallBacks rdcb;
	public static interface refreshDataCallBacks{
		public void datafreshed(Result result);
	}


	private void setListener() {


		imageview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent=new Intent(getActivity(), CaptureActivity.class);
				startActivityForResult(intent, 0);
			}
		});

		et_company.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getActivity(), CompanyActivity.class);
				startActivityForResult(intent, 100);
			}
		});

		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Tools.showDialog(getActivity(), "正在查询");
				Parameters params = new Parameters();
				params.add("com", com);
				params.add("no", et_number.getText().toString());
				JuheData.executeWithAPI(getActivity(),HTTP_ID, HTTP_INF_URL, HTTP_METHORD, params, new DataCallBack() {

					private Information informationEntity;

					@Override
					public void onSuccess(int arg0, String arg1) {
						Tools.closeDialog();
						informationEntity=JSON.parseObject(arg1, Information.class);
						Log.i("resultcode", informationEntity.getResultcode());
						if (informationEntity.getResultcode().equals("200")) {
							Message msg=Message.obtain();
							msg.what=STATUS_SEARCH_SUCCESS;
							Bundle data=new Bundle();
							data.putSerializable("info", informationEntity);
							msg.setData(data);
							//����Ϣ����ֵ
							handler.sendMessage(msg);
						}else {
							Message msg=Message.obtain();
							msg.what=STATUS_SEARCH_FAILURE;
							handler.sendMessage(msg);
						}

					}

					@Override
					public void onFinish() {

					}

					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {
						Message msg=Message.obtain();
						msg.what=STATUS_CONNECT_FAILURE;
						handler.sendMessage(msg);
					}
				});
			}
		});

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == getActivity().RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			et_number.setText(scanResult);
		}else 
			if (1000==resultCode) {
				com.ypf.kuaicha.entity.Company.Result result = (com.ypf.kuaicha.entity.Company.Result)data.getSerializableExtra("result");
				String comname = result.getCom();
				et_company.setText(comname);
				com=result.getNo();
			}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
