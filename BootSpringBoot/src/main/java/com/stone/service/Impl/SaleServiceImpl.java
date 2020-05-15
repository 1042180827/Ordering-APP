package com.stone.service.Impl;

import com.stone.bean.Caipin;
import com.stone.bean.SaleBean;
import com.stone.bean.SureBean;
import com.stone.mapper.SaleMapper;
import com.stone.mapper.SureMapper;
import com.stone.service.SaleService;
import com.stone.service.SureService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {
    @Resource
    private SaleMapper saleMapper;



    @Override
    public int addSale(List<SaleBean> list, boolean isSave) {
//        int storeId = list.get(0).getStoreId();
//        List<SaleBean> olist = new ArrayList<>();
//        olist.addAll(getSaleData(storeId));
//        for(int i=0;i<olist.size();i++){
//            for(int j=0;j<list.size();j++){
//                if(olist.get(i).getName().equals(list.get(j).getName())){
//                    olist.get(i).setSale(olist.get(i).getSale()+list.get(j).getSale());
//                    saleMapper.updateByPrimaryKey(olist.get(i));
//                }
//            }
//        }
        return saleMapper.insertList(list);
    }

    @Override
    public List<SaleBean> getSaleData(int storeId) {
        Example example = new Example(SaleBean.class);
        example.createCriteria().andEqualTo("storeId",storeId);
        return saleMapper.selectByExample(example);
    }

    @Override
    public List<SaleBean> getSaleByStore(int storeId) {
        Example example = new Example(SaleBean.class);
        example.createCriteria().andEqualTo("storeId",storeId);
        List<SaleBean> list = saleMapper.selectByExample(example);
        for (int i=0;i<list.size();i++)
        {
            for (int j=i+1;j<list.size();j++)
            {
                if(list.get(i).equals(list.get(j))){
                    list.get(i).setSale(list.get(i).getSale()+list.get(j).getSale());
                    list.remove(j);
                }
            }
        }
        return list;
    }

}
