package com.saxiao.orderinghelpapp.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.google.gson.JsonParser;
import com.jaeger.library.StatusBarUtil;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.adapter.MyOrderListAdapter;
import com.saxiao.orderinghelpapp.base.RequestBean;
import com.saxiao.orderinghelpapp.base.RequestListBean;
import com.saxiao.orderinghelpapp.http.ApiFactory;
import com.saxiao.orderinghelpapp.model.SaleBean;
import com.saxiao.orderinghelpapp.model.SureOrder;
import com.saxiao.orderinghelpapp.model.UserBean;
import com.saxiao.orderinghelpapp.util.DialogUtil;
import com.saxiao.orderinghelpapp.util.SPUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *商家--店内订单
 */
public class OrderListActivity extends AppCompatActivity {
	@BindView(R.id.swipe_ref) SwipeRefreshLayout mSwipeRefresh;
	@BindView(R.id.recycler) RecyclerView rcView;
	@BindView(R.id.tv1) TextView tvTitle;
	@BindView(R.id.tv_add) TextView tvAdd;

	private List<SureOrder> list;
	private MyOrderListAdapter mAdapter;
	private Context mContext;
	private UserBean userOBean;
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StatusBarUtil.setTranslucent(this);
		setContentView(R.layout.activity_my_pl);
		ButterKnife.bind(this);
		tvTitle.setText("can take");
		tvAdd.setVisibility(View.GONE);
		mContext = OrderListActivity.this;
		list = new ArrayList<>();
		//读取userBean类
		userOBean = SPUtils.readObject("userBean", UserBean.class);

		mAdapter=new MyOrderListAdapter(R.layout.paylist_item,list,mContext);
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
				getOrderList();
				mAdapter.setEnableLoadMore(true);
			}
		});

		mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
					AlertDialog.Builder builder = new AlertDialog.Builder(OrderListActivity.this);
					builder.setTitle("prompt");
					builder.setMessage("sure to take？");
					builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Dialog d = DialogUtil.getLoadingDialog(OrderListActivity.this, "taking...");
							d.show();
							//加入商家的信息
							list.get(position).setStoreId(userOBean.getId());
							list.get(position).setStoreName(userOBean.getName());
							list.get(position).setStatus(1);//状态变为已接单
							updateSaleCount(position,d);

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

		getOrderList();
	}

	/**
	 * 获取服务器上的未接订单
	 */
	@SuppressLint("CheckResult") private void getOrderList(){
		Dialog dialog = DialogUtil.getLoadingDialog(OrderListActivity.this, "getting list...");
		dialog.show();

		ApiFactory.getApi().getStoreOrder(userOBean.getId())
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Consumer<RequestListBean<SureOrder>>() {
				@Override public void accept(RequestListBean<SureOrder> bean) {
					if(mSwipeRefresh.isRefreshing()){
						mSwipeRefresh.setRefreshing(false);
					}
					dialog.dismiss();
					list.clear();
					for(int i=0;i<bean.getReturnData().size();i++){
						if(bean.getReturnData().get(i).getStatus() == 0){
							list.add(bean.getReturnData().get(i));
						}
					}
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
					if(mSwipeRefresh.isRefreshing()){
						mSwipeRefresh.setRefreshing(false);
					}
					Toast.makeText(mContext,"request exception："+throwable.getMessage(),Toast.LENGTH_SHORT).show();
				}
			});
	}

	/**
	 * 更改食物的销售情况
	 */
	@SuppressLint("CheckResult") private void updateSaleCount(int position,Dialog d){
		List<SaleBean> saleList = new ArrayList<>();
		List<String> sList = Arrays.asList(list.get(position).getCaipin().split(";"));
		for(int i=0;i<sList.size();i++){
			Log.e("xxxx","长度："+sList.get(i).length());
			String name = sList.get(i).substring(0,sList.get(i).indexOf(" "));
			Log.e("xxxx","name:"+name);
			String fen = sList.get(i).substring(sList.get(i).indexOf(" ")+2,sList.get(i).indexOf("份"));
			Log.e("xxxx","fen:"+fen);
			SaleBean saleBean = new SaleBean();
			saleBean.setName(name);
			saleBean.setSale(Integer.parseInt(fen));
			saleBean.setStoreId(userOBean.getId());
			saleList.add(saleBean);
		}
		HashMap<String,Object> map = new HashMap<>();
		map.put("sale",saleList);
		map.put("isSave",true);
		ApiFactory.getApi().addSale(new Gson().toJson(map))
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(requestBean -> {
				if (requestBean.isSuccess()) {
					//更新该条订单状态信息
					updateStatus(list.get(position),d);
				}
			}, throwable -> {
			});
	}


	/**
	 * 更新该条订单状态信息
	 * @param order
	 */
	@SuppressLint("CheckResult") private void updateStatus(SureOrder order,Dialog dialog){
		HashMap<String,Object> map = new HashMap<>();
		map.put("sure",order);
		map.put("isSave",false);
		ApiFactory.getApi().addToSure(new JsonParser().parse(new Gson().toJson(map)).getAsJsonObject())
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Consumer<RequestBean>() {
				@Override public void accept(RequestBean requestBean) throws Exception {
					Log.e("xxxx","bean:"+new Gson().toJson(requestBean));
					if(requestBean.isSuccess()){
						addStoreMoney(order,dialog);
					}
				}
			},throwable -> {
				Log.e("xxxx","update异常："+throwable.getMessage());
			});
	}


	/**
	 * 给商家账户里增加相应的钱数
	 */
	@SuppressLint("CheckResult") private void addStoreMoney(SureOrder order,Dialog d){
		double ye = Double.parseDouble(userOBean.getMoney());
		double newM = ye+Double.parseDouble(order.getZongjia());
		userOBean.setMoney(String.valueOf(newM));
		HashMap<String,Object> map = new HashMap<>();
		Log.e("xxxx","usmo:"+userOBean.getMoney());
		map.put("userBean",userOBean);
		map.put("isSave",false);
		ApiFactory.getApi().updateUserInfo(new JsonParser().parse(new Gson().toJson(map)).getAsJsonObject())
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Consumer<RequestBean>() {
				@Override public void accept(RequestBean requestBean) throws Exception {
					if(requestBean.isSuccess()){
						d.dismiss();
						Toast.makeText(mContext,"take successfully",Toast.LENGTH_SHORT).show();
						getOrderList();
					}else{
						Log.e("xxxx","failed");
					}
				}
			}, new Consumer<Throwable>() {
				@Override public void accept(Throwable throwable) throws Exception {
					Log.e("xxx","exception"+throwable.getMessage());
				}
			});

	}

	@Override
	protected void onResume() {
		super.onResume();
		getOrderList();
	}
}
