package com.hehe.demo.test1.excel.fastexcel;

import java.io.IOException;
import java.util.Objects;

/**
 * Date: 2017/10/11
 * Time: 上午11:33
 * Author: xieqinghe .
 */
public class Border {
    protected static final Border NONE;
    private final BorderElement left;
    private final BorderElement right;
    private final BorderElement top;
    private final BorderElement bottom;
    private final BorderElement diagonal;

    Border(BorderElement left, BorderElement right, BorderElement top, BorderElement bottom, BorderElement diagonal) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.diagonal = diagonal;
    }

    static Border fromStyleAndColor(String style, String color) {
        BorderElement element = new BorderElement(style, color);
        return new Border(element, element, element, element, BorderElement.NONE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(new Object[]{this.left, this.right, this.top, this.bottom, this.diagonal});
    }

    @Override
    public boolean equals(Object obj) {
        boolean result;
        if(obj != null && obj.getClass() == this.getClass()) {
            Border other = (Border)obj;
            result = Objects.equals(this.left, other.left) && Objects.equals(this.right, other.right) && Objects.equals(this.top, other.top) && Objects.equals(this.bottom, other.bottom) && Objects.equals(this.diagonal, other.diagonal);
        } else {
            result = false;
        }

        return result;
    }

    void write(Writer w) throws IOException {
        w.append("<border>");
        this.left.write("left", w);
        this.right.write("right", w);
        this.top.write("top", w);
        this.bottom.write("bottom", w);
        this.diagonal.write("diagonal", w);
        w.append("</border>");
    }

    static {
        NONE = new Border(BorderElement.NONE, BorderElement.NONE, BorderElement.NONE, BorderElement.NONE, BorderElement.NONE);
    }
}

