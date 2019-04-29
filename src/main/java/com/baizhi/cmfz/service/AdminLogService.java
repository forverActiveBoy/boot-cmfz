package com.baizhi.cmfz.service;

import java.util.Map;

public interface AdminLogService {
    Map getAll(int rows, int page);

    boolean multiDel(Integer[] ids);
}
