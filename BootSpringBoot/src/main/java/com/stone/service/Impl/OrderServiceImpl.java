package com.stone.service.Impl;

import com.stone.bean.Food;
import com.stone.bean.OrderBean;
import com.stone.mapper.FoodMapper;
import com.stone.mapper.OrderMapper;
import com.stone.service.OrderService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    private FoodMapper foodMapper;

    @Override
    public List<OrderBean> getOrderByMy(int id) {
        Example example = new Example(OrderBean.class);
        example.createCriteria().andEqualTo("userId", id);
        return orderMapper.selectByExample(example);
    }



    /**
     * 删除
     * @return
     */
    @Override
    public int deleteOrder(int id) {
        Example example = new Example(OrderBean.class);
        example.createCriteria().andEqualTo("userId", id);
        return orderMapper.deleteByExample(example);
    }

    /**
     * 保存
     * @param order
     * @return
     */
    @Override
    public int addOrder(OrderBean order) {
      //  Example example = new Example(OrderBean.class);
      //  example.createCriteria().andEqualTo("caipin", order.getCaipin());
//        List<OrderBean> list = orderMapper.selectByExample(example);
//        if(list!=null&&list.size()>0){
//            list.get(0).setFenshu(list.get(0).getFenshu()+1);
//            return  orderMapper.updateByPrimaryKey(list.get(0));
//        }else{
//            order.setFenshu(1);
//            return orderMapper.insertUseGeneratedKeys(order);
//        }

        return orderMapper.insertUseGeneratedKeys(order);
    }

}
