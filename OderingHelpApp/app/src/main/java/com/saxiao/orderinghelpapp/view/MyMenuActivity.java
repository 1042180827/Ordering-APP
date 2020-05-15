package com.saxiao.orderinghelpapp.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.jaeger.library.StatusBarUtil;
import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.adapter.DetailListAdapter;
import com.saxiao.orderinghelpapp.base.RequestBean;
import com.saxiao.orderinghelpapp.base.RequestListBean;
import com.saxiao.orderinghelpapp.http.ApiFactory;
import com.saxiao.orderinghelpapp.model.Caipin;
import com.saxiao.orderinghelpapp.model.OrderInfo;
import com.saxiao.orderinghelpapp.model.SureOrder;
import com.saxiao.orderinghelpapp.model.UpdateCount;
import com.saxiao.orderinghelpapp.model.UserBean;
import com.saxiao.orderinghelpapp.util.DialogUtil;
import com.saxiao.orderinghelpapp.util.SPUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 我的菜单
 */
public class MyMenuActivity extends AppCompatActivity {
	@BindView(R.id.tvzongjia)
	TextView tvzongjia;
	@BindView(R.id.btnsure)
	TextView btn;//确认下单
	@BindView(R.id.item_recycler)
	RecyclerView rv;
	@BindView(R.id.etdetail)
	EditText beizhu;//备注

	private StringBuilder sb;
	private StringBuilder sb1;
	private String[] sb2;
	private ArrayList<OrderInfo> list;
	private DetailListAdapter adapter;
	private ArrayList<Caipin> caipinList;
	private Double zongjia;
	private Double zong=0.0;

	private String date;//当天日期

	private Context mContext;
	private UserBean userOBean;
	private int storeId;
	private String storeName;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StatusBarUtil.setTranslucent(this);
		setContentView(R.layout.activity_detatil);
		EventBus.getDefault().register(this);
		ButterKnife.bind(this);
		mContext = MyMenuActivity.this;
		//读取userBean类
		userOBean = SPUtils.readObject("userBean", UserBean.class);
		list = new ArrayList<>();
		caipinList = new ArrayList<Caipin>();
		adapter = new DetailListAdapter(R.layout.detailitem,list);
		rv.setLayoutManager(new LinearLayoutManager(mContext));
		rv.setAdapter(adapter);


