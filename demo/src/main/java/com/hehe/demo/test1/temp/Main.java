package com.hehe.demo.test1.temp;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.hehe.demo.common.util.Arguments;
import com.hehe.demo.common.util.JsonMapper;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xieqinghe .
 * @date 2018/1/23 下午2:59
 * @email xieqinghe@terminus.io
 */
public class Main {
    public static void main(String[] args) {
//        String result1="{\"errorMsg\":\"horus.task.running\"}";
//
//        String result="fdsdsaf";
//        Map<String,Object> map= JsonMapper.JSON_NON_EMPTY_MAPPER.fromJson(result, HashMap.class);
//
//        System.out.println(map.get("errorMsg"));


        System.out.println(DateTime.now().toString("yyyy-MM-dd:HH"));

    }
}
