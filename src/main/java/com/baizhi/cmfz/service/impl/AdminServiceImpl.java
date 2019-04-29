package com.baizhi.cmfz.service.impl;

import com.baizhi.cmfz.entity.Admin;
import com.baizhi.cmfz.dao.AdminDao;
import com.baizhi.cmfz.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * (Admin)表服务实现类
 *
 * @author myself
 * @since 2019-01-06 14:19:14
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminDao adminDao;

    /**
     * 通过ID查询单条数据
     */
    @Override
    public Admin getById(Integer id) {
        return this.adminDao.getById(id);
    }

    /**
     * 通过实体作为筛选条件分页查询数据
     */
    @Override
    public Map getAllByLimit(int page, int rows, Admin admin) {
        int start = (page - 1) * rows;
        Map map = new HashMap();
        List<Admin> adminList = adminDao.getAllByLimit(start,rows,admin);
        int count = adminDao.getCount(admin);

        map.put("rows", adminList);
        map.put("total", count);
        return map;
    }

    /**
     * 新增数据
     */
    @Override
    public void insert(Admin admin) {
        this.adminDao.insert(admin);
    }

    /**
     * 修改数据
     */
    @Override
    public void update(Admin admin) {
        this.adminDao.update(admin);
    }

    /**
     * 通过主键删除数据
     */
    @Override
    public void deleteById(Integer id) {
        this.adminDao.deleteById(id);
    }

    /**
     * 通过主键批量删除数据
     */
    @Override
    public void deleteByIds(Integer[] ids) {
        this.adminDao.deleteByIds(ids);
    }

    @Override
    public boolean login(Admin admin) {
        try {
            if (adminDao.getOne(admin) != null) {

//                登录的时候吧用户信息扔seesion里面
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = requestAttributes.getRequest();
                HttpSession session = request.getSession();

                session.setAttribute("admin", adminDao.getOne(admin));

            }
            return adminDao.getOne(admin) == null ? false : true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void logout() {
        // 获取session，修改里面的信息
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpSession session = request.getSession();

        // 获取session
        Admin admin = (Admin) session.getAttribute("admin");

        session.setAttribute("admin", admin.getUsername());
    }

    /**
     * shiro认证
     */
    @Override
    public String loginByShiro(String username, String password) {
        //认证
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username, password));
        } catch (UnknownAccountException e) {
            System.out.println("账号未知");
            return "redirect:/login.jsp";
        }catch (IncorrectCredentialsException e){
            System.out.println("密码错误");
            return "redirect:/login.jsp";
        }
        return "redirect:/main/main.jsp";
    }
}