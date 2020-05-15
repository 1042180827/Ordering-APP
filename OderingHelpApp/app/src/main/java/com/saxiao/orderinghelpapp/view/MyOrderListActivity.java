package com.saxiao.orderinghelpapp.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.adapter.MyOrderListAdapter;
import com.saxiao.orderinghelpapp.base.RequestListBean;
import com.saxiao.orderinghelpapp.http.ApiFactory;
import com.saxiao.orderinghelpapp.model.SureOrder;
import com.saxiao.orderinghelpapp.model.UserBean;
import com.saxiao.orderinghelpapp.util.DialogUtil;
import com.saxiao.orderinghelpapp.util.SPUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的订单
 */
public class MyOrderListActivity extends AppCompatActivity {


	@BindView(R.id.tv_add) TextView tvAdd;
	@BindView(R.id.recycler) RecyclerView rcView;
	@BindView(R.id.tv1) TextView tvTitle;

	private List<SureOrder> list;
	private MyOrderListAdapter mAdapter;
	private Context mContext;
	private UserBean userOBean;
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StatusBarUtil.setTranslucent(this);
		setContentView(R.layout.activity_my_pl);
		ButterKnife.bind(this);
		tvAdd.setVisibility(View.GONE);
		mContext = MyOrderListActivity.this;
		tvTitle.setText("My order");
		list = new ArrayList<>();
		//读取userBean类
		userOBean = SPUtils.readObject("userBean", UserBean.class);


		mAdapter=new MyOrderListAdapter(R.layout.paylist_item,list,mContext);
		rcView.setLayoutManager(new LinearLayoutManager(mContext));
		rcView.setAdapter(mAdapter);

		mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
			@Override public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
				if(view.getId() == R.id.tv_ping){
					//评价
					Intent intent = new Intent(MyOrderListActivity.this,AddPingActivity.class);
					intent.putExtra("order",list.get(position));
					startActivity(intent);
				}
			}
		});

		getOrderList();
	}

	/**
	 * 获取服务器上的我的订单
	 */
	@SuppressLint("CheckResult") private void getOrderList(){
		Dialog dialog = DialogUtil.getLoadingDialog(MyOrderListActivity.this, "getting list...");
		dialog.show();
		/**
		 * 获取我的订单
		 */
		ApiFactory.getApi().getMyOrder(userOBean.getId())
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Consumer<RequestListBean<SureOrder>>() {
				@Override public void accept(RequestListBean<SureOrder> bean) {
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


	@Override
	protected void onResume() {
		super.onResume();
		getOrderList();
	}
}
