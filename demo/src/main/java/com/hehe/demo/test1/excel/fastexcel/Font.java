package com.hehe.demo.test1.excel.fastexcel;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Date: 2017/10/11
 * Time: 上午11:27
 * Author: xieqinghe .
 */
public class Font {
    protected static final Font DEFAULT = build(false, false, (String)null, (BigDecimal)null, (String)null);
    private final boolean bold;
    private final boolean italic;
    private final String name;
    private final BigDecimal size;
    private final String rgbColor;

    Font(boolean bold, boolean italic, String name, BigDecimal size, String rgbColor) {
        this.bold = bold;
        this.italic = italic;
        this.name = name;
        this.size = size.setScale(2);
        this.rgbColor = rgbColor;
    }

    static Font build(boolean bold, boolean italic, String name, BigDecimal size, String rgbColor) {
        return new Font(bold, italic, name == null?"Calibri":name, size == null?BigDecimal.valueOf(11.0D):size, rgbColor == null?"000000":rgbColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(new Object[]{Boolean.valueOf(this.bold), Boolean.valueOf(this.italic), this.name, this.size, this.rgbColor});
    }

    @Override
    public boolean equals(Object obj) {
        boolean result;
        if(obj != null && obj.getClass() == this.getClass()) {
            Font other = (Font)obj;
            result = Objects.equals(Boolean.valueOf(this.bold), Boolean.valueOf(other.bold)) && Objects.equals(Boolean.valueOf(this.italic), Boolean.valueOf(other.italic)) && Objects.equals(this.name, other.name) && Objects.equals(this.size, other.size) && Objects.equals(this.rgbColor, other.rgbColor);
        } else {
            result = false;
        }

        return result;
    }

    void write(Writer w) throws IOException {
        w.append("<font>").append(this.bold?"<b/>":"").append(this.italic?"<i/>":"").append("<sz val=\"").append(this.size.toString()).append("\"/>");
        if(this.rgbColor != null) {
            w.append("<color rgb=\"").append(this.rgbColor).append("\"/>");
        }

        w.append("<name val=\"").appendEscaped(this.name).append("\"/>");
        w.append("</font>");
    }
}

