package com.saxiao.orderinghelpapp.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.jaeger.library.StatusBarUtil;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.base.RequestBean;
import com.saxiao.orderinghelpapp.http.ApiFactory;
import com.saxiao.orderinghelpapp.model.SureOrder;
import com.saxiao.orderinghelpapp.model.UserBean;
import com.saxiao.orderinghelpapp.model.Caipin;
import com.saxiao.orderinghelpapp.util.DialogUtil;
import com.saxiao.orderinghelpapp.util.SPUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * 发布订单
 */
public class PublishOrderActivity extends AppCompatActivity {

	@BindView(R.id.et_name) EditText etName;
	@BindView(R.id.et_count) EditText etCount;
	@BindView(R.id.et_bz) EditText etBz;
	@BindView(R.id.tv_commit) Button btnCommit;

	private Context mContext;
	private UserBean userBean;
	private SureOrder order;
	private Caipin cai;
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StatusBarUtil.setTranslucent(this);
		setContentView(R.layout.activity_put_order);
		ButterKnife.bind(this);
		mContext = PublishOrderActivity.this;
		order = (SureOrder) getIntent().getSerializableExtra("order");
		cai = (Caipin) getIntent().getSerializableExtra("cai");
		//读取userBean类
		userBean = SPUtils.readObject("userBean", UserBean.class);

		//提交
		btnCommit.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				save();
			}
		});
	}

	/**
	 * 发布订单
	 */
	@SuppressLint("CheckResult") private void save(){
		Dialog dialog = DialogUtil.getLoadingDialog(PublishOrderActivity.this, "ordering...");
		dialog.show();
		SureOrder sureOrder = new SureOrder();
		sureOrder.setCaipin(etName.getText().toString()+"  "+etCount.getText().toString()+"份");
		sureOrder.setBeizhu(etBz.getText().toString());
		sureOrder.setUserName(userBean.getName());
		sureOrder.setUserId(userBean.getId());
		//获取当天日期
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		sureOrder.setDate(format.format(new Date()));
		HashMap<String,Object> map = new HashMap<>();
		map.put("sure",sureOrder);
		map.put("isSave",true);
		ApiFactory.getApi().addToSure(new JsonParser().parse(new Gson().toJson(map)).getAsJsonObject())
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Consumer<RequestBean>() {
				@Override public void accept(RequestBean requestBean) throws Exception {
					dialog.dismiss();
					if(requestBean.isSuccess()){
						Toast.makeText(mContext,"success",Toast.LENGTH_SHORT).show();
						finish();
					}
				}
			},throwable -> {dialog.dismiss();});
		Caipin caipin = new Caipin();
		caipin.setName(etName.getText().toString());
		caipin.setUserId(userBean.getId());
		//获取当天日期
		HashMap<String,Object> map1 = new HashMap<>();
		map1.put("caipin",caipin);
		map1.put("isSave",true);
		ApiFactory.getApi().addrec(new JsonParser().parse(new Gson().toJson(map1)).getAsJsonObject())
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<RequestBean>() {
					@Override public void accept(RequestBean requestBean) throws Exception {
						dialog.dismiss();
						if(requestBean.isSuccess()){
							Toast.makeText(mContext,"success",Toast.LENGTH_SHORT).show();
							finish();
						}
					}
				},throwable -> {dialog.dismiss();});
	}


}
