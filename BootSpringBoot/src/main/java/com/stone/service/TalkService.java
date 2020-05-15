package com.stone.service;


import com.stone.bean.AdviceBean;

import java.util.List;

public interface TalkService {

	int saveTalk(AdviceBean ping);

	List<AdviceBean> getTalk(int storeId);

}
