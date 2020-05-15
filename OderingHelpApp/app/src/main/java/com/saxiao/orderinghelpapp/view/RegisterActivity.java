package com.saxiao.orderinghelpapp.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.jaeger.library.StatusBarUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.base.RequestBean;
import com.saxiao.orderinghelpapp.http.ApiFactory;
import com.saxiao.orderinghelpapp.model.UserBean;
import com.saxiao.orderinghelpapp.util.DialogUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.Arrays;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

	@BindView(R.id.sp_type)
	MaterialSpinner spType;
	@BindView(R.id.reg_phone) EditText etAccount;
	@BindView(R.id.reg_name) EditText etName;
	@BindView(R.id.reg_password) EditText etPwd;
	@BindView(R.id.btn_register) TextView tvReg;


	private Context mContext;
	private int type ;
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StatusBarUtil.setTranslucent(this);
		setContentView(R.layout.activity_register);
		ButterKnife.bind(this);
		mContext = RegisterActivity.this;
		//注册
		tvReg.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				doRegister();
			}
		});

		String[] a = "买家、商家".split("、");
		spType.setItems(Arrays.asList(a));
		spType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
			@Override public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
				type = position;
			}
		});

	}

	/**
	 * 注册
	 */
	@SuppressLint("CheckResult") private void doRegister() {
		if (TextUtils.isEmpty(etAccount.getText().toString()) || TextUtils.isEmpty(etPwd.getText().toString())) {
			Toast.makeText(RegisterActivity.this, "You have unfilled items", Toast.LENGTH_SHORT).show();
		}else{
			Dialog dialog = DialogUtil.getLoadingDialog(RegisterActivity.this, "registering...");
			dialog.show();
			HashMap<String,Object> map = new HashMap<>();
			UserBean userBean = new UserBean();
			userBean.setPhoneNumber(etAccount.getText().toString());
			userBean.setName(etName.getText().toString());
			userBean.setPwd(etPwd.getText().toString());
			if(type==0){
				userBean.setType("买家");
				userBean.setMoney("300");
			}else{
				userBean.setType("商家");
				userBean.setMoney("0");
			}
			map.put("userBean",userBean);
			map.put("isSave",true);
			ApiFactory.getApi().updateUserInfo(new JsonParser().parse(new Gson().toJson(map)).getAsJsonObject())
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<RequestBean>() {
					@Override public void accept(RequestBean requestBean) throws Exception {
						dialog.dismiss();
						if(requestBean.isSuccess()){
							Toast.makeText(mContext,"success,jump to login",Toast.LENGTH_SHORT).show();
							finish();
						}else{
							Toast.makeText(mContext,"failed："+requestBean.getMsg(),Toast.LENGTH_SHORT).show();
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
