package com.stone.service.Impl;


import com.stone.bean.Caipin;
import com.stone.mapper.CaipinMapper;
import com.stone.service.CaipinService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import javax.annotation.Resource;
import java.util.List;

@Service
public class CaipinServiceImpl implements CaipinService {
    @Resource
    private CaipinMapper caipinMapper;

    @Override
    public List<Caipin> getCaipinById(int id){
        Example example = new Example(Caipin.class);
        example.createCriteria().andEqualTo("userId",id);
        return caipinMapper.selectByExample(example);
    }

    @Override
    public int addCaipin(Caipin caipin) {
        return caipinMapper.insertUseGeneratedKeys(caipin);
    }
}
