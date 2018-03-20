package com.hehe.demo.test1.annotations;

import org.springframework.stereotype.Component;
/**
 * @author xieqinghe .
 * @date 2017/11/23 下午1:54
 * @email qinghe101@qq.com
 */
@Component
public class LogSave {

    public void save1(TestController.Add add, String type, String ret){
        System.out.println("r:"+add.toString()+"    "+type);
        System.out.println("return: "+ret);
    }

    public void save2(){
        System.out.println("save2");
    }


    public void save3(TestController.AddList list,String type,String ret){
        System.out.println("log: :"+list.toString()+"    "+type);
        System.out.println("log  :return: "+ret);
        System.out.println("log  :save3");
    }
}
