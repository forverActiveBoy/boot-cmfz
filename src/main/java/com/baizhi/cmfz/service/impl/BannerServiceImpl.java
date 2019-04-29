package com.baizhi.cmfz.service.impl;

import com.baizhi.cmfz.dao.BannerDao;
import com.baizhi.cmfz.entity.Banner;
import com.baizhi.cmfz.service.BannerService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BannerServiceImpl implements BannerService {
    @Resource
    private BannerDao bannerDao;

    @Override
    public Map<String, Object> getAllBanner(int rows, int page) {
        Map<String, Object> map = new HashMap<String, Object>();

        IPage<Banner> iPage = new Page<Banner>(page, rows);
        IPage<Banner> iPage1 = bannerDao.selectPage(iPage, null);

        List<Banner> list = iPage1.getRecords();

        int count = bannerDao.selectCount(null);

        map.put("rows", list);
        map.put("total", count);

        return map;
    }

    @Override
    public boolean multiDelete(int[] ids) {
        try {
            for (int id : ids) {
                Banner banner = bannerDao.selectById(id);
                banner.setBannerState(banner.getBannerState() == 1 ? 2 : 1);
                bannerDao.updateById(banner);
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean insertNew(Banner banner) {
        System.out.println("banner = " + banner);
        return bannerDao.insert(banner) == 1 ? true : false;
    }

    @Override
    public boolean updateBanner(Banner banner) {
        return bannerDao.updateById(banner)==1?true:false;
    }
}
