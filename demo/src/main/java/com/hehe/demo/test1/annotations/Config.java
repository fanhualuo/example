package com.hehe.demo.test1.annotations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 配置类，启用注解
 * @author xieqinghe .
 * @date 2017/11/23 下午1:50
 * @email qinghe101@qq.com
 */
@Configuration
//启动AspectJ自动代理
@EnableAspectJAutoProxy
public class Config {
    @Bean
    AnnotationAspect annotationAspect(){
        return new AnnotationAspect();
    }
}
