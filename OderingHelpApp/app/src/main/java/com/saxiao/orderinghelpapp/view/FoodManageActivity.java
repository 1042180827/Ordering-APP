package com.saxiao.orderinghelpapp.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.adapter.FoodListAdapter;
import com.saxiao.orderinghelpapp.base.RequestBean;
import com.saxiao.orderinghelpapp.base.RequestListBean;
import com.saxiao.orderinghelpapp.http.ApiFactory;
import com.saxiao.orderinghelpapp.model.Food;
import com.saxiao.orderinghelpapp.model.UpdateFood;
import com.saxiao.orderinghelpapp.model.UserBean;
import com.saxiao.orderinghelpapp.util.DialogUtil;
import com.saxiao.orderinghelpapp.util.SPUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 食物管理
 */
public class FoodManageActivity extends AppCompatActivity {

	@BindView(R.id.recycler) RecyclerView rcView;
	@BindView(R.id.title_right) TextView tvAdd;
	@BindView(R.id.title_txt) TextView tvTitle;
	@BindView(R.id.et_see) EditText etSee;
	@BindView(R.id.btn_search) Button btnSea;

	private List<Food> list;
	private FoodListAdapter mAdapter;
	private Context mContext;
	private UserBean userBean;
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StatusBarUtil.setTranslucent(this);
		setContentView(R.layout.activity_plant_manage);
		ButterKnife.bind(this);
		mContext = FoodManageActivity.this;
		list = new ArrayList<>();
		mAdapter=new FoodListAdapter(R.layout.foodlist_item,list,mContext,"manage");
		rcView.setLayoutManager(new LinearLayoutManager(mContext));
		rcView.setAdapter(mAdapter);

		EventBus.getDefault().register(this);
		//get userBean
		userBean = SPUtils.readObject("userBean", UserBean.class);
		//add new food
		tvAdd.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				//jump to add page
				Intent intent = new Intent(FoodManageActivity.this,AddAndUpFoodActivity.class);
				intent.putExtra("from","add");
				startActivity(intent);
			}
		});

		//search
		btnSea.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				search();
			}
		});

		//modify
		mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
			@Override public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
				if(view.getId() == R.id.btn_up){
					Intent intent = new Intent(FoodManageActivity.this,AddAndUpFoodActivity.class);
					intent.putExtra("from","up");
					Log.e("xxxx","id"+list.get(position).getId());
					intent.putExtra("id", list.get(position).getId());
					startActivity(intent);
				}
			}
		});

		//delete（hold down）
		mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
			@Override public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
				AlertDialog.Builder builder = new AlertDialog.Builder(FoodManageActivity.this);
				builder.setTitle("prompt");
				builder.setMessage("sure to delete?");
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						//delete
						delete(position);
					}
				});

				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.create().show();
				return true;
			}
		});

		getPlantList();

	}

	/**
	 * 获取店内食物
	 */
	@SuppressLint("CheckResult") private void getPlantList(){
		Dialog dialog = DialogUtil.getLoadingDialog(FoodManageActivity.this, "getting list...");
		dialog.show();
		ApiFactory.getApi().getFoodByStore2(userBean.getId())
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Consumer<RequestListBean<Food>>() {
				@Override public void accept(RequestListBean<Food> bean) {
					Log.e("xxx",new Gson().toJson(bean));
					dialog.dismiss();
					list.clear();

					list.addAll(bean.getReturnData());
					if(list.size()==0){
						//没有数据，设置空布局
						View emptyView = LayoutInflater.from(mContext)
							.inflate(R.layout.layout_empty, (ViewGroup) rcView.getParent(), false);
						mAdapter.setEmptyView(emptyView);
					}
					mAdapter.notifyDataSetChanged();
				}
			}, new Consumer<Throwable>() {
				@Override public void accept(Throwable throwable) {
					dialog.dismiss();
					Toast.makeText(mContext,"request exception："+throwable.getMessage(),Toast.LENGTH_SHORT).show();
				}
			});

	}

	/**
	 * 查询信息
	 */
	@SuppressLint("CheckResult") private void search(){
		ApiFactory.getApi().searchFood(userBean.getId(),etSee.getText().toString())
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Consumer<RequestListBean<Food>>() {
				@Override public void accept(RequestListBean<Food> bean) throws Exception {
					if(bean.isSuccess()){
						list.clear();
						list.addAll(bean.getReturnData());
						if(list.size()==0){
							//没有数据，设置空布局
							View emptyView = LayoutInflater.from(mContext)
								.inflate(R.layout.layout_empty, (ViewGroup) rcView.getParent(), false);
							mAdapter.setEmptyView(emptyView);
						}
						mAdapter.notifyDataSetChanged();
					}else{
						Toast.makeText(mContext,"failed："+bean.getMsg(),Toast.LENGTH_SHORT).show();
					}
				}
			}, new Consumer<Throwable>() {
				@Override public void accept(Throwable throwable) throws Exception {
					Toast.makeText(mContext,"request exception："+throwable.getMessage(),Toast.LENGTH_SHORT).show();
				}
			});
	}

	/**
	 * 删除
	 */
	@SuppressLint("CheckResult") private void delete(int position){
		ApiFactory.getApi().deleteFood(list.get(position).getId())
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Consumer<RequestBean>() {
				@Override public void accept(RequestBean requestBean) throws Exception {
					if(requestBean.isSuccess()){
						Toast.makeText(mContext,"delete successfully",Toast.LENGTH_SHORT).show();
						//重新获取列表数据
						getPlantList();
					}else{
						Toast.makeText(mContext,"failed："+requestBean.getMsg(),Toast.LENGTH_SHORT).show();
					}
				}
			}, new Consumer<Throwable>() {
				@Override public void accept(Throwable throwable) throws Exception {
					Toast.makeText(mContext,"request exception："+throwable.getMessage(),Toast.LENGTH_SHORT).show();
				}
			});
	}

	@Override protected void onResume() {
		super.onResume();

	}

	/**
	 *
	 */
	@Subscribe(threadMode = ThreadMode.MAIN)
	@SuppressWarnings("unused")
	public void updateTaskData(UpdateFood updateData) {
		getPlantList();
	}
}
