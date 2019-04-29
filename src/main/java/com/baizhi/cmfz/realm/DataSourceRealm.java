package com.baizhi.cmfz.realm;

import com.baizhi.cmfz.dao.AdminDao;
import com.baizhi.cmfz.entity.Admin;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DataSourceRealm extends AuthenticatingRealm {
    @Autowired
    private AdminDao adminDao;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("-----------------进入了自定义规则--------------------");

        // token中有输入的账号密码  能获取到账号
        String username = (String) authenticationToken.getPrincipal();
        System.out.println("-----------------获取的username" + username);

        // 从数据库中获取到数据
        Admin admin = new Admin();
        admin.setUsername(username);

        List<Admin> list = adminDao.getAllByLimit(0, 2, admin);


        // 进行数据判断，如果相同就封装信息
    if (list.size() != 0) {
            Admin one = list.get(0);
            one.setId(1);

            System.out.println("one = " + one);
            // 把从数据库查出来的数据封装进info中 最后一个参数是当前类的名字
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(one.getUsername(), one.getPassword(), this.getName());

            return info;
        }
        return null;
    }
}
