package com.saxiao.orderinghelpapp.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.jaeger.library.StatusBarUtil;
import com.saxiao.orderinghelpapp.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * 启动页
 */
public class SplashActivity extends AppCompatActivity {

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StatusBarUtil.setTranslucent(this);
		setContentView(R.layout.activity_splash);
		//Android 6.0 以上动态获取权限
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getPermission(this);
		} else {
			startActivity(new Intent(SplashActivity.this, LoginActivity.class));
			finish();
		}
	}

	@SuppressLint("CheckResult") private void getPermission(SplashActivity it) {
		new RxPermissions(it)
			.request(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,
				Manifest.permission.WRITE_EXTERNAL_STORAGE)
			.subscribe(grant -> {
				if (grant) {
					startActivity(new Intent(SplashActivity.this, LoginActivity.class));
					finish();
				} else {
					Toast.makeText(SplashActivity.this,"应用必须获取相关权限才能运行", Toast.LENGTH_SHORT).show();
					finish();
				}
			});

	}
}
