package com.hehe.demo.test1.excel.fastexcel;

import java.io.IOException;
import java.util.Objects;

/**
 * Date: 2017/10/11
 * Time: 上午11:37
 * Author: xieqinghe .
 */
public class Style {
    private final int valueFormatting;
    private final int font;
    private final int fill;
    private final int border;
    private final Alignment alignment;

    Style(Style original, int valueFormatting, int font, int fill, int border, Alignment alignment) {
        this.valueFormatting = valueFormatting == 0 && original != null?original.valueFormatting:valueFormatting;
        this.font = font == 0 && original != null?original.font:font;
        this.fill = fill == 0 && original != null?original.fill:fill;
        this.border = border == 0 && original != null?original.border:border;
        this.alignment = alignment == null && original != null?original.alignment:alignment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(this.valueFormatting), Integer.valueOf(this.font), Integer.valueOf(this.fill), Integer.valueOf(this.border), this.alignment});
    }

    @Override
    public boolean equals(Object obj) {
        boolean result;
        if(obj != null && obj.getClass() == this.getClass()) {
            Style other = (Style)obj;
            result = Objects.equals(Integer.valueOf(this.valueFormatting), Integer.valueOf(other.valueFormatting)) && Objects.equals(Integer.valueOf(this.font), Integer.valueOf(other.font)) && Objects.equals(Integer.valueOf(this.fill), Integer.valueOf(other.fill)) && Objects.equals(Integer.valueOf(this.border), Integer.valueOf(other.border)) && Objects.equals(this.alignment, other.alignment);
        } else {
            result = false;
        }

        return result;
    }

    void write(Writer w) throws IOException {
        w.append("<xf numFmtId=\"").append(this.valueFormatting).append("\" fontId=\"").append(this.font).append("\" fillId=\"").append(this.fill).append("\" borderId=\"").append(this.border).append("\" xfId=\"0\"");
        if(this.alignment == null) {
            w.append("/>");
        } else {
            w.append('>');
            this.alignment.write(w);
            w.append("</xf>");
        }

    }
}
