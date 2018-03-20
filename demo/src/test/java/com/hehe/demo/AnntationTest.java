package com.hehe.demo;

import com.google.common.collect.Lists;
import com.hehe.demo.test1.annotations.TestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xieqinghe .
 * @date 2017/11/23 下午2:27
 * @email
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AnntationTest {

    @Autowired
    TestController test;
    @Test
    public void test1(){
//        String resp=test.testAnnotations(Lists.newArrayList("test"),"type1");
//        System.out.println("结果："+resp);
    }
}
