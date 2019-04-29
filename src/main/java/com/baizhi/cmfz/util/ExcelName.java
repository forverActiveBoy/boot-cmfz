package com.baizhi.cmfz.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Target(ElementType.FIELD) 只能用在属性上
 * @Retention(RetentionPolicy.RUNTIME) 范围Runtime
 * String name() default "";  代表可以写入一个值
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelName {

    String name() default "";

}
