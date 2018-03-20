package com.hehe.demo.test1.excel.fastexcel;

import java.util.Objects;

/**
 * Date: 2017/10/11
 * Time: 上午11:19
 * Author: xieqinghe .
 */
public class CachedString {
    private final String string;
    private final int index;

    CachedString(String string, int index) {
        Objects.requireNonNull(string);
        this.string = string;
        this.index = index;
    }

    String getString() {
        return this.string;
    }

    int getIndex() {
        return this.index;
    }
}