		//确认下单
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sureOrder();
			}});

		//获取临时订单信息
		getOrderList();
	}

	/**
	 * 确认下单
	 */
	private void sureOrder(){
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("sure to order？").setMessage("can not change anymore")
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					doSure();
				}}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).create();
		builder.show();

	}

	@SuppressLint("CheckResult") private void getOrderList(){
		Dialog dialog = DialogUtil.getLoadingDialog(MyMenuActivity.this, "getting list...");
		dialog.show();
		ApiFactory.getApi().getOrderByUserId(userOBean.getId())
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Consumer<RequestListBean<OrderInfo>>() {
				@Override public void accept(RequestListBean<OrderInfo> bean) throws Exception {
					dialog.dismiss();
					list.clear();
					list.addAll(bean.getReturnData());
					adapter.notifyDataSetChanged();
					sb = new StringBuilder();
					for(int i=0;i<list.size();i++){
						storeId = list.get(i).getStoreId();
						storeName = list.get(i).getStoreName();
						sb.append(list.get(i).getCaipin()+"  "+list.get(i).getFenshu()+"份  "+";");
					}
					for (int i=0;i<list.size();i++){
						Caipin caipin = new Caipin();
						caipin.setName(list.get(i).getCaipin());
						caipin.setUserId(Integer.parseInt(list.get(i).getUserId()));
						caipin.setTaste(list.get(i).getFoodTaste());
						caipin.setStoreId(String.valueOf(list.get(i).getStoreId()));
						caipinList.add(caipin);
					}
					zong=0.0;
					for(int i=0;i<list.size();i++){
						zong = zong+Double.parseDouble(list.get(i).getDanjia());
					}
					tvzongjia.setText("total price："+zong+"￥");
					adapter.notifyDataSetChanged();

				}
			},throwable -> {
				dialog.dismiss();
			});

	}
	@SuppressLint("CheckResult") private void doSure(){
		Dialog dialog = DialogUtil.getLoadingDialog(MyMenuActivity.this, "ordering...");
		dialog.show();
		SureOrder sureOrder = new SureOrder();
		sureOrder.setUserName(userOBean.getName());
		sureOrder.setCaipin(sb.toString());
		sureOrder.setBeizhu(beizhu.getText().toString());
		sureOrder.setZongjia(zong+"");
		sureOrder.setStoreId(storeId);
		sureOrder.setStoreName(storeName);
		sureOrder.setUserName(userOBean.getName());
		sureOrder.setUserId(userOBean.getId());
		//获取当天日期
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date1 = new Date();
		date = format.format(date1);

		sureOrder.setDate(date);
//        Caipin caipin = new Caipin();
//        caipin.setName(sb.toString());
//        caipin.setUserId(userOBean.getId());
		HashMap<String,Object> map = new HashMap<>();
		map.put("sure",sureOrder);
		map.put("isSave",true);
		for (int i=0;i<caipinList.size();i++){
			map.put("caipin",caipinList.get(i));
			ApiFactory.getApi().addrec(new JsonParser().parse(new Gson().toJson(map)).getAsJsonObject())
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(new Consumer<RequestBean>() {
						@Override public void accept(RequestBean requestBean) throws Exception {
							dialog.dismiss();
							if(requestBean.isSuccess()){
								//deleteByZhuo();
							}
						}
					},throwable -> {dialog.dismiss();});
		}
		ApiFactory.getApi().addToSure(new JsonParser().parse(new Gson().toJson(map)).getAsJsonObject())
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Consumer<RequestBean>() {
					@Override public void accept(RequestBean requestBean) throws Exception {
						dialog.dismiss();
						if(requestBean.isSuccess()){
							deleteByZhuo();
						}
				}
			},throwable -> {dialog.dismiss();});
	}

	/**
	 * 从临时orderInfo表中删除某账号数据
	 */
	@SuppressLint("CheckResult") private void deleteByZhuo(){
		ApiFactory.getApi().deleteOrderById(userOBean.getId())
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Consumer<RequestBean>() {
				@Override public void accept(RequestBean requestBean) throws Exception {
					if(requestBean.isSuccess()){
						payMethod();
					}
				}
			},throwable -> {
				Toast.makeText(mContext,"request exception："+throwable.getMessage(), Toast.LENGTH_LONG).show();
			});
	}



	/**
	 * 更改数量后，金额改变
	 */
	@Subscribe(threadMode = ThreadMode.MAIN)
	@SuppressWarnings("unused")
	public void updateTaskData(UpdateCount updateData) {
		zong=0.0;
		for(int i=0;i<list.size();i++){
			zong = zong+Double.parseDouble(list.get(i).getDanjia())*list.get(i).getFenshu();
		}
		tvzongjia.setText("total price："+zong+"￥");
	}

	/**
	 * 支付
	 * 个人账户扣除对应的金额
	 */
	@SuppressLint("CheckResult") private void payMethod(){
		Dialog dialog = DialogUtil.getLoadingDialog(MyMenuActivity.this, "paying...");
		dialog.show();
		//判断余额是否够用
		double ye = Double.parseDouble(userOBean.getMoney());
		if(ye<zong){
			dialog.dismiss();
			Toast.makeText(mContext,"You have insufficient balance to pay",Toast.LENGTH_SHORT).show();
			return;
		}
		HashMap<String,Object> map = new HashMap<>();
		double y = Double.parseDouble(userOBean.getMoney());
		userOBean.setMoney(String.valueOf(y-zong));
		map.put("userBean",userOBean);
		map.put("isSave",false);
		ApiFactory.getApi().updateUserInfo(new JsonParser().parse(new Gson().toJson(map)).getAsJsonObject())
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Consumer<RequestBean>() {
				@Override public void accept(RequestBean requestBean) throws Exception {
					dialog.dismiss();
					if(requestBean.isSuccess()){
						Toast.makeText(mContext,"paid successfully",Toast.LENGTH_SHORT).show();
						//保存修改后的userBean
						SPUtils.saveObject("userBean",userOBean);
						finish();
					}else{
						Toast.makeText(mContext,"paid failed："+requestBean.getMsg(),Toast.LENGTH_SHORT).show();
					}
				}
			}, new Consumer<Throwable>() {
				@Override public void accept(Throwable throwable) throws Exception {
					dialog.dismiss();
					Toast.makeText(mContext,"request exception:"+throwable.getMessage(),Toast.LENGTH_SHORT).show();
				}
			});

	}

}
