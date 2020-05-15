package com.saxiao.orderinghelpapp.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.adapter.GvImageAdapter;
import com.saxiao.orderinghelpapp.base.GifSizeFilter;
import com.saxiao.orderinghelpapp.base.Glide4Engine;
import com.saxiao.orderinghelpapp.base.RequestBean;
import com.saxiao.orderinghelpapp.http.ApiFactory;
import com.saxiao.orderinghelpapp.model.ImageBean;
import com.saxiao.orderinghelpapp.model.UserBean;
import com.saxiao.orderinghelpapp.util.Base64Utils;
import com.saxiao.orderinghelpapp.util.DialogUtil;
import com.saxiao.orderinghelpapp.util.SPUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnCheckedListener;
import com.zhihu.matisse.listener.OnSelectedListener;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 商家认证
 */
public class StoreCheckActivity extends AppCompatActivity {

	@BindView(R.id.rc_view)
	RecyclerView rcView;
	@BindView(R.id.et_name) EditText etName;
	@BindView(R.id.et_add) EditText etAddress;
	@BindView(R.id.btn_sure) Button btnSure;
	@BindView(R.id.tv_add)
	TextView tvAdd;

	private Context mContext;
	private GvImageAdapter adapter;
	private List<ImageBean> imageList;
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_check);
		ButterKnife.bind(this);
		mContext = StoreCheckActivity.this;
		imageList = new ArrayList<>();

		btnSure.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("CheckResult") @Override public void onClick(View view) {
				regChekc();
			}
		});

		initGridView();

	}


	private void regChekc(){
		HashMap<String,Object> map = new HashMap<>();
		UserBean userBean = SPUtils.readObject("userBean", UserBean.class);
		userBean.setStoreName(etName.getText().toString());
		userBean.setStoreAddress(etAddress.getText().toString());

		if(imageList.size()>0){
			userBean.setStoreImage(imageList.get(0).getImage());
		}else{
		}
		map.put("userBean",userBean);
		map.put("isSave",false);
		Dialog dialog = DialogUtil.getLoadingDialog(StoreCheckActivity.this, "正在认证...");
		dialog.show();
		ApiFactory.getApi().updateUserInfo(new JsonParser().parse(new Gson().toJson(map)).getAsJsonObject())
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<RequestBean>() {
					@Override public void accept(RequestBean requestBean) throws Exception {
						dialog.dismiss();
						if(requestBean.isSuccess()){
							Toast.makeText(mContext,"认证成功，自动跳转到登录页",Toast.LENGTH_SHORT).show();
							finish();
						}else{
							Toast.makeText(mContext,"认证失败："+requestBean.getMsg(),Toast.LENGTH_SHORT).show();
						}
					}
				}, new Consumer<Throwable>() {
					@Override public void accept(Throwable throwable) throws Exception {
						dialog.dismiss();
						Toast.makeText(mContext,"请求异常："+throwable.getMessage(),Toast.LENGTH_SHORT).show();
					}
				});
	}


	private void initGridView() {
		rcView.setLayoutManager(new GridLayoutManager(mContext,3));
		adapter = new GvImageAdapter(R.layout.gv_item,imageList,mContext,"add");
		rcView.setAdapter(adapter);
		//添加图片
		tvAdd.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				Matisse.from(StoreCheckActivity.this)
						.choose(MimeType.ofAll(), false)
						.countable(true)
						.capture(true)
						.captureStrategy(
								new CaptureStrategy(true, "com.saxiao.electronicproductassessapp.fileprovider","test"))
						.maxSelectable(1)
						.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
						.gridExpectedSize(
								120)
						.restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
						.thumbnailScale(0.85f)
						.imageEngine(new Glide4Engine())
						.setOnSelectedListener(new OnSelectedListener() {
							@Override
							public void onSelected(
									@NonNull List<Uri> uriList, @NonNull List<String> pathList) {
								Log.e("onSelected", "onSelected: pathList=" + pathList);

							}
						})
						.originalEnable(true)
						.maxOriginalSize(10)
						.autoHideToolbarOnSingleTap(true)
						.setOnCheckedListener(new OnCheckedListener() {
							@Override
							public void onCheck(boolean isChecked) {
								Log.e("isChecked", "onCheck: isChecked=" + isChecked);
							}
						})
						.forResult(111);
			}
		});
	}

	@Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 111) {
			List<String> liststr= Matisse.obtainPathResult(data);
			Log.e("OnActivityResult ", String.valueOf(Matisse.obtainOriginalState(data)));
			boolean isok=true;
			if(liststr!=null&&liststr.size()>0){
				for(String path:liststr){
					File resultFile = new File(path);
					if (resultFile.isFile()) {
						ImageBean imageBean = new ImageBean();
						imageBean.setImagePath(path);
						imageBean.setImage(Base64Utils.imageToBase64(path));
						if(imageList.size()<1){
							imageList.add(imageBean);
						}else{
							if(isok){
								//控制只提示一次
								Toast.makeText(mContext,"the maximum of picture :"+1,Toast.LENGTH_SHORT).show();
								isok=false;
							}
						}
					}
				}
				adapter.notifyDataSetChanged();
			}
		}
	}

}
