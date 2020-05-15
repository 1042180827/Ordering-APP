package com.stone.service.Impl;

import com.stone.bean.AdviceBean;
import com.stone.mapper.TalkMapper;

import com.stone.service.TalkService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TalkServiceImpl implements TalkService {

	@Resource
	private TalkMapper pingMapper;

	@Override
	public int saveTalk(AdviceBean talk) {
		int flag = pingMapper.insertUseGeneratedKeys(talk);
		return flag;
	}

	/**
	 * 获取
	 * @return
	 */
	@Override
	public List<AdviceBean> getTalk(int id) {
		Example example = new Example(AdviceBean.class);
		example.createCriteria().andEqualTo("storeId",id);
		return pingMapper.selectByExample(example);
	}

}
