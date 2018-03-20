package com.hehe.demo.test1.excel.fastexcel;
import java.io.IOException;

/**
 * Date: 2017/10/11
 * Time: 上午11:39
 * Author: xieqinghe .
 */


@FunctionalInterface
public interface ThrowingConsumer<T> {
    void accept(T var1) throws IOException;
}
