package com.hehe.demo.test1.annotations;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xieqinghe .
 * @date 2017/11/23 下午1:52
 * @email qinghe101@qq.com
 */
@Slf4j
@RestController
public class TestController {


    /**
     * 使用注解
     * 传递json
     */
    //@Annotation(processor =LogSave.class,method = "save1")
    @PostMapping("/test/{type}")
    @ResponseBody
    public String testAnnotations(@RequestBody Add add, @PathVariable String type) {
        log.info("入参1{},入参2{}", add.toString(), type);
        return "testAnnotations";
    }

    /**
     * 使用注解
     * 传递json
     */
    //@Annotation(processor =LogSave.class,method = "save1")
    @PostMapping("/test2/{type}")
    @ResponseBody
    public String testAnnotations2(@RequestBody List<String> list, @PathVariable String type) {
        log.info("入参1{},入参2{}", list.toString(), type);
        return "testAnnotations";
    }

    /**
     * 使用注解
     * 传递json
     */

    @PostMapping("/test3/{type}")
    @ResponseBody
    @Annotation(processor = LogSave.class, method = "save3")
    public String testAnnotations3(@RequestBody AddList list, @PathVariable String type) {
        log.info("入参1:{} ,入参2:{}", list.toString(), type);
        return "testAnnotations3";
    }

    @Data
    public static class Add {
        int a;
        String b;
    }

    @Data
    public static class AddList {
        List<Add> list;
    }


}
