package com.saxiao.orderinghelpapp.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.model.UserBean;
import java.util.List;

/**
 *
 */

public class UserListAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> {


    public UserListAdapter(int layoutResId, @Nullable List<UserBean> data, Context context) {
        super(layoutResId,data);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
	@Override
    protected void convert(BaseViewHolder helper, UserBean item) {
	    TextView tvName,tvPhone,tvSName,tvSAdd;

	    tvName = helper.getView(R.id.tv_name);
	    tvPhone = helper.getView(R.id.tv_phone);
	    tvSName = helper.getView(R.id.tv_sname);
		tvSAdd = helper.getView(R.id.tv_sadd);

		if(item.getType().equals("买家")){
			tvSName.setVisibility(View.GONE);
			tvSAdd.setVisibility(View.GONE);
		}else{
			tvSName.setVisibility(View.VISIBLE);
			tvSAdd.setVisibility(View.VISIBLE);
		}
		tvName.setText("姓名："+item.getName());
		tvPhone.setText("联系方式："+item.getPhoneNumber());
		tvSName.setText("商铺名称："+item.getStoreName());
		tvSAdd.setText("商铺地址："+item.getStoreAddress());
	    helper.addOnClickListener(R.id.tv_up);


    }
}
