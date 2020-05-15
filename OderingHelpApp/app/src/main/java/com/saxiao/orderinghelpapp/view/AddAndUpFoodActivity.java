package com.saxiao.orderinghelpapp.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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
import com.saxiao.orderinghelpapp.adapter.GvImageAdapter;
import com.saxiao.orderinghelpapp.base.GifSizeFilter;
import com.saxiao.orderinghelpapp.base.Glide4Engine;
import com.saxiao.orderinghelpapp.base.RequestBean;
import com.saxiao.orderinghelpapp.http.ApiFactory;
import com.saxiao.orderinghelpapp.model.Food;
import com.saxiao.orderinghelpapp.model.ImageBean;
import com.saxiao.orderinghelpapp.model.UpdateFood;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/**
 * 新增和修改食物页面
 */
public class AddAndUpFoodActivity extends AppCompatActivity {

	@BindView(R.id.title_txt) TextView tvTitle;
	@BindView(R.id.tv_add) TextView tvAdd;
	@BindView(R.id.rc_view) RecyclerView rcView;
	@BindView(R.id.et_name) EditText etName;
	@BindView(R.id.et_dan) EditText etDan;
	@BindView(R.id.sp) MaterialSpinner spType;
	@BindView(R.id.sp_status) MaterialSpinner spStatus;
	@BindView(R.id.btn_sure) Button btnAdd;


	private String from;
	private Food food;
	private int foodId;
	private boolean isSave;

	private Context mContext;
	private GvImageAdapter adapter;
	private List<ImageBean> imageList;
	private UserBean userOBean;


	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StatusBarUtil.setTranslucent(this);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.activity_add_and_up_food);
		ButterKnife.bind(this);
		from = getIntent().getStringExtra("from");

		mContext = AddAndUpFoodActivity.this;
		imageList = new ArrayList<>();
		//读取userBean类
		userOBean = SPUtils.readObject("userBean", UserBean.class);
		initGridView();


		switch (from){
			case "add":
				tvTitle.setText("Add new Food");
				break;
			case "up":
				tvTitle.setText("Change Food");
				foodId = getIntent().getIntExtra("id",0);
				getFoodById();

				break;
			default:
				break;
		}


		//修改或新增
		btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				switch (from) {
					case "add":
						save(true);
						break;
					case "up":
						save(false);
						break;
					default:
						break;
				}
			}
		});

		String type = "素菜、荤菜、凉菜、面食、汤类";
		List<String> list = Arrays.asList(type.split("、"));
		spType.setItems(list);

		String type2 = "正常、售罄";
		List<String> list2 = Arrays.asList(type2.split("、"));
		spStatus.setItems(list2);

	}

	@SuppressLint("CheckResult") private void getFoodById(){
		Dialog dialog = DialogUtil.getLoadingDialog(AddAndUpFoodActivity.this, "getting message...");
		dialog.show();
		Log.e("xxxx","foodId:"+foodId+"");
		ApiFactory.getApi().getFoodById(foodId)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Consumer<RequestBean<Food>>() {
				@Override public void accept(RequestBean<Food> requestBean) throws Exception {
					Log.e("xxx",new Gson().toJson(requestBean));
					dialog.dismiss();
					if(requestBean.isSuccess()){
						food = requestBean.getReturnData();
						etName.setText(food.getName());
						etDan.setText(food.getDanjia()+"");
						Log.e("xxx","status:"+food.getStatus());
						if(food.getStatus().equals("正常")){
							spStatus.setSelectedIndex(0);
						}else{
							spStatus.setSelectedIndex(1);
						}
						spStatus.setText(food.getStatus());
						spType.setText(food.getType());
						ImageBean imageBean3 = new ImageBean();
						Bitmap bitmap3 = Base64Utils.decodeToBitmap(food.getImage());
						imageBean3.setBitmap(bitmap3);
						imageBean3.setImage(food.getImage());
						imageList.add(imageBean3);
						adapter.notifyDataSetChanged();
					}else{
						Toast.makeText(mContext,"保存失败："+requestBean.getMsg(),Toast.LENGTH_SHORT).show();
					}
				}
			},throwable -> {
				dialog.dismiss();
				Toast.makeText(mContext,"请求异常："+throwable.getMessage(),Toast.LENGTH_SHORT).show();
			});
	}



	/**
	 * 保存
	 */
	@SuppressLint("CheckResult") private void save(boolean isSave){
		if(isSave){
			food = new Food();
		}
		food.setName(etName.getText().toString());
		food.setDanjia(Double.parseDouble(etDan.getText().toString()));
		food.setStatus(spStatus.getText().toString());
		food.setType(spType.getText().toString());
		food.setStoreId(userOBean.getId()+"");
		food.setStoreName(userOBean.getStoreName());
		if(imageList.size()>0){
			Log.e("xxxx","0.imagePaht"+imageList.get(0).getImagePath());
			food.setImage(imageList.get(0).getImage());
		}else{
			Log.e("xxxx","imageList<0");
		}

		Log.e("xxxx","image:"+food.getImage());

		Dialog dialog = DialogUtil.getLoadingDialog(AddAndUpFoodActivity.this, "saving...");
		dialog.show();
		HashMap<String,Object> map = new HashMap<>();
		map.put("food",food);
		map.put("isSave",isSave);
		ApiFactory.getApi().addFood(new JsonParser().parse(new Gson().toJson(map)).getAsJsonObject())
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Consumer<RequestBean>() {
				@Override public void accept(RequestBean requestBean) throws Exception {
					dialog.dismiss();
					if(requestBean.isSuccess()){
						Toast.makeText(mContext,"saved successfully",Toast.LENGTH_SHORT).show();
						EventBus.getDefault().post(new UpdateFood());
						finish();
					}else{
						Toast.makeText(mContext,"saved failed："+requestBean.getMsg(),Toast.LENGTH_SHORT).show();
					}
				}
			},throwable -> {
				dialog.dismiss();
				Toast.makeText(mContext,"request exception："+throwable.getMessage(),Toast.LENGTH_SHORT).show();
			});
	}



	private void initGridView() {
		rcView.setLayoutManager(new GridLayoutManager(mContext,3));
		adapter = new GvImageAdapter(R.layout.gv_item,imageList,mContext,"add");
		rcView.setAdapter(adapter);
		//添加图片
		tvAdd.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				Matisse.from(AddAndUpFoodActivity.this)
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

		//delete
		adapter.setOnItemChildClickListener((adapter, view, position) -> {
			if(view.getId() == R.id.delimg){
				imageList.remove(position);
				adapter.notifyDataSetChanged();
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
								Toast.makeText(mContext,"the maximum of picture"+1,Toast.LENGTH_SHORT).show();
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
