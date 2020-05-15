package com.saxiao.orderinghelpapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.model.UserBean;
import com.saxiao.orderinghelpapp.util.Base64Utils;
import java.util.List;

/**
 * 店铺列表的adapter
 */

public class StoreListAdapter extends BaseQuickAdapter<UserBean,BaseViewHolder> {

    private Context context;
    private String from;

    public StoreListAdapter(int layoutResId, @Nullable List<UserBean> data, Context context) {
        super(layoutResId,data);
        this.context = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, UserBean item) {
	    TextView tvName,tvAddress;
	    ImageView ivStore;

	    ivStore = helper.getView(R.id.iv_store);
	    tvName = helper.getView(R.id.tv_name);
	    tvAddress = helper.getView(R.id.tv_address);

	    tvName.setText(item.getStoreName());
	    tvAddress.setText(item.getStoreAddress());

	    Bitmap bitmap =  Base64Utils.decodeToBitmap(item.getStoreImage());
		ivStore.setImageBitmap(bitmap);

    }
}
