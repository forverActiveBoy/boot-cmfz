package com.baizhi.cmfz.service.impl;

import com.baizhi.cmfz.dao.AdminLogDao;
import com.baizhi.cmfz.entity.AdminLog;
import com.baizhi.cmfz.service.AdminLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminLogServiceImpl implements AdminLogService {
    @Autowired
    private AdminLogDao adminLogDao;

    @Override
    public Map getAll(int rows, int page) {
        Map<String, Object> map = new HashMap<String, Object>();

        IPage<AdminLog> iPage = new Page<AdminLog>(page, rows);
        IPage<AdminLog> iPage1 = adminLogDao.selectPage(iPage, null);

        List<AdminLog> list = iPage1.getRecords();

        int count = adminLogDao.selectCount(null);

        map.put("rows", list);
        map.put("total", count);

        return map;

    }

    @Override
    public boolean multiDel(Integer[] ids) {
        try {
            for (Integer id : ids) {
                adminLogDao.delete(new QueryWrapper<AdminLog>().eq("log_id", id));
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
