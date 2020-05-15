package com.saxiao.orderinghelpapp.util;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.saxiao.orderinghelpapp.R;

public class DialogUtil {
	public static Dialog getLoadingDialog(Activity activity, String msg) {
		final Dialog dialog = new Dialog(activity, R.style.loadingDialog);
		dialog.setContentView(R.layout.loading_dialog);
		dialog.setCancelable(Boolean.FALSE);
		TextView msgText = (TextView) dialog.findViewById(R.id.loading_msg);
		if (msg != null && !("").equals(msg))
			msgText.setText(msg);
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		int screenW = getScreenWidth(activity);
		lp.width = (int) (0.6 * screenW);
		return dialog;
	}

	public static int getScreenWidth(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

}
