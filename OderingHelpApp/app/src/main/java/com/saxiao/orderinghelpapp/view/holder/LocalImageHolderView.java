package com.saxiao.orderinghelpapp.view.holder;

import android.view.View;
import android.widget.ImageView;
import com.bigkoo.convenientbanner.holder.Holder;
import com.saxiao.orderinghelpapp.R;

/**
 * banner图
 * 本地图片
 */

public class LocalImageHolderView extends Holder<Integer> {

    private ImageView imageView;

    public LocalImageHolderView(View itemView) {
        super(itemView);
    }


    @Override
    protected void initView(View itemView) {
        imageView = itemView.findViewById(R.id.iv_banner);
    }

    @Override
    public void updateUI(Integer data) {
        imageView.setImageResource(data);
    }
}
