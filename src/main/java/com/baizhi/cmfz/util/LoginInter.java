package com.baizhi.cmfz.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootConfiguration
public class LoginInter extends WebMvcConfigurerAdapter {
    @Autowired
    private MyInterceptor myInteceport;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*registry.addInterceptor(myInteceport).addPathPatterns("/**").excludePathPatterns("/getImage","/admin/login");
        super.addInterceptors(registry);*/
    }
}
