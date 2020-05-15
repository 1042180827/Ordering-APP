package com.stone.service.Impl;

import com.stone.bean.OrderBean;
import com.stone.bean.SureBean;
import com.stone.mapper.OrderMapper;
import com.stone.mapper.SureMapper;
import com.stone.service.OrderService;
import com.stone.service.SureService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SureServiceImpl implements SureService {
    @Resource
    private SureMapper sureMapper;

    @Override
    public int addOrder(SureBean order,boolean isSave) {
        if(isSave){
            return sureMapper.insertUseGeneratedKeys(order);
        }else{
            return sureMapper.updateByPrimaryKey(order);
        }

    }

    @Override
    public List<SureBean> getMy(int id) {
        Example example = new Example(SureBean.class);
        example.createCriteria().andEqualTo("userId", id);
        return sureMapper.selectByExample(example);
    }

    @Override
    public List<SureBean> getStore(int storeId) {
        Example example = new Example(SureBean.class);
        example.createCriteria().orEqualTo("storeId", storeId).orEqualTo("storeId",0);
        return sureMapper.selectByExample(example);
    }
}
