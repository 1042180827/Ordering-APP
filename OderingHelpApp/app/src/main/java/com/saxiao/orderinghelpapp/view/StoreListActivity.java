package com.saxiao.orderinghelpapp.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.adapter.StoreListAdapter;
import com.saxiao.orderinghelpapp.http.ApiFactory;
import com.saxiao.orderinghelpapp.model.UserBean;
import com.saxiao.orderinghelpapp.util.DialogUtil;
import com.saxiao.orderinghelpapp.util.SPUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/**
 * 商家（店铺）列表
 */
public class StoreListActivity extends AppCompatActivity {

	@BindView(R.id.swipe_ref) SwipeRefreshLayout mSwipeRefresh;
	@BindView(R.id.recycler) RecyclerView rcView;
    @BindView(R.id.tv_add) TextView tvAdd;
    @BindView(R.id.tv1) TextView tvTitle;
	private Context mContext;
	private List<UserBean> list;
	private int userId;
	private UserBean userBean;
	private StoreListAdapter mAdapter;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StatusBarUtil.setTranslucent(this);
		setContentView(R.layout.activity_my_pl);
		ButterKnife.bind(this);
		mContext = StoreListActivity.this;
		tvAdd.setVisibility(View.GONE);
		tvTitle.setText("all stores");
		list=new ArrayList<>();
		mAdapter=new StoreListAdapter(R.layout.store_item,list,mContext);
		rcView.setLayoutManager(new LinearLayoutManager(mContext));
		rcView.setAdapter(mAdapter);

		//下拉刷新
		mSwipeRefresh.setColorSchemeResources(
			R.color.blue,
			R.color.green,
			R.color.red,
			R.color.yellow
		);
		//刷新
		mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override public void onRefresh() {
				getData();
				mAdapter.setEnableLoadMore(true);
			}
		});

		userBean = SPUtils.readObject("userBean",UserBean.class);
		userId = userBean.getId();
		getData();

		//进入商家页面
		mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				Intent intent = new Intent(StoreListActivity.this,FoodListActivity.class);
				intent.putExtra("storeId",list.get(position).getId());
				intent.putExtra("storeName",list.get(position).getStoreName());
				startActivity(intent);
			}
		});
	}

	@SuppressLint("CheckResult") private void getData(){
		Dialog dialog = DialogUtil.getLoadingDialog(StoreListActivity.this, "getting list...");
		dialog.show();
		ApiFactory.getApi().getStoreList()
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(requestBean -> {
				dialog.dismiss();
				//停止刷新
				if (mSwipeRefresh.isRefreshing()) {
					mSwipeRefresh.setRefreshing(false);
				}
				if (requestBean.isSuccess()) {
					//请求成功
					if (requestBean.getReturnData().size() > 0) {
						Log.e("shs",new Gson().toJson(requestBean.getReturnData()));
						//有数据
						list.clear();
						list.addAll(requestBean.getReturnData());
						mAdapter.notifyDataSetChanged();
					} else {
						//没有数据，设置空布局
						View emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_empty, (ViewGroup) rcView.getParent(), false);
						mAdapter.setNewData(null);
						mAdapter.setEmptyView(emptyView);
						mAdapter.notifyDataSetChanged();
					}
				} else {//请求失败
					Toast.makeText(mContext, "request failed：" + requestBean.getMsg(), Toast.LENGTH_SHORT).show();
				}
			}, throwable -> {
				dialog.dismiss();
				//停止刷新
				if (mSwipeRefresh.isRefreshing()) {
					mSwipeRefresh.setRefreshing(false);
				}
				Toast.makeText(mContext, "exception：" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
			});
	}
}
