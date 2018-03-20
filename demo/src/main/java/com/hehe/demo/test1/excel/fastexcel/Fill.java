package com.hehe.demo.test1.excel.fastexcel;

/**
 * Date: 2017/10/11
 * Time: 上午11:08
 * Author: xieqinghe .
 */

import java.io.IOException;
import java.util.Objects;

public class Fill {
    protected static final Fill NONE = new Fill("none", (String)null, true);
    protected static final Fill GRAY125 = new Fill("gray125", (String)null, true);
    private final String patternType;
    private final String colorRgb;
    private final boolean fg;

    Fill(String patternType, String colorRgb, boolean fg) {
        this.patternType = patternType;
        this.colorRgb = colorRgb;
        this.fg = fg;
    }

    static Fill fromColor(String fgColorRgb) {
        return fromColor(fgColorRgb, true);
    }

    static Fill fromColor(String colorRgb, boolean fg) {
        return new Fill("solid", colorRgb, fg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(new Object[]{this.patternType, this.colorRgb, Boolean.valueOf(this.fg)});
    }

    @Override
    public boolean equals(Object obj) {
        boolean result;
        if(obj != null && obj.getClass() == this.getClass()) {
            Fill other = (Fill)obj;
            result = Objects.equals(this.patternType, other.patternType) && Objects.equals(this.colorRgb, other.colorRgb) && Objects.equals(Boolean.valueOf(this.fg), Boolean.valueOf(other.fg));
        } else {
            result = false;
        }

        return result;
    }

    void write(Writer w) throws IOException {
        w.append("<fill><patternFill patternType=\"").append(this.patternType).append('"');
        if(this.colorRgb == null) {
            w.append("/>");
        } else {
            w.append("><").append(this.fg?"fg":"bg").append("Color rgb=\"").append(this.colorRgb).append("\"/></patternFill>");
        }

        w.append("</fill>");
    }
}