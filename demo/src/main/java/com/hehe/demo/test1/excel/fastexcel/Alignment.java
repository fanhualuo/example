package com.hehe.demo.test1.excel.fastexcel;

import java.io.IOException;
import java.util.Objects;

/**
 * Date: 2017/10/11
 * Time: 上午11:23
 * Author: xieqinghe .
 */
public class Alignment {
    private final String horizontal;
    private final String vertical;
    private final boolean wrapText;

    Alignment(String horizontal, String vertical, boolean wrapText) {
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.wrapText = wrapText;
    }

    @Override
    public int hashCode() {
        return Objects.hash(new Object[]{this.horizontal, this.vertical, Boolean.valueOf(this.wrapText)});
    }

    @Override
    public boolean equals(Object obj) {
        boolean result;
        if(obj != null && obj.getClass() == this.getClass()) {
            Alignment other = (Alignment)obj;
            result = Objects.equals(this.horizontal, other.horizontal) && Objects.equals(this.vertical, other.vertical) && Objects.equals(Boolean.valueOf(this.wrapText), Boolean.valueOf(other.wrapText));
        } else {
            result = false;
        }

        return result;
    }

    void write(Writer w) throws IOException {
        w.append("<alignment");
        if(this.horizontal != null) {
            w.append(" horizontal=\"").append(this.horizontal).append('"');
        }

        if(this.vertical != null) {
            w.append(" vertical=\"").append(this.vertical).append('"');
        }

        if(this.wrapText) {
            w.append(" wrapText=\"").append(Boolean.toString(this.wrapText)).append('"');
        }

        w.append("/>");
    }
}

