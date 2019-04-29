package com.baizhi.cmfz.controller;

import com.baizhi.cmfz.service.AdminLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/adminLog")
public class AdminLogController {
    @Autowired
    private AdminLogService adminLogService;

    @RequestMapping("/show")
    @ResponseBody
    public Map showAllLog(int page, int rows) {
        System.out.println("adminLogService = " + adminLogService.getAll(rows, page));
        return adminLogService.getAll(rows, page);
    }

    @RequestMapping("/multiDel")
    @ResponseBody
    public boolean multiDel(Integer[] ids) {
        return adminLogService.multiDel(ids);
    }
}
