package com.baizhi.cmfz.util;

import com.baizhi.cmfz.dao.AdminLogDao;
import com.baizhi.cmfz.entity.Admin;
import com.baizhi.cmfz.entity.AdminLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.baizhi.cmfz.dao.AdminDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class LogAspect {
    @Autowired
    private AdminLogDao cmfzAdminLogDao;

    private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 切到方法上
     */
    @Pointcut("@annotation(com.baizhi.cmfz.util.ServiceLog)")
    public void testLog(){}

    @Around("testLog()")
    public Object testLogAdd(ProceedingJoinPoint proceedingJoinPoint){
        AdminLog cmfzAdminLog = new AdminLog();

//        1.获取正在执行的方法 的方法对象
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
//        2.通过方法对象获取方法上的注解对象
        ServiceLog annotation = method.getAnnotation(ServiceLog.class);
//        3.获取注解中的值
        String value = annotation.value();

//        1.获取request
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
//        2.通过request和工具类得到ip
        String ip = IPKit.getIpAddrByRequest(request);


        HttpSession session = null;
        Admin admin = null;
        try {
            Object proceed = proceedingJoinPoint.proceed();
            //        3.通过request可以得到session
            session = request.getSession();
            //        4.通过session可以得到session中的信息 id 和 用户名
            admin = (Admin) session.getAttribute("admin");
//            封装对象
            cmfzAdminLog.setLogAction(value);
            cmfzAdminLog.setLogDate(new Date());
            cmfzAdminLog.setLogIp(ip);
            cmfzAdminLog.setAdminId(admin.getId());
            cmfzAdminLog.setAdminUsername(admin.getUsername());
            cmfzAdminLog.setLogResult("成功");
            //添加数据库
            cmfzAdminLogDao.insert(cmfzAdminLog);
            logger.info("操作成功:"+cmfzAdminLog);
            return proceed;
        } catch (Throwable throwable) {
            logger.error("操作失败");
            return null;
        }

    }
}
