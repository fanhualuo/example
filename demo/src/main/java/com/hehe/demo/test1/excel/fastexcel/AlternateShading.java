package com.hehe.demo.test1.excel.fastexcel;

/**
 * Date: 2017/10/11
 * Time: 上午11:08
 * Author: xieqinghe .
 */


import java.io.IOException;

class AlternateShading {
    private final Range range;
    private final int fill;

    AlternateShading(Range range, int fill) {
        this.range = range;
        this.fill = fill;
    }

    void write(Writer w) throws IOException {
        w.append("<conditionalFormatting sqref=\"").append(this.range.toString()).append("\"><cfRule type=\"expression\" dxfId=\"").append(this.fill).append("\" priority=\"1\"><formula>MOD(ROW(),2)</formula></cfRule></conditionalFormatting>");
    }
}

