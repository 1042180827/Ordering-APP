package com.saxiao.orderinghelpapp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.model.PingBean;
import java.util.List;

/**
 * 详情里面评论的adapter
 */

public class DetailPlListAdapter extends BaseQuickAdapter<PingBean, BaseViewHolder> {

    private Context context;
    private String from;

    public DetailPlListAdapter(int layoutResId, @Nullable List<PingBean> data, Context context) {
        super(layoutResId,data);
        this.context = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, PingBean item) {
	    TextView tvName,tvContent,tvDate;
	    tvName = helper.getView(R.id.name);
	    tvContent = helper.getView(R.id.content);
	    tvDate = helper.getView(R.id.date);

	    tvName.setText(item.getName());
	    tvContent.setText(item.getContent());
	    tvDate.setText(item.getDate());

    }
}
