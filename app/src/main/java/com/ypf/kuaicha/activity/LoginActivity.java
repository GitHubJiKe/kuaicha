package com.ypf.kuaicha.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ypf.kuaicha.R;
import com.ypf.kuaicha.biz.LoginBiz;
import com.ypf.kuaicha.entity.User;
import com.ypf.kuaicha.util.Const;
import com.ypf.kuaicha.util.Tools;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class LoginActivity extends Activity implements Const {
	@ViewInject(R.id.et_username)
	EditText et_username;
	@ViewInject(R.id.et_password)
	EditText et_password;
	@ViewInject(R.id.yanzhengma)
	EditText et_yanzhengma;
	@ViewInject(R.id.customtextview)
	private TextView ctv;
	@ViewInject(R.id.btn_login)
	Button btn_login;
	@ViewInject(R.id.btn_register)
	Button btn_register;
	private User user;
	@ViewInject(R.id.checkBox1)
	private CheckBox checkbox;
	private MyReciver reciver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
		getsavedtoSharedPreference();
		ctv.setText(getRandom());
		ctv.setBackgroundColor(getRandomColor());
		ctv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ctv.setText(getRandom());
				ctv.setBackgroundColor(getRandomColor());
			}
		});
	}

	private int getRandomColor() {
		Random random=new Random();
		int r = random.nextInt(256);
		int g= random.nextInt(256);
		int b = random.nextInt(256);
		int mColor = Color.rgb(r, g, b);
		return mColor;
	}
	private String getRandom() {
		Random random=new Random();
		Set<Integer> set = new HashSet<Integer>();
		while (set.size() < 4)
		{
			int randomInt = random.nextInt(10);
			set.add(randomInt);
		}
		StringBuffer sb = new StringBuffer();
		for (Integer i : set)
		{
			sb.append("" + i);
		}
		return sb.toString();
	}

	public void doClick(View v){
		switch (v.getId()) {
		case R.id.btn_login:

			//登录biz
			user=new User();
			String us_Name=et_username.getText().toString();
			user.setName(us_Name);
			String us_Password=et_password.getText().toString();
			user.setPassword(us_Password);
			if (checkbox.isChecked()) {
				savedtoSharedPreference();
			}
			String yan1=ctv.getText().toString();
			String yan2=et_yanzhengma.getText().toString();
			if (yan1.equals(yan2)){
				LoginBiz.login(this, user);
			}else{
				Tools.showToast("验证码输入错误",LoginActivity.this);
				ctv.setText(getRandom());
				ctv.setBackgroundColor(getRandomColor());
			}

			break;

		case R.id.btn_register:
			//跳转登录界面
			Intent intent=new Intent(this, RegisterActivity.class);
			startActivityForResult(intent, 1000);
			break;
		}
	}

	/**
	 * 保存登录信息，以供下次方便登陆
	 */
	private void savedtoSharedPreference() {
		SharedPreferences sp=getSharedPreferences("USER",MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putString("username", user.getName().toString());
		edit.putString("userpassword", user.getPassword().toString());
		edit.commit();
	}
	
	
	 /**
     * 获取保存的用户登录信息
     */
	private void getsavedtoSharedPreference() {
		SharedPreferences sp=getSharedPreferences("USER", MODE_PRIVATE);
		String name = sp.getString("username", "");
		String password = sp.getString("userpassword", "");
		setUserinformation(name, password);
	}
	
	
	/**
	 * 自动设置新注册用户登录信息
	 * @param name
	 * @param password
	 */
	public void setUserinformation(String name, String password) {
		et_username.setText(name);
		et_password.setText(password);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (1000==requestCode) {
			if (null!=data) {
				user=(User) data.getSerializableExtra("user");
				setText();
			}
			
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void setText() {
		et_username.setText(user.getName());
		et_password.setText(user.getPassword());
	}

	@Override
	protected void onResume() {
		registerReciver();
		super.onResume();
	}

	private void registerReciver() {
		if (null==reciver) {
			reciver=new MyReciver();
			IntentFilter filter=new IntentFilter(LOGIN);
			registerReceiver(reciver, filter);
		}else {
			IntentFilter filter=new IntentFilter(LOGIN);
			registerReceiver(reciver, filter);
		}


	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(reciver);
		super.onDestroy();
	}

	class MyReciver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.i("action", action);
			if (action.equals(LOGIN)) {
				if (intent.getStringExtra(LOGIN).equals(LOGIN_SUCCESS)) {
					Tools.showToast("登录成功", context);
					//登陆成功后，跳转到系统主页
					intent=new Intent(LoginActivity.this, CenterActivity.class);
					intent.putExtra("user", user);
					startActivity(intent);
					finish();
				} else if(intent.getStringExtra(LOGIN).equals(LOGIN_FAILURE)) {
					Tools.showToast("请检查是否输入有误", context);
				}
			}
		}

	}

}
