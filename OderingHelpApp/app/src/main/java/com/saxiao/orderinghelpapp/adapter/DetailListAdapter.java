package com.saxiao.orderinghelpapp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.model.OrderInfo;
import com.saxiao.orderinghelpapp.model.UpdateCount;
import com.saxiao.orderinghelpapp.util.AmountView;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class DetailListAdapter extends BaseQuickAdapter<OrderInfo, BaseViewHolder> {


    private Context context;
    private ArrayList<OrderInfo> list;

	public DetailListAdapter(int layoutResId, @Nullable List<OrderInfo> data) {
		super(layoutResId, data);
	}



	@Override protected void convert(BaseViewHolder helper, OrderInfo item) {
		TextView tvName,tvDan;
		AmountView tvCount;
		tvName = helper.getView(R.id.tvdename);
		tvDan = helper.getView(R.id.tvdedanjia);
		tvCount = helper.getView(R.id.amount_view);

		tvCount.setGoodsStorage(999);
		tvCount.setNum(item.getFenshu(), helper.getPosition());
		tvCount.setOnAmountChangeListener((view, amount, position) -> {
			if (position == helper.getPosition()) {
				item.setFenshu(amount);
				//计算当前金额
				EventBus.getDefault().post(new UpdateCount());
			}
		});

		tvName.setText(item.getCaipin());
		tvDan.setText(item.getDanjia()+"元");

	}

}
