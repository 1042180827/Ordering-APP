package com.saxiao.orderinghelpapp.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.jaeger.library.StatusBarUtil;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.base.RequestBean;
import com.saxiao.orderinghelpapp.http.ApiFactory;
import com.saxiao.orderinghelpapp.model.PingBean;
import com.saxiao.orderinghelpapp.model.SureOrder;
import com.saxiao.orderinghelpapp.model.UserBean;
import com.saxiao.orderinghelpapp.util.DialogUtil;
import com.saxiao.orderinghelpapp.util.SPUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 评价
 */
public class AddPingActivity extends AppCompatActivity {

	@BindView(R.id.title_txt) TextView tvTitle;
	@BindView(R.id.et_ping) EditText etPing;
	@BindView(R.id.tv_commit) Button btnCommit;

	private Context mContext;
	private UserBean userBean;
	private SureOrder order;
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StatusBarUtil.setTranslucent(this);
		setContentView(R.layout.activity_add_ping);
		ButterKnife.bind(this);
		mContext = AddPingActivity.this;
		order = (SureOrder) getIntent().getSerializableExtra("order");
		//读取userBean类
		userBean = SPUtils.readObject("userBean", UserBean.class);


		//提交
		btnCommit.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				savePing();
			}
		});
	}

	/**
	 * 保存评论
	 */
	@SuppressLint("CheckResult") private void savePing(){
		Dialog dialog = DialogUtil.getLoadingDialog(AddPingActivity.this, "submitting...");
		dialog.show();
		PingBean ping = new PingBean();
		ping.setUserId(userBean.getId());
		ping.setName(userBean.getName());
		Log.e("xxx","name:"+userBean.getName());
		ping.setOrderId(order.getId());
		ping.setStoreId(order.getStoreId());
		ping.setStoreName(order.getStoreName());
		ping.setContent(etPing.getText().toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ping.setDate(sdf.format(new Date()));
		ApiFactory.getApi().savePing(new JsonParser().parse(new Gson().toJson(ping)).getAsJsonObject())
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Consumer<RequestBean>() {
				@Override public void accept(RequestBean requestBean) throws Exception {
					dialog.dismiss();
					if(requestBean.isSuccess()){
						Toast.makeText(mContext,"submit successfully",Toast.LENGTH_SHORT).show();
						finish();
					}
				}
			},throwable -> {
				dialog.dismiss();
				Toast.makeText(mContext,"request exception："+throwable.getMessage(),Toast.LENGTH_SHORT).show();
			});
	}

}
