package com.saxiao.orderinghelpapp.http;

import com.google.gson.JsonObject;
import com.saxiao.orderinghelpapp.base.RequestBean;
import com.saxiao.orderinghelpapp.base.RequestListBean;
import com.saxiao.orderinghelpapp.model.Advice;
import com.saxiao.orderinghelpapp.model.Caipin;
import com.saxiao.orderinghelpapp.model.Food;
import com.saxiao.orderinghelpapp.model.FoodType;
import com.saxiao.orderinghelpapp.model.OrderInfo;
import com.saxiao.orderinghelpapp.model.PingBean;
import com.saxiao.orderinghelpapp.model.SaleBean;
import com.saxiao.orderinghelpapp.model.SureOrder;
import com.saxiao.orderinghelpapp.model.UserBean;
import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 *
 * 网络请求API
 *
 */

public interface Api {

	/**
	 * post请求，请求登录
	 */
	@POST(BaseApi.Login)
	@FormUrlEncoded
	Flowable<RequestBean<UserBean>> login(@Field("userName") String userName,@Field("pwd") String pwd);

	/**
	 * post请求，提交个人信息(注册)
	 */
	@POST(BaseApi.upDateUserInfo) @Headers("Content-Type:application/json; charset=utf-8")
	Flowable<RequestBean> updateUserInfo(@Body JsonObject json);

	/**
	 * post请求，获取全部商家
	 */
	@POST(BaseApi.getAllStore)
	Flowable<RequestListBean<UserBean>> getStoreList();


	/**
	 * post请求，
	 */
	@POST(BaseApi.getFoodByStore)
	@FormUrlEncoded
	Flowable<RequestListBean<FoodType>> getFoodByStore(@Field("id") int id);

	/**
	 * post请求，
	 */
	@POST(BaseApi.getAllFood)
	Flowable<RequestListBean<Food>> getAllFood();
	/**
	 * post请求，获取某商家的食物列表
	 */
	@POST(BaseApi.getFoodByStore2)
	@FormUrlEncoded
	Flowable<RequestListBean<Food>> getFoodByStore2(@Field("id") int id);

	/**
	 * post请求，查询食物信息
	 */
	@POST(BaseApi.searchFood)
	@FormUrlEncoded
	Flowable<RequestListBean<Food>> searchFood(@Field("storeId") int storeId,@Field("con") String con);

	/**
	 * post请求，删除食物信息
	 */
	@POST(BaseApi.deleteFood)
	@FormUrlEncoded
	Flowable<RequestBean> deleteFood(@Field("id") int id);

	/**
	 * post请求，获取用户信息
	 */
	@POST(BaseApi.getUser)
	Flowable<RequestListBean<UserBean>> getUser();



	/**
	 * post请求，获取
	 */
	@POST(BaseApi.getFoodById)
	@FormUrlEncoded
	Flowable<RequestBean<Food>> getFoodById(@Field("id") int id);

	/**
	 * post请求，我的订单
	 */
	@POST(BaseApi.getMyOrder)
	@FormUrlEncoded
	Flowable<RequestListBean<SureOrder>> getMyOrder(@Field("userId") int userId);

	/**
	 * post请求，店内订单
	 */
	@POST(BaseApi.getStoreOrder)
	@FormUrlEncoded
	Flowable<RequestListBean<SureOrder>> getStoreOrder(@Field("storeId") int storeId);


	/**
	 * post请求，更新订单信息
	 */
	@POST(BaseApi.updateStatus) @Headers("Content-Type:application/json; charset=utf-8")
	Flowable<RequestBean> addOrderStatus(@Body JsonObject json);

	/**
	 * post请求，保存该账号下对某个商家的操作：评论
	 */
	@POST(BaseApi.savePing)
	Flowable<RequestBean> savePing(@Body JsonObject json);


	/**
	 * post请求，
	 */
	@POST(BaseApi.getPing)
	@FormUrlEncoded
	Flowable<RequestListBean<PingBean>> getPing(@Field("storeId") int storeId);


	/**
	 * post请求，增加食物
	 */
	@POST(BaseApi.addFood) @Headers("Content-Type:application/json; charset=utf-8")
	Flowable<RequestBean> addFood(@Body JsonObject json);

	/**
	 * post请求，增加
	 */
	@POST(BaseApi.addSale) @Headers("Content-Type:application/json; charset=utf-8")
	Flowable<RequestBean> addSale(@Body String str);



	/**
	 * post请求，加入订单
	 */
	@POST(BaseApi.addToDing) @Headers("Content-Type:application/json; charset=utf-8")
	Flowable<RequestBean> addToDing(@Body JsonObject json);


	/**
	 * post请求，删除orderinfo表中某用户的数据
	 */
	@POST(BaseApi.deletebyzhuo)
	@FormUrlEncoded
	Flowable<RequestBean> deleteOrderById(@Field("userId") int userId);


	/**
	 * post请求，根据用户Id获取订单
	 */
	@POST(BaseApi.getCaiListById)
	@FormUrlEncoded
	Flowable<RequestListBean<OrderInfo>> getOrderByUserId(@Field("userId") int userId);

	/**
	 * post请求 下单
	 */
	@POST(BaseApi.addToSure) @Headers("Content-Type:application/json; charset=utf-8")
	Flowable<RequestBean> addToSure(@Body JsonObject json);

	/**
	 * post请求，
	 */
	@POST(BaseApi.getSaleData)
	@FormUrlEncoded
	Flowable<RequestListBean<SaleBean>> getSaleData(@Field("storeId") int storeId);

	/**
	 * post请求，
	 */
	@POST(BaseApi.getFood)
	@FormUrlEncoded
	Flowable<RequestListBean<Food>> getRecFood(@Field("id") int id);

//	/**
////	 * post请求，
////	 */
////	@POST(BaseApi.getFood)
////	Flowable<RequestListBean<Food>> getRecFood();

	/**
	 * post请求，
	 */
	@POST(BaseApi.addCaipin) @Headers("Content-Type:application/json; charset=utf-8")
	Flowable<RequestBean> addrec(@Body JsonObject json);

}
