package com.hehe.demo.test1.excel.fastexcel;

import java.io.IOException;
import java.util.Objects;

/**
 * Date: 2017/10/11
 * Time: 上午11:33
 * Author: xieqinghe .
 */
public class BorderElement {
    protected static final BorderElement NONE = new BorderElement((String)null, (String)null);
    private final String style;
    private final String rgbColor;

    BorderElement(String style, String rgbColor) {
        this.style = style;
        this.rgbColor = rgbColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(new Object[]{this.style, this.rgbColor});
    }

    @Override
    public boolean equals(Object obj) {
        boolean result;
        if(obj != null && obj.getClass() == this.getClass()) {
            BorderElement other = (BorderElement)obj;
            result = Objects.equals(this.style, other.style) && Objects.equals(this.rgbColor, other.rgbColor);
        } else {
            result = false;
        }

        return result;
    }

    void write(String name, Writer w) throws IOException {
        w.append("<").append(name);
        if(this.style == null && this.rgbColor == null) {
            w.append("/>");
        } else {
            if(this.style != null) {
                w.append(" style=\"").append(this.style).append('"');
            }

            w.append('>');
            if(this.rgbColor != null) {
                w.append("<color rgb=\"").append(this.rgbColor).append("\"/>");
            }

            w.append("</").append(name).append(">");
        }

    }
}

