package com.saxiao.orderinghelpapp.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jaeger.library.StatusBarUtil;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.adapter.DetailPlListAdapter;
import com.saxiao.orderinghelpapp.http.ApiFactory;
import com.saxiao.orderinghelpapp.model.PingBean;
import com.saxiao.orderinghelpapp.model.UserBean;
import com.saxiao.orderinghelpapp.util.DialogUtil;
import com.saxiao.orderinghelpapp.util.SPUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/**
 * 店内评价列表
 */
public class StorePjListActivity extends AppCompatActivity {

	@BindView(R.id.swipe_ref) SwipeRefreshLayout mSwipeRefresh;
	@BindView(R.id.recycler) RecyclerView rcView;
    @BindView(R.id.tv_add) TextView tvAdd;
	@BindView(R.id.tv1) TextView tvTitle;
	private Context mContext;
	private List<PingBean> list;
	private int userId;
	private UserBean userBean;

	private DetailPlListAdapter mAdapter;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StatusBarUtil.setTranslucent(this);
		setContentView(R.layout.activity_my_pl);
		ButterKnife.bind(this);
		tvTitle.setText(getIntent().getStringExtra("storeName"));
		tvAdd.setVisibility(View.GONE);
		mContext = StorePjListActivity.this;
		list=new ArrayList<>();
		userBean = SPUtils.readObject("userBean",UserBean.class);
		//评论
		mAdapter=new DetailPlListAdapter(R.layout.detail_pl_item,list,mContext);
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
				getPlData();
				mAdapter.setEnableLoadMore(true);
			}
		});

		getPlData();
	}

	/**
	 * 获取评论的数据
	 */
	@SuppressLint("CheckResult")
	private void getPlData() {
		Dialog dialog = DialogUtil.getLoadingDialog(StorePjListActivity.this, "getting list...");
		dialog.show();
		ApiFactory.getApi().getPing(userBean.getId())
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(requestBean -> {
				dialog.dismiss();
				if (requestBean.isSuccess()) {
					//请求成功
					if (requestBean.getReturnData().size() > 0) {
						//有数据
						list.clear();
						list.addAll(requestBean.getReturnData());
						mAdapter.notifyDataSetChanged();
					} else {
						//没有数据，设置空布局
						View emptyView = LayoutInflater.from(mContext).inflate(R.layout.pl_empty, (ViewGroup) rcView.getParent(), false);
						mAdapter.setNewData(null);
						mAdapter.setEmptyView(emptyView);
						mAdapter.notifyDataSetChanged();
					}
				} else {//请求失败
					Toast.makeText(mContext, "failed：" + requestBean.getMsg(), Toast.LENGTH_SHORT).show();
				}
			}, throwable -> {
				dialog.dismiss();
				Toast.makeText(mContext, "exception：" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
			});
	}

}
