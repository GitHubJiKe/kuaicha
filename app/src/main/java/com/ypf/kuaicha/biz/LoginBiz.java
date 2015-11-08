package com.ypf.kuaicha.biz;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.ypf.kuaicha.entity.User;
import com.ypf.kuaicha.util.Const;

import android.content.Context;
import android.content.Intent;

public class LoginBiz implements Const {
	private static Intent intent;

	public static void login(Context context,User user){
		DbUtils db=DbUtils.create(context);
		try {
			User findFirst = db.findFirst(Selector.from(User.class).where("name", "=", user.getName()).and("password","=",user.getPassword()));
			
			if (null!=findFirst) {
				intent=new Intent(LOGIN);
				intent.putExtra(LOGIN, LOGIN_SUCCESS);
				context.sendBroadcast(intent);
			}else {
				intent=new Intent(LOGIN);
				intent.putExtra(LOGIN, LOGIN_FAILURE);
				context.sendBroadcast(intent);
			}
			
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
}
