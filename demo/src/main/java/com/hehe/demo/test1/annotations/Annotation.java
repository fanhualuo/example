package com.hehe.demo.test1.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创建一个注解
 * 作用，调用某个类的方法，可用来日志记录
 * @author xieqinghe .
 * @date 2017/11/23 下午1:40
 * @email qinghe101@qq.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Annotation {
    Class processor();
    String method();
}
