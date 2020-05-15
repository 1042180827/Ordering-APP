package com.saxiao.orderinghelpapp.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;

import com.saxiao.orderinghelpapp.R;
import com.saxiao.orderinghelpapp.base.RequestListBean;
import com.saxiao.orderinghelpapp.http.ApiFactory;
import com.saxiao.orderinghelpapp.model.SaleBean;
import com.saxiao.orderinghelpapp.model.SureOrder;
import com.saxiao.orderinghelpapp.model.UserBean;
import com.saxiao.orderinghelpapp.util.DialogUtil;
import com.saxiao.orderinghelpapp.util.SPUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 销售量
 */
public class SaleActivity extends AppCompatActivity {

	@BindView(R.id.title_txt) TextView tvTitle;
	@BindView(R.id.chart)
	PieChart pieChart;

	private Context mContext;
	private UserBean userBean;
	private SureOrder order;
	private List<SaleBean> list;
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StatusBarUtil.setTranslucent(this);
		setContentView(R.layout.activity_sale);
		ButterKnife.bind(this);
		list = new ArrayList<>();
		mContext = SaleActivity.this;
		order = (SureOrder) getIntent().getSerializableExtra("order");
		//读取userBean类
		userBean = SPUtils.readObject("userBean", UserBean.class);

		getSaleData();
	}



	private void getSaleData(){
		Dialog dialog = DialogUtil.getLoadingDialog(SaleActivity.this, "getting sales...");
		dialog.show();
		ApiFactory.getApi().getSaleData(userBean.getId())
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<RequestListBean<SaleBean>>() {
					@Override
					public void accept(RequestListBean<SaleBean> bean) throws Exception {
						dialog.dismiss();
						Log.e("xxx",new Gson().toJson(bean));
						list.clear();
						list.addAll(bean.getReturnData());
						if(list!=null&&list.size()>0){
							showPieView();
						}
					}
				});

	}


	private void initPieChartData(){
		ArrayList<PieEntry> pieEntries = new ArrayList<>();
		for(int i=0;i<list.size();i++){
			pieEntries.add(new PieEntry(list.get(i).getSale(), list.get(i).getName()));
		}
		PieDataSet pieDataSet = new PieDataSet(pieEntries, null);
		// 设置颜色list，让不同的块显示不同颜色，下面是我觉得不错的颜色集合，比较亮
		ArrayList<Integer> colors = new ArrayList<Integer>();
		int[] MATERIAL_COLORS = {
				Color.rgb(200, 172, 255)
		};
		for (int c : MATERIAL_COLORS) {
			colors.add(c);
		}
		for (int c : ColorTemplate.VORDIPLOM_COLORS) {
			colors.add(c);
		}
		pieDataSet.setColors(colors);
		PieData pieData = new PieData(pieDataSet);
		pieDataSet.setSliceSpace(3f);//设置每块饼之间的空隙
		pieDataSet.setSelectionShift(10f);//点击某个饼时拉长的宽度
		pieData.setDrawValues(true);
		pieData.setValueTextSize(12f);
		pieData.setValueTextColor(Color.BLUE);

		pieChart.setData(pieData);
		pieChart.invalidate();

	}

	private void showPieView(){
			pieChart.setUsePercentValues(false);//这货，是否使用百分比显示，但是我还是没操作出来。
			Description description = pieChart.getDescription();
			description.setText("店内食物的销售饼图"); //设置描述的文字
			pieChart.setHighlightPerTapEnabled(true); //设置piecahrt图表点击Item高亮是否可用
			pieChart.animateX(2000);
			initPieChartData();

			pieChart.setDrawEntryLabels(true); // 设置entry中的描述label是否画进饼状图中
			pieChart.setEntryLabelColor(Color.GRAY);//设置该文字是的颜色
			pieChart.setEntryLabelTextSize(10f);//设置该文字的字体大小

			pieChart.setDrawHoleEnabled(true);//设置圆孔的显隐，也就是内圆
			pieChart.setHoleRadius(28f);//设置内圆的半径。外圆的半径好像是不能设置的，改变控件的宽度和高度，半径会自适应。
			pieChart.setHoleColor(Color.WHITE);//设置内圆的颜色
			pieChart.setDrawCenterText(true);//设置是否显示文字
			pieChart.setCenterText("销售量");//设置饼状图中心的文字
			pieChart.setCenterTextSize(10f);//设置文字的消息
			pieChart.setCenterTextColor(Color.RED);//设置文字的颜色
			pieChart.setTransparentCircleRadius(31f);//设置内圆和外圆的一个交叉园的半径，这样会凸显内外部的空间
			pieChart.setTransparentCircleColor(Color.BLACK);//设置透明圆的颜色
			pieChart.setTransparentCircleAlpha(50);//设置透明圆你的透明度



			Legend legend = pieChart.getLegend();//图例
			legend.setEnabled(true);//是否显示
			legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//对齐
			legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);//对齐
			legend.setForm(Legend.LegendForm.DEFAULT);//设置图例的图形样式,默认为圆形
			legend.setOrientation(Legend.LegendOrientation.VERTICAL);//设置图例的排列走向:vertacal相当于分行
			legend.setFormSize(6f);//设置图例的大小
			legend.setTextSize(8f);//设置图注的字体大小
			legend.setFormToTextSpace(4f);//设置图例到图注的距离

			legend.setDrawInside(true);//不是很清楚
			legend.setWordWrapEnabled(false);//设置图列换行(注意使用影响性能,仅适用legend位于图表下面)，我也不知道怎么用的
			legend.setTextColor(Color.BLACK);

	}

	/** 获取指定长度的16进制字符串. */
	public static String randomHexStr(int len) {
		try {
			StringBuffer result = new StringBuffer();
			for(int i=0;i<len;i++) {
				//随机生成0-15的数值并转换成16进制
				result.append(Integer.toHexString(new Random().nextInt(16)));
			}
			return result.toString().toUpperCase();
		} catch (Exception e) {
			System.out.println("获取16进制字符串异常，返回默认...");
			return "00CCCC";
		}
	}

	public int randomColor() {
		int color = Integer.valueOf(randomHexStr(6), 16);
		return color;
	}

}
