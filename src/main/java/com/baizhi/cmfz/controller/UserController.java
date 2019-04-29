package com.baizhi.cmfz.controller;

import com.baizhi.cmfz.entity.Sex;
import com.baizhi.cmfz.entity.User;
import com.baizhi.cmfz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/show")
    public @ResponseBody
    Map showAllUser(int rows, int page) {
        return userService.showUser(page, rows);
    }

    @RequestMapping("/insert")
    public @ResponseBody
    boolean insertNew(User user) {
        return userService.insertNew(user);
    }

    @RequestMapping("/update")
    public @ResponseBody
    boolean updateBanner(User user) {
        return userService.updateUser(user);
    }

    @RequestMapping("/multiDel")
    public @ResponseBody
    boolean MultiDelete(int[] ids) {
        return userService.multiDelete(ids);
    }

    @RequestMapping("/getSex")
    public @ResponseBody
    Map getSex() {
        Map map = new HashMap();
        List<Sex> sex = userService.getCountBySex();

        map.put("boy", sex.get(1).getCount());
        map.put("girl", sex.get(0).getCount());

        return map;
    }

    @RequestMapping("/getProvince")
    @ResponseBody
    List<Map> getProvince() {
        return userService.getProvince();
    }

    @RequestMapping("/getData")
    @ResponseBody
    public List getData() {

        return userService.getDataPage();
    }

}
