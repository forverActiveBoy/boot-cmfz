package com.baizhi.cmfz.service;

import com.baizhi.cmfz.entity.Sex;
import com.baizhi.cmfz.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, Object> showUser(int page, int rows);

    boolean multiDelete(int[] ids);

    boolean insertNew(User user);

    boolean updateUser(User user);

    List<Sex> getCountBySex();

    List<Map> getProvince();

    List getDataPage();

    Map youhua();
}
