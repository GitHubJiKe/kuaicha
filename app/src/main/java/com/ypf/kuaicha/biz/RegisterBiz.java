package com.ypf.kuaicha.biz;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.ypf.kuaicha.entity.User;
import com.ypf.kuaicha.util.Const;

public class RegisterBiz implements Const {
	private static Intent intent;

	public static void register(Context context,User user){
		DbUtils db=DbUtils.create(context);
		try {
			User findFirst = db.findFirst(Selector.from(User.class).where("name", "=", user.getName()));
			Log.i("findFirst", findFirst+"");
			if (null!=findFirst) {
				intent=new Intent(REGISTER);
				intent.putExtra(REGISTER, REGIZTER_FAILURE);
				context.sendBroadcast(intent);
			}else {
				db.save(user);
				intent=new Intent(REGISTER);
				intent.putExtra(REGISTER, REGISTER_SUCCESS);
				context.sendBroadcast(intent);
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
}
