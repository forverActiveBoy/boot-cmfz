package com.baizhi.cmfz.controller;

import com.baizhi.cmfz.entity.Admin;
import com.baizhi.cmfz.service.AdminService;
import com.baizhi.cmfz.util.ServiceLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * (Admin)表控制层
 *
 * @author myself
 * @since 2019-01-06 14:19:14
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final static Logger logger = LoggerFactory.getLogger(AdminController.class);

    /**
     * 服务对象
     */
    @Resource
    private AdminService adminService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 通过主键查询单条数据
     */
    @RequestMapping("/getOne")
    @ResponseBody
    public Admin getOne(Integer id) {
        return this.adminService.getById(id);
    }
    
    /**
     * 通过实体作为筛选条件分页查询数据
     */
    @RequestMapping("/getAllAdminByLimit")
    @ResponseBody
    public Map getAllAdminByLimit(int page, int rows, Admin admin) {
        logger.info(admin.toString());
        return this.adminService.getAllByLimit(page,rows,admin);
    }
    
    /**
     * 添加数据
     */
    @RequestMapping("/insertAdmin")
    @ResponseBody
    public boolean insertAdmin(Admin admin){
        try {
            adminService.insert(admin);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 修改数据
     */
    @RequestMapping("/updateAdmin")
    @ResponseBody
    public boolean updateAdmin(Admin admin){
        try {
            adminService.update(admin);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 通过主键删除数据
     */
    @RequestMapping("/deleteAdminById")
    @ResponseBody
    public boolean deleteAdminById(Integer id){
        try {
            adminService.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 通过主键批量删除数据
     */
    @RequestMapping("/deleteAdminByIds")
    @ResponseBody
    public boolean deleteAdminByIds(Integer[] ids){
        try {
            adminService.deleteByIds(ids);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 登录
     */
    @ServiceLog("登录系统")
    @RequestMapping("/login")
    public String login(Admin admin) {
        System.out.println("admin = " + admin);

        if (adminService.login(admin)) {
            return "redirect:/main/main.jsp";
        }

        System.out.println("返回的是false，返回了");

        request.setAttribute("error", "用户名或者密码错误");
        return "/login";
    }

    @RequestMapping("/logout")
    public String logout() {
        adminService.logout();
        return "/login";
    }

    @RequestMapping("/loginByShiro")
    public String loginByShiro(String username, String password) {
        System.out.println("jin fangfa ");

        return adminService.loginByShiro(username, password);
    }
}