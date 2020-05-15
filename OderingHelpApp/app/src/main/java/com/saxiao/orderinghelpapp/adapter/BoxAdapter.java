package com.saxiao.orderinghelpapp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.model.Food;
import com.saxiao.orderinghelpapp.model.FoodType;
import com.saxiao.orderinghelpapp.model.UserBean;
import java.util.List;

/**
 *
 */

public class BoxAdapter extends BaseQuickAdapter<FoodType, BaseViewHolder> {

	private Context context;
	private UserBean userBean;
	private boolean isSelect;
	private BoxSubAdapter mAdapter;
	private int storeId;
	private String storeName;

	public BoxAdapter(int layoutResId, @Nullable List<FoodType> data, Context context,UserBean userBean,int storeId,String storeName) {
		super(layoutResId, data);
		this.context = context;
		this.userBean = userBean;
		this.storeId= storeId;
		this.storeName = storeName;
	}

	@Override protected void convert(BaseViewHolder helper, FoodType item) {
		Log.e("xxxxx","name:"+item.getName());
		//设置基本信息
		helper.setText(R.id.name, item.getName());

		//设置详细消息布局隐藏
		helper.setGone(R.id.item_sub, item.isShow());
		//设置title的点击监听
		helper.addOnClickListener(R.id.rl_item_title);

		//设置子布局
		RecyclerView recyclerView = helper.getView(R.id.item_recycler);
		List<Food> wzList = item.getFood();
		mAdapter = new BoxSubAdapter(R.layout.box_sub_item, item.getFood(),context,userBean,storeId,storeName);
		recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
		recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
		recyclerView.setAdapter(mAdapter);
		if(wzList==null || wzList.size()==0){
			View emptyView = LayoutInflater.from(mContext)
				.inflate(R.layout.layout_empty, (ViewGroup) recyclerView.getParent(), false);
			mAdapter.setEmptyView(emptyView);
			mAdapter.notifyDataSetChanged();
		}
	}

}
