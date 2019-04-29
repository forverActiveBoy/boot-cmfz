package com.baizhi.cmfz.dao;

import com.baizhi.cmfz.entity.Province;
import com.baizhi.cmfz.entity.Sex;
import com.baizhi.cmfz.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface UserDao extends BaseMapper<User> {

    /**
     * 查询近n天的新注册的用户数量
     */
    public List<User> getNewCreate(int day);

    /**
     * 统计性别相应人数
     */
    List<Sex> getCountBySex();

    List<Province> getProvince();
}
