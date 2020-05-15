package com.saxiao.orderinghelpapp.adapter;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.base.RequestBean;
import com.saxiao.orderinghelpapp.http.ApiFactory;
import com.saxiao.orderinghelpapp.model.Food;
import com.saxiao.orderinghelpapp.model.FoodType;
import com.saxiao.orderinghelpapp.model.OrderInfo;
import com.saxiao.orderinghelpapp.model.UserBean;
import com.saxiao.orderinghelpapp.util.Base64Utils;
import com.saxiao.orderinghelpapp.util.DialogUtil;
import com.saxiao.orderinghelpapp.view.FoodListActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */

public class RecAdapter extends BaseQuickAdapter<Food,BaseViewHolder> {

    private Context context;
    private String from;
    List<Food> wzList ;
    private BoxSubAdapter mAdapter;
    private UserBean userBean;

    public RecAdapter(int layoutResId ,@Nullable List<Food> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Food item) {
//        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_price,item.getDanjia()+"");
//        ImageView iv = helper.getView(R.id.re_wz_img);
//        iv.setImageBitmap(Base64Utils.decodeToBitmap(item.getImage()));
//        helper.addOnClickListener(R.id.add);
        TextView tvName,tvPrice;
        ImageView ivStore;

        ivStore = helper.getView(R.id.re_wz_img);
        tvName = helper.getView(R.id.tv_foodname);

        tvName.setText(item.getStoreName());

        Bitmap bitmap =  Base64Utils.decodeToBitmap(item.getImage());
        ivStore.setImageBitmap(bitmap);

    }
}


