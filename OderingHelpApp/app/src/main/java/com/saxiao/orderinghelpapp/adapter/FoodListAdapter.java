package com.saxiao.orderinghelpapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.model.Food;
import com.saxiao.orderinghelpapp.util.Base64Utils;
import com.saxiao.orderinghelpapp.util.BitmapUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 *
 */

public class FoodListAdapter extends BaseQuickAdapter<Food,BaseViewHolder> {

	private String from;
	private Context context;
    public FoodListAdapter(int layoutResId, @Nullable List<Food> data, Context context,String from) {
        super(layoutResId,data);
        this.from = from;
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
	@Override
    protected void convert(BaseViewHolder helper, Food item) {
	    TextView tvName,tvDj;
	    ImageView iv;

	    tvName = helper.getView(R.id.tv_name);
	    tvDj = helper.getView(R.id.tv_dj);
	    tvName.setText(item.getName());
	    tvDj.setText(item.getDanjia()+"/份");
	    iv = helper.getView(R.id.imageView);

		if(item.getBitmap()==null){
			Bitmap bitmap =  Base64Utils.decodeToBitmap(item.getImage());
			iv.setImageBitmap(bitmap);
		}else{
			iv.setImageBitmap(BitmapUtils.byte2Bitmap(item.getBitmap()));
		}
	    helper.addOnClickListener(R.id.btn_up);

	    Button btnUp = helper.getView(R.id.btn_up);
	    if(from.equals("manage")){
	    	btnUp.setVisibility(View.VISIBLE);
	    }else{
	    	btnUp.setVisibility(View.GONE);
	    }
    }
}
