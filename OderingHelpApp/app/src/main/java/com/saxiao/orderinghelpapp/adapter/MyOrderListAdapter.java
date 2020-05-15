package com.saxiao.orderinghelpapp.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.model.SureOrder;
import java.util.List;

/**
 *
 */

public class MyOrderListAdapter extends BaseQuickAdapter<SureOrder, BaseViewHolder> {


    public MyOrderListAdapter(int layoutResId, @Nullable List<SureOrder> data, Context context) {
        super(layoutResId,data);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
	@Override
    protected void convert(BaseViewHolder helper, SureOrder item) {
	    Log.e("xxxx","itemsds");
	    TextView tvName,tvDetail,tvTime,tvStatus;
	    LinearLayout ll;

	    tvName = helper.getView(R.id.tv_name);
	    tvDetail = helper.getView(R.id.tv_deatil);
	    tvTime = helper.getView(R.id.tv_date);
	    tvStatus = helper.getView(R.id.tv_status);
	    ll = helper.getView(R.id.ll);

	    tvName.setText(item.getStoreName());
	    tvDetail.setText(item.getCaipin());
	    tvTime.setText(item.getDate());

	    if(item.getStatus() == 0){
		    tvStatus.setText("待接单");
		    tvStatus.setTextColor(ContextCompat.getColor(mContext,R.color.blue));

		    ll.setVisibility(View.GONE);
	    }else if(item.getStatus() == 1){
		    tvStatus.setText("已接单");
		    tvStatus.setTextColor(ContextCompat.getColor(mContext,R.color.green));

		    ll.setVisibility(View.VISIBLE);
	    }

	    //评价
	    helper.addOnClickListener(R.id.tv_ping);
    }
}
