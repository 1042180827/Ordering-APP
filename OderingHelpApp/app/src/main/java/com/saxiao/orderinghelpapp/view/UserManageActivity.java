package com.saxiao.orderinghelpapp.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.adapter.UserListAdapter;
import com.saxiao.orderinghelpapp.base.RequestListBean;
import com.saxiao.orderinghelpapp.http.ApiFactory;
import com.saxiao.orderinghelpapp.model.UserBean;
import com.saxiao.orderinghelpapp.util.DialogUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserManageActivity extends AppCompatActivity {

	@BindView(R.id.recycler) RecyclerView rcView;
	@BindView(R.id.title_txt) TextView tvTitle;

	private List<UserBean> list;
	private UserListAdapter mAdapter;
	private Context mContext;
	private String from;
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StatusBarUtil.setTranslucent(this);
		setContentView(R.layout.activity_user_manage);
		ButterKnife.bind(this);
		mContext = UserManageActivity.this;
		from = getIntent().getStringExtra("from");
		if(from.equals("sj")){
			tvTitle.setText("Merchant");
		}else{
			tvTitle.setText("Customer");
		}
		list = new ArrayList<>();
		mAdapter=new UserListAdapter(R.layout.userlist_item,list,mContext);
		rcView.setLayoutManager(new LinearLayoutManager(mContext));
		rcView.setAdapter(mAdapter);

		//修改
		mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
			@Override public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
				if(view.getId() == R.id.tv_up){
					Intent intent = new Intent(UserManageActivity.this,UserInfoActivity.class);
					intent.putExtra("user", (Serializable) list.get(position));
					startActivity(intent);
				}
			}
		});

	//	getUserList();

	}

	@Override protected void onResume() {
		super.onResume();
		getUserList();
	}

	/**
	 * 获取用户列表
	 */
	@SuppressLint("CheckResult") private void getUserList(){
		Dialog dialog = DialogUtil.getLoadingDialog(UserManageActivity.this, "getting list...");
		dialog.show();
		ApiFactory.getApi().getUser()
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Consumer<RequestListBean<UserBean>>() {
				@Override public void accept(RequestListBean<UserBean> bean) {
					dialog.dismiss();
					list.clear();
					for(int i=0;i<bean.getReturnData().size();i++){
						if(from.equals("sj")){
							if(bean.getReturnData().get(i).getType().equals("商家")){
								list.add(bean.getReturnData().get(i));
							}
						}else{
							if(bean.getReturnData().get(i).getType().equals("买家")){
								list.add(bean.getReturnData().get(i));
							}
						}
					}
					if(list.size()==0){
						//没有数据，设置空布局
						View emptyView = LayoutInflater.from(mContext)
							.inflate(R.layout.layout_empty, (ViewGroup) rcView.getParent(), false);
						mAdapter.setEmptyView(emptyView);
					}
					mAdapter.notifyDataSetChanged();
				}
			}, new Consumer<Throwable>() {
				@Override public void accept(Throwable throwable) {
					dialog.dismiss();
					Toast.makeText(mContext,"exception："+throwable.getMessage(),Toast.LENGTH_SHORT).show();
				}
			});

	}
}
