package com.saxiao.orderinghelpapp.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.adapter.BoxAdapter;
import com.saxiao.orderinghelpapp.adapter.IndexAdapter;
import com.saxiao.orderinghelpapp.adapter.RecAdapter;
import com.saxiao.orderinghelpapp.http.ApiFactory;
import com.saxiao.orderinghelpapp.model.Food;
import com.saxiao.orderinghelpapp.model.IndexItem;
import com.saxiao.orderinghelpapp.model.UserBean;
import com.saxiao.orderinghelpapp.util.DialogUtil;
import com.saxiao.orderinghelpapp.util.SPUtils;
import com.saxiao.orderinghelpapp.view.holder.LocalImageHolderView;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
	@BindView(R.id.recycle) RecyclerView recyclerView;
//	@BindView(R.id.recomm) RecyclerView recyclerView1;
//	@BindView(R.id.swipe_ref1) SwipeRefreshLayout mSwipeRefresh;
	//banner
	@BindView(R.id.cb_banner) ConvenientBanner cb_banner;
	private List<Integer> bannerList;
	private List<Food> foodlist;
	private List<IndexItem> list;
	private IndexAdapter adapter;
	private Unbinder unbinder;
	private UserBean userOBean;
	private RecAdapter mAdapter;
	private List<Food> reclist;
	private Context mContext;
	@Override protected void onCreate(Bundle savedInstanceState) {
		mContext = Main2Activity.this;
		super.onCreate(savedInstanceState);
		StatusBarUtil.setTranslucent(this);
		setContentView(R.layout.activity_main2);
		ButterKnife.bind(this);
		initBanner();
		loadMenuList();
//		if(userOBean.getType().equals("买家")){
//        mAdapter=new RecAdapter(R.layout.box_sub_item,foodlist,mContext,userOBean);
//        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        recyclerView.setAdapter(mAdapter);
//			loadRecommand();
//		}
	}

	//轮播图初始化
	private void initBanner() {
		bannerList = new ArrayList<>();
		bannerList.add(R.mipmap.timg);
		bannerList.add(R.mipmap.timg1);
		bannerList.add(R.mipmap.timg3);
		bannerList.add(R.mipmap.timg4);
		cb_banner.startTurning(3000);

		cb_banner.setPages(new CBViewHolderCreator() {
			@Override
			public Holder createHolder(View itemView) {
				return new LocalImageHolderView(itemView);
			}

			@Override
			public int getLayoutId() {
				return R.layout.item_banner;
			}
		},bannerList)
			//设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器，不需要圆点指示器可以不设
			.setPageIndicator(new int[] { R.drawable.banner_normal, R.drawable.banner_select })
			//设置指示器的方向
			.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
			//设置指示器是否可见
			.setPointViewVisible(true);
		cb_banner.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(int position) {
				// ToastyUtils.toastSuccess(getActivity(),"跳转"+ position +"页面");
			}
		});
	}

	private void loadMenuList() {
		list = new ArrayList<>();
		userOBean = SPUtils.readObject("userBean", UserBean.class);


		switch (userOBean.getType()){
			case "商家":
				list.add(new IndexItem(R.string.sj_manage, R.mipmap.index_in, FoodManageActivity.class,R.color.list_a));
				list.add(new IndexItem(R.string.sj_see, R.mipmap.index_search, OrderListActivity.class,R.color.list_c));
				list.add(new IndexItem(R.string.sj_xs, R.mipmap.index_search, SaleActivity.class,R.color.list_d));
				list.add(new IndexItem(R.string.sj_pj, R.mipmap.index_search, StorePjListActivity.class,R.color.list_e));
				list.add(new IndexItem(R.string.sj_info, R.mipmap.index_search, UserInfoActivity.class,R.color.list_d));
				break;
			case "买家":
				list.add(new IndexItem(R.string.user_store, R.mipmap.index_in, StoreListActivity.class,R.color.list_a));
				list.add(new IndexItem(R.string.user_my, R.mipmap.index_search, MyOrderListActivity.class,R.color.list_e));
				list.add(new IndexItem(R.string.user_cai, R.mipmap.index_search, UserInfoActivity.class,R.color.list_d));
				list.add(new IndexItem(R.string.user_recommend,R.mipmap.index_out,RecommendActivity.class,R.color.list_c));
				break;
			case "管理员":
				list.add(new IndexItem(R.string.index_user, R.mipmap.index_in, UserManageActivity.class,R.color.list_a));
				list.add(new IndexItem(R.string.index_sj, R.mipmap.index_allocation, UserManageActivity.class,R.color.list_d));
				break;
			default:
				break;
		}

		adapter = new IndexAdapter(R.layout.main_gridview_item,list,Main2Activity.this);

		adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				IndexItem item = list.get(position);
				switch (item.getName()) {
					case R.string.index_user:
						Intent intent = new Intent(Main2Activity.this, item.getClassPazh());
						intent.putExtra("from","user");
						startActivity(intent);
						break;
					case R.string.index_sj:
						Intent intent2 = new Intent(Main2Activity.this, item.getClassPazh());
						intent2.putExtra("from","sj");
						startActivity(intent2);
						break;
					case R.string.user_store:
					case R.string.user_cai:
					case R.string.user_my:
					case R.string.user_recommend:
					case R.string.sj_manage:
					case R.string.sj_pj:
					case R.string.sj_see:
					case R.string.sj_xs:
					case R.string.sj_info:
						startActivity(new Intent(Main2Activity.this, item.getClassPazh()));
						break;
					default:
						break;
				}
			}
		});

		recyclerView.setAdapter(adapter);
		GridLayoutManager gridLayoutManager=new GridLayoutManager(Main2Activity.this, 3);
		gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
			@Override
			public int getSpanSize(int position) {
				if(position==1){
					return 2;
				}
				return 1;
			}
		});
		recyclerView.setLayoutManager(gridLayoutManager);
	}

//	@SuppressLint("CheckResult")private void loadRecommand(){
//
//		userOBean = SPUtils.readObject("userBean", UserBean.class);
//        reclist = new ArrayList<>();
//		Dialog dialog = DialogUtil.getLoadingDialog(Main2Activity.this, "getting list...");
//		dialog.show();
//		ApiFactory.getApi().getRecFood(userOBean.getId())
//				.subscribeOn(Schedulers.io())
//				.observeOn(AndroidSchedulers.mainThread())
//				.subscribe(requestBean -> {
//					dialog.dismiss();
//					//停止刷新
//					if (mSwipeRefresh.isRefreshing()) {
//						mSwipeRefresh.setRefreshing(false);
//					}
//					if (requestBean.isSuccess()) {
//						//请求成功
//						if (requestBean.getReturnData().size() > 0) {
//							Log.e("xxx",new Gson().toJson(requestBean.getReturnData()));
//							//有数据
//							reclist.clear();
//							reclist.addAll(requestBean.getReturnData());
//							mAdapter.notifyDataSetChanged();
//						} else {
//							//没有数据，设置空布局
//							View emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_empty, (ViewGroup) recyclerView.getParent(), false);
//							mAdapter.setNewData(null);
//							mAdapter.setEmptyView(emptyView);
//							mAdapter.notifyDataSetChanged();
//						}
//					} else {//请求失败
//						Toast.makeText(mContext, "request exception1：" + requestBean.getMsg(), Toast.LENGTH_SHORT).show();
//					}
//				}, throwable -> {
//					dialog.dismiss();
//					//停止刷新
//					if (mSwipeRefresh.isRefreshing()) {
//						mSwipeRefresh.setRefreshing(false);
//					}
//					Toast.makeText(mContext, "request exception2：" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
//				});
//	}

}
