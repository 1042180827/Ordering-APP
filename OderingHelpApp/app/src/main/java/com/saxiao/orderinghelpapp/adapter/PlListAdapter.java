package com.saxiao.orderinghelpapp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.model.Advice;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 评论的adapter
 */

public class PlListAdapter extends BaseQuickAdapter<Advice,BaseViewHolder> {

    private Context context;
    private String from;

    public PlListAdapter(int layoutResId, @Nullable List<Advice> data, Context context) {
        super(layoutResId,data);
        this.context = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, Advice item) {
	    TextView tvContent,tvDate;
	    ImageView iv1,iv2,iv3;

	    iv1 = helper.getView(R.id.iv1);
	    iv2 = helper.getView(R.id.iv2);
	    iv3 = helper.getView(R.id.iv3);
	    tvContent = helper.getView(R.id.content);
	    tvDate = helper.getView(R.id.date);

	    tvContent.setText(item.getContent());
	    tvDate.setText(item.getDate());
	    List<String> images ;
	    if(!TextUtils.isEmpty(item.getImage())){
		    images = Arrays.asList(item.getImage().split(","));
	    }else{
	    	images = new ArrayList<>();
	    }

	    //三个图片
	    if(images.size()>0){
		    Picasso.with(context).load("file://"+images.get(0)).error(R.mipmap.original).into(iv1);
	    }else{
		    iv1.setVisibility(View.GONE);
		    iv2.setVisibility(View.GONE);
		    iv3.setVisibility(View.GONE);
	    }
	    if(images.size()>1){
		    Picasso.with(context).load("file://"+images.get(1)).error(R.mipmap.original).into(iv2);
	    }else{
		    iv2.setVisibility(View.GONE);
		    iv3.setVisibility(View.GONE);
	    }
	    if(images.size()>2){
		    Picasso.with(context).load("file://"+images.get(2)).error(R.mipmap.original).into(iv3);
	    }else{
		    iv3.setVisibility(View.GONE);
	    }


    }
}
