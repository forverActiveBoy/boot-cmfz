package com.baizhi.cmfz.service;

import com.baizhi.cmfz.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {
    Map<String, Object> getAllBanner(int rows, int page);

    boolean multiDelete(int[] ids);

    boolean insertNew(Banner banner);

    boolean updateBanner(Banner banner);
}
