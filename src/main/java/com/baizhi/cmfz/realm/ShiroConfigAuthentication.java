package com.baizhi.cmfz.realm;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfigAuthentication {
    /**
     * 1. filter 过滤器
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager){
        System.out.println("-----------------进入了拦截器-----------------");

        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        // 过滤链  书写过滤规则  需要拦截什么 不需要拦截什么
        Map map = new HashMap();

        // anon  匿名可以访问  不用登陆就可以方法
        // authc  认证后可以访问  登陆后才可以访问
        map.put("/login.jsp", "anon");
        map.put("admin/loginByShiro", "anon");
        map.put("/**", "anon");
        filterFactoryBean.setFilterChainDefinitionMap(map);

        // 设置安全管理器
        filterFactoryBean.setSecurityManager(securityManager);

        filterFactoryBean.setSuccessUrl("/main/main.jsp");
        filterFactoryBean.setUnauthorizedUrl("/index.jsp");

        return filterFactoryBean;
    }

    /**
     * 2. SecurityManager
     */
    @Bean
    public SecurityManager getSecurityManager(DataSourceRealm dataSourceRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(dataSourceRealm);
        return securityManager;
    }
    /**
     * 创建Realm
     * @return
     */
    @Bean
    public DataSourceRealm getDataSourceRealm(){
        return new DataSourceRealm();
    }
}
