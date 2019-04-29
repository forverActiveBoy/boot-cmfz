package com.baizhi.cmfz.dao;

import com.baizhi.cmfz.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface MenuDao extends BaseMapper<Menu> {
    List<Menu> getAll();
}
