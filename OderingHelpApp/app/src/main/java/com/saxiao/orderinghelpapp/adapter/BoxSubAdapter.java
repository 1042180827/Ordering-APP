package com.saxiao.orderinghelpapp.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.base.RequestBean;
import com.saxiao.orderinghelpapp.http.ApiFactory;
import com.saxiao.orderinghelpapp.model.Food;
import com.saxiao.orderinghelpapp.model.OrderInfo;
import com.saxiao.orderinghelpapp.model.UserBean;
import com.saxiao.orderinghelpapp.util.Base64Utils;
import com.saxiao.orderinghelpapp.util.DialogUtil;
import com.saxiao.orderinghelpapp.view.FoodListActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



public class BoxSubAdapter extends BaseQuickAdapter<Food, BaseViewHolder> {
	private Context mContext;
	private UserBean userBean;
	private int storeId;
	private String storeName;
    public BoxSubAdapter(int layoutResId, @Nullable List<Food> data,Context context,UserBean userBean,int storeId,String storeName) {
        super(layoutResId,data);
        this.mContext = context;
        this.userBean = userBean;
        this.storeId = storeId;
        this.storeName = storeName;
    }

    @Override
    protected void convert(BaseViewHolder helper, Food item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_danjia,item.getDanjia()+"/份");
	    ImageView iv = helper.getView(R.id.re_wz_img);
	    iv.setImageBitmap(Base64Utils.decodeToBitmap(item.getImage()));

	    TextView tvAdd,tvStatus;
	    tvAdd = helper.getView(R.id.add);
	    tvStatus = helper.getView(R.id.status);
	    helper.addOnClickListener(R.id.add);

	    //已售罄
	    if("售罄".equals(item.getStatus())){
	    	helper.getView(R.id.hasno).setVisibility(View.VISIBLE);
		    tvStatus.setVisibility(View.GONE);
		    tvAdd.setVisibility(View.GONE);
	    }else{
	    	helper.getView(R.id.hasno).setVisibility(View.GONE);
		    if(item.isAdd()){
			    tvStatus.setVisibility(View.VISIBLE);
			    tvAdd.setVisibility(View.GONE);
		    }else{
			    tvStatus.setVisibility(View.GONE);
			    tvAdd.setVisibility(View.VISIBLE);
		    }
	    }

	    //加入购物车
	    tvAdd.setOnClickListener(new View.OnClickListener() {
		    @Override public void onClick(View view) {
			    addToDing(item,userBean);
		    }
	    });
    }


	/**
	 * 加入订单
	 */
	@SuppressLint("CheckResult") private void addToDing(Food item,UserBean userBean){
		FoodListActivity activity = (FoodListActivity) mContext;
		Dialog dialog = DialogUtil.getLoadingDialog(activity, "正在加入订单...");
		dialog.show();
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCaipin(item.getName());
		orderInfo.setDanjia(item.getDanjia()+"");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		orderInfo.setDate(sdf.format(new Date()));
		orderInfo.setType(item.getType());
		orderInfo.setUserId(String.valueOf(userBean.getId()));
		orderInfo.setUserName(userBean.getName());
		orderInfo.setUserId(String.valueOf(userBean.getId()));
		orderInfo.setFenshu(1);
		orderInfo.setStoreId(storeId);
		orderInfo.setStoreName(storeName);
		orderInfo.setFoodTaste(item.getTaste());
		ApiFactory.getApi().addToDing(new JsonParser().parse(new Gson().toJson(orderInfo)).getAsJsonObject())
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Consumer<RequestBean>() {
				@Override public void accept(RequestBean bean) throws Exception {
					dialog.dismiss();
					if(bean.isSuccess()){
						Toast.makeText(mContext,"已加入购物车", Toast.LENGTH_LONG).show();
						item.setAdd(true);
						notifyDataSetChanged();
					}
				}
			},throwable -> {
				dialog.dismiss();
			});
	}

}
