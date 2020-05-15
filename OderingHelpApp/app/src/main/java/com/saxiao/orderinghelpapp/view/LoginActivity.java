package com.saxiao.orderinghelpapp.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jaeger.library.StatusBarUtil;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.base.RequestBean;
import com.saxiao.orderinghelpapp.http.ApiFactory;
import com.saxiao.orderinghelpapp.model.UserBean;
import com.saxiao.orderinghelpapp.util.DialogUtil;
import com.saxiao.orderinghelpapp.util.SPUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

	@BindView(R.id.login_account)
	EditText etAccount;
	@BindView(R.id.login_password)
	EditText etPwd;
	@BindView(R.id.btn_login)
	TextView tvLogin;
	@BindView(R.id.tv_register)
	TextView tvReg;

	private Context mContext;
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StatusBarUtil.setTranslucent(this);
		setContentView(R.layout.activity_login);
		ButterKnife.bind(this);
		mContext = LoginActivity.this;
		//登录
		tvLogin.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				LoginActivity.this.doLogin();
			}
		});

		//注册
		tvReg.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
			}
		});

	}

	@SuppressLint("CheckResult") private void doLogin(){
		//获取用户账号和密码
		String accout = etAccount.getText().toString();
		String pwd = etPwd.getText().toString();
		if(TextUtils.isEmpty(accout)||TextUtils.isEmpty(pwd)){
			Toast.makeText(LoginActivity.this,"Username and password cannot be empty",Toast.LENGTH_SHORT).show();
		}else{
			Dialog dialog = DialogUtil.getLoadingDialog(LoginActivity.this, "login...");
			dialog.show();
			//请求接口，输入的信息和服务器上注册的信息做对比，相同就允许登录，否则就提示。
			ApiFactory.getApi().login(etAccount.getText().toString(),etPwd.getText().toString())
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<RequestBean<UserBean>>() {
					@Override public void accept(RequestBean<UserBean> requestBean) throws Exception {
						dialog.dismiss();
						if(requestBean.isSuccess()){
							UserBean userBean = requestBean.getReturnData();
							//保存bean
							SPUtils.saveObject("userBean",userBean);

							if(userBean.getType().equals("商家")){
								if(TextUtils.isEmpty(userBean.getStoreName())){
									//如果未认证则去认证
									startActivity(new Intent(mContext,StoreCheckActivity.class));
								}else{
									startActivity(new Intent(mContext,Main2Activity.class));
								}
							}else{
								startActivity(new Intent(mContext,Main2Activity.class));
							}

						}else{
							Toast.makeText(mContext,"request exception："+requestBean.getMsg(),Toast.LENGTH_SHORT).show();
						}
					}
				}, new Consumer<Throwable>() {
					@Override public void accept(Throwable throwable) throws Exception {
						dialog.dismiss();
						Toast.makeText(mContext,"request exception："+throwable.getMessage(),Toast.LENGTH_SHORT).show();
					}
				});
		}
	}
}
