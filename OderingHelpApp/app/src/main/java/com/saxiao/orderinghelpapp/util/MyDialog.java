package com.saxiao.orderinghelpapp.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.saxiao.orderinghelpapp.R;

public class MyDialog {

	private static Dialog mDialog;
	public static void showDialog(Context context,String info){
		//1.创建一个Dialog对象，如果是AlertDialog对象的话，弹出的自定义布局四周会有一些阴影，效果不好
		mDialog = new Dialog(context);
		//去除标题栏
		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//2.填充布局
		LayoutInflater inflater = LayoutInflater.from(context);
		View dialogView     = inflater.inflate(R.layout.detail_dialog, null);
		//将自定义布局设置进去
		mDialog.setContentView(dialogView);
		//3.设置指定的宽高,如果不设置的话，弹出的对话框可能不会显示全整个布局，当然在布局中写死宽高也可以
		WindowManager.LayoutParams lp     = new WindowManager.LayoutParams();
		Window                     window = mDialog.getWindow();
		lp.copyFrom(window.getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = 500;
		//注意要在Dialog show之后，再将宽高属性设置进去，才有效果
		mDialog.show();
		window.setAttributes(lp);
		//设置点击其它地方让消失弹窗
		mDialog.setCancelable(true);

		TextView tv = dialogView.findViewById(R.id.tv_detail);
		tv.setText(info);
	}
}
