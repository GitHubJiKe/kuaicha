package com.ypf.kuaicha.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ypf.kuaicha.R;
import com.ypf.kuaicha.biz.RegisterBiz;
import com.ypf.kuaicha.entity.User;
import com.ypf.kuaicha.util.Const;
import com.ypf.kuaicha.util.Tools;

public class RegisterActivity extends Activity implements Const {
	@ViewInject(R.id.et_username)
	EditText et_username;
	@ViewInject(R.id.et_password)
	EditText et_password;
	@ViewInject(R.id.et_password2)
	EditText et_password2;
	User user;
	MyReciver reciver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		ViewUtils.inject(this);
	}

	private void registerReciver() {
		IntentFilter filter=new IntentFilter(REGISTER);
		reciver=new MyReciver();
		registerReceiver(reciver, filter);
	}

	public void doClick(View v){
		String name = et_username.getText().toString();
		String password = et_password.getText().toString();
		String password2 = et_password.getText().toString();
		if(name.equals("")||password.equals("")||password2.equals("")){
			Tools.showToast("请完善信息", RegisterActivity.this);
		}else{
		if (password.equals(password2)) {
			user=new User();
			user.setName(name);
			user.setPassword(password);
			user.setList(null);
			Log.i("user", user+"");
			RegisterBiz.register(RegisterActivity.this, user);
		}else {
			Tools.showToast("请检查输入", RegisterActivity.this);
		}}
	}

	@Override
	protected void onResume() {
		registerReciver();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(reciver);
		finish();
		super.onDestroy();
	}

	class MyReciver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.i("action", action);
			if (action.equals(REGISTER)) {
				if (intent.getStringExtra(REGISTER).equals(REGISTER_SUCCESS)) {
					Tools.showToast("注册成功", context);
					Intent data = ((RegisterActivity) context).getIntent();
					data.putExtra("user", user);
					((RegisterActivity) context).setResult(1000, data);
					finish();
				} else if(intent.getStringExtra(REGISTER).equals(REGIZTER_FAILURE)) {
					Tools.showToast("注册失败", context);
				}
			}
			
		}

	}
}
