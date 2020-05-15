package com.saxiao.orderinghelpapp.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.base.RequestBean;
import com.saxiao.orderinghelpapp.http.ApiFactory;
import com.saxiao.orderinghelpapp.model.UserBean;
import com.saxiao.orderinghelpapp.util.DialogUtil;
import com.saxiao.orderinghelpapp.util.SPUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;

public class UserInfoActivity extends AppCompatActivity {
	//@BindView(R.id.title_left)
	//ImageView title_left;
	//@BindView(R.id.title_txt)
	//TextView title_txt;
	@BindView(R.id.title_right)
	TextView tvRight;
	@BindView(R.id.info_name)
	EditText etName;
	@BindView(R.id.info_phone)
	EditText etPhone;
	@BindView(R.id.info_pwd)
	EditText etPwd;
	@BindView(R.id.ll_name) LinearLayout llname;
	@BindView(R.id.ll_add) LinearLayout llAdd;
	@BindView(R.id.info_store_name) EditText tvStoreName;
	@BindView(R.id.info_address) EditText tvAddress;
	@BindView(R.id.btn_sure)
	Button btnSure;

	private UserBean userOBean;
	private SharedPreferences cookieStore;
	private Unbinder unbinder;
	private Context mContext;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		ButterKnife.bind(this);
		initView();
	}


	private void initView() {
		mContext = UserInfoActivity.this;
		if(getIntent().getSerializableExtra("user")!=null){
			userOBean = (UserBean) getIntent().getSerializableExtra("user");
			tvRight.setVisibility(View.GONE);
		}else{
			//读取userBean类
			userOBean = SPUtils.readObject("userBean",UserBean.class);
			tvRight.setVisibility(View.VISIBLE);
		}
		if(userOBean.getType().equals("商家")){
			llAdd.setVisibility(View.VISIBLE);
			llname.setVisibility(View.VISIBLE);
		}else{
			llAdd.setVisibility(View.GONE);
			llname.setVisibility(View.GONE);
		}
		tvRight.setText("My balance："+userOBean.getMoney()+"元");
		etName.setText(userOBean.getName());
		etPwd.setText(userOBean.getPwd());
		etPhone.setText(userOBean.getPhoneNumber());
		tvStoreName.setText(userOBean.getStoreName());
		tvAddress.setText(userOBean.getStoreAddress());

		//确定修改
		btnSure.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("CheckResult") @Override public void onClick(View view) {
				HashMap<String,Object> map = new HashMap<>();
				userOBean.setName(etName.getText().toString());
				userOBean.setPwd(etPwd.getText().toString());
				userOBean.setStoreAddress(tvAddress.getText().toString());
				userOBean.setStoreName(tvStoreName.getText().toString());
				map.put("userBean",userOBean);
				map.put("isSave",false);
				Dialog dialog = DialogUtil.getLoadingDialog(UserInfoActivity.this, "changing...");
				dialog.show();
				ApiFactory.getApi().updateUserInfo(new JsonParser().parse(new Gson().toJson(map)).getAsJsonObject())
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(new Consumer<RequestBean>() {
						@Override public void accept(RequestBean requestBean) throws Exception {
							dialog.dismiss();
							if(requestBean.isSuccess()){
								Toast.makeText(mContext,"successfully",Toast.LENGTH_SHORT).show();
								//保存修改后的userBean
								SPUtils.saveObject("userBean",userOBean);
								finish();
							}else{
								Toast.makeText(mContext,"failed："+requestBean.getMsg(),Toast.LENGTH_SHORT).show();
							}
						}
					}, new Consumer<Throwable>() {
						@Override public void accept(Throwable throwable) throws Exception {
							dialog.dismiss();
							Toast.makeText(mContext,"exception:"+throwable.getMessage(),Toast.LENGTH_SHORT).show();
						}
					});
			}
		});

	}
}
