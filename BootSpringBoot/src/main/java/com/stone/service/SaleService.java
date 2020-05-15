package com.stone.service;

import com.stone.bean.SaleBean;
import com.stone.bean.SureBean;

import java.util.List;

public interface SaleService {



	int addSale(List<SaleBean> list, boolean isSave);

	List<SaleBean> getSaleData(int storeId);

	List<SaleBean> getSaleByStore(int storeId);
}
