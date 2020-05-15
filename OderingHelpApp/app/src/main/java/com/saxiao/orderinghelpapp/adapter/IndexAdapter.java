package com.saxiao.orderinghelpapp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.model.IndexItem;
import java.util.List;

public class IndexAdapter extends BaseQuickAdapter<IndexItem, BaseViewHolder> {
	private Context context;
	public IndexAdapter(int layoutResId, @Nullable List<IndexItem> data, Context context) {
		super(layoutResId,data);
		this.context = context;
	}
	@Override protected void convert(BaseViewHolder helper, IndexItem item) {
		ImageView imageView = helper.getView(R.id.function_view);
		TextView nameView = helper.getView(R.id.function_name);
		TextView symbolView= helper.getView(R.id.function_symbol);
		LinearLayout content = helper.getView(R.id.content);
		int sum = item.getSum();
		if (sum == 0) {
			symbolView.setVisibility(View.GONE);
		} else if (sum < 100) {
			symbolView.setText(sum + "");
			symbolView.setVisibility(View.VISIBLE);
		} else {
			symbolView.setText("99");
			symbolView.setVisibility(View.VISIBLE);
		}
		content.setBackgroundColor(context.getResources().getColor(item.getBackground()));
		imageView.setImageResource(item.getImage());
		nameView.setText(item.getName());

	}
}
