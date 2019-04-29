package com.baizhi.cmfz.controller;

import com.baizhi.cmfz.entity.Banner;
import com.baizhi.cmfz.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @RequestMapping("/show")
    public @ResponseBody
    Map showBanner(int rows, int page) {
        return bannerService.getAllBanner(rows, page);
    }

    @RequestMapping("/insert")
    public @ResponseBody
    boolean insertNew(Banner banner) {
        banner.setBannerId(0);
        banner.setBannerDate(new Date());

        return bannerService.insertNew(banner);
    }

    @RequestMapping("/update")
    public @ResponseBody
    boolean updateBanner(Banner banner) {
        return bannerService.updateBanner(banner);
    }

    /**
     * 批量删除，做假删除
     * @param ids
     * @return
     */
    @RequestMapping("/multiDel")
    public @ResponseBody
    boolean MultiDelete(int[] ids) {
        return bannerService.multiDelete(ids);
    }
}
