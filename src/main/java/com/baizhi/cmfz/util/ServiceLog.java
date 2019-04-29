package com.baizhi.cmfz.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lbypc on 2019/1/10.
 * 注解在属性和方法上  标记当前方法的作用  用于日志记录
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceLog {
    String value() default "";
}