package com.hehe.demo.test1.excel.fastexcel;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Date: 2017/10/11
 * Time: 上午11:30
 * Author: xieqinghe .
 */
public class StyleSetter {
    private final Range range;
    private String valueFormatting;
    private String fillColor;
    private String alternateShadingFillColor;
    private boolean bold;
    private boolean italic;
    private String fontName;
    private BigDecimal fontSize;
    private String fontColor;
    private String horizontalAlignment;
    private String verticalAlignment;
    private boolean wrapText;
    private String borderStyle;
    private String borderColor;

    StyleSetter(Range range) {
        this.range = range;
    }

    public StyleSetter format(String numberingFormat) {
        this.valueFormatting = numberingFormat;
        return this;
    }

    public StyleSetter fillColor(String rgb) {
        this.fillColor = rgb;
        return this;
    }

    public StyleSetter shadeAlternateRows(String rgb) {
        this.alternateShadingFillColor = rgb;
        return this;
    }

    public StyleSetter fontColor(String rgb) {
        this.fontColor = rgb;
        return this;
    }

    public StyleSetter fontName(String name) {
        this.fontName = name;
        return this;
    }

    public StyleSetter fontSize(BigDecimal size) {
        this.fontSize = size;
        return this;
    }

    public StyleSetter fontSize(int size) {
        this.fontSize = BigDecimal.valueOf((long)size);
        return this;
    }

    public StyleSetter bold() {
        this.bold = true;
        return this;
    }

    public StyleSetter italic() {
        this.italic = true;
        return this;
    }

    public StyleSetter horizontalAlignment(String alignment) {
        this.horizontalAlignment = alignment;
        return this;
    }

    public StyleSetter verticalAlignment(String alignment) {
        this.verticalAlignment = alignment;
        return this;
    }

    public StyleSetter wrapText(boolean wrapText) {
        this.wrapText = wrapText;
        return this;
    }

    public StyleSetter borderStyle(String borderStyle) {
        this.borderStyle = borderStyle;
        return this;
    }

    public StyleSetter borderColor(String borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public StyleSetter merge() {
        this.range.merge();
        return this;
    }

    public void set() {
        Alignment alignment;
        if(this.horizontalAlignment == null && this.verticalAlignment == null && !this.wrapText) {
            alignment = null;
        } else {
            alignment = new Alignment(this.horizontalAlignment, this.verticalAlignment, this.wrapText);
        }

        Font font;
        if(!this.bold && !this.italic && this.fontColor == null && this.fontName == null && this.fontSize == null) {
            font = Font.DEFAULT;
        } else {
            font = Font.build(this.bold, this.italic, this.fontName, this.fontSize, this.fontColor);
        }

        Fill fill;
        if(this.fillColor == null) {
            fill = Fill.NONE;
        } else {
            fill = Fill.fromColor(this.fillColor);
        }

        Border border;
        if(this.borderStyle == null && this.borderColor == null) {
            border = Border.NONE;
        } else {
            border = Border.fromStyleAndColor(this.borderStyle, this.borderColor);
        }

        Set<Integer> currentStyles = this.range.getStyles();
        Map<Integer, Integer> newStyles = (Map)currentStyles.stream().collect(Collectors.toMap(Function.identity(), (s) -> {
            return Integer.valueOf(this.range.getWorksheet().getWorkbook().mergeAndCacheStyle(s.intValue(), this.valueFormatting, font, fill, border, alignment));
        }));
        this.range.applyStyle(newStyles);
        if(this.alternateShadingFillColor != null) {
            this.range.shadeAlternateRows(Fill.fromColor(this.alternateShadingFillColor, false));
        }

    }
}

