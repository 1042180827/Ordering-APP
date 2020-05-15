package com.saxiao.orderinghelpapp.http;


public class BaseApi {

    public static String token="";

	public static String BaseUrl = "";

	/**
	 * 登录
	 */
	public static final String Login = "user/userLogin";

	/**
	 * 注册个人信息
	 */
	public static final String upDateUserInfo = "user/addUser";

	/**
	 * 获取所有商家
	 */
	public static final String getAllStore = "user/get_store";

	/**
	 * 获取食物
	 */
	public static final String getFoodByStore = "food/get";

    /**
     * 获取食物
     */
    public static final String getAllFood = "food/get_all";
	/**
	 * 获取食物
	 */
	public static final String getFoodByStore2 = "food/get_store";


	/**
	 *
	 */
	public static final String getSaleData = "sale/get_store";
	/**
	 * 查询
	 */
	public static final String searchFood = "food/search";


	/**
	 * 删除
	 */
	public static final String deleteFood = "food/delete";

	/**
	 * 获取用户
	 */
	public static final String getUser = "user/get";


	/**
	 * 获取食物
	 */
	public static final String getFoodById = "food/get_id";
	/**
	 * 增加食物
	 */
	public static final String addFood = "food/add";

	/**
	 * 增加销售
	 */
	public static final String addSale = "sale/add";

	/**
	 * 获取我的订单
	 */
	public static final String getMyOrder = "sure/get_my";

	/**
	 * 获取店内订单
	 */
	public static final String getStoreOrder = "sure/get_store";

	/**
	 * 更新订单状态
	 */
	public static final String updateStatus = "sure/update";

	/**
	 * 保存该账号下对某个商家的操作：评论
	 */
	public static final String savePing = "pj/add";

	/**
	 * 获取店内评论
	 */
	public static final String getPing = "pj/get_store";

	/**
	 * 加入订单
	 */
	public static final String addToDing = "order/add_to";

	/**
	 * 删除
	 */
	public static final String deletebyzhuo = "order/delete";


	/**
	 * 获取
	 */
	public static final String getCaiListById = "order/get_id";

	/**
	 * 下单
	 */
	public static final String addToSure = "sure/add";

	/**
	 * command
	 */
	public static final String addPing = "pj/add";

	/**
	 * recommend
	 */
	public static final String addCaipin = "rec/add";

	public static final String getTaste = "rec/taste";

	public static final String getFood = "rec/recommend";



}
