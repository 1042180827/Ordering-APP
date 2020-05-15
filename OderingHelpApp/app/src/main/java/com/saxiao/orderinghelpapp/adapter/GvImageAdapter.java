package com.saxiao.orderinghelpapp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.model.ImageBean;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * gv图片的adapter
 */

public class GvImageAdapter extends BaseQuickAdapter<ImageBean, BaseViewHolder> {

    private Context context;
    private String from;

    public GvImageAdapter(int layoutResId, @Nullable List<ImageBean> data, Context context,String from) {
        super(layoutResId,data);
        this.context = context;
        this.from = from;
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageBean item) {
	    ImageView iv,ivDel;
	    iv = helper.getView(R.id.iv);
	    ivDel = helper.getView(R.id.delimg);
	    if(item.getBitmap()==null){
		    Picasso.with(context).load("file://"+item.getImagePath()).error(R.mipmap.original).into(iv);
	    }else{
		    iv.setImageBitmap(item.getBitmap());
	    }

	    helper.addOnClickListener(R.id.delimg);

	    if(from.equals("add")){
	    	ivDel.setVisibility(View.VISIBLE);
	    }else{
	    	ivDel.setVisibility(View.GONE);
	    }
    }
}
