package com.stone.service;

import com.stone.bean.Caipin;
import com.stone.bean.UserBean;

import java.util.List;

public interface CaipinService {

    List<Caipin> getCaipinById(int id);
    int addCaipin(Caipin caipin);
}
