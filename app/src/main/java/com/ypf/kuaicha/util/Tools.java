package com.ypf.kuaicha.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
/**
 * 在执行耗时操作时可以通过ProgressDialog
 * 让用户等待操作结果
 * @author 苑朋飞
 *
 */
public class Tools {
	private static ProgressDialog dialog;
	public static void showToast(String text,Context context){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	
	public static void log(String context,String text){
		Log.i(context, text);
	}
	
	static Context context;
	public static void showDialog (Context context2,String msg) {
		if (dialog==null) {
			dialog=new ProgressDialog(context2);
			dialog.setMessage(msg);
			// 进度条显示时，单击不能取消掉
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		}
	}
	public static void closeDialog() {
		if (dialog != null) {
			dialog.cancel();
			dialog = null;
		}
	}
}
