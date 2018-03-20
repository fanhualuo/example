package com.hehe.demo.test1.excel.fastexcel;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Date: 2017/10/11
 * Time: 上午11:08
 * Author: xieqinghe .
 */
public class Cell {
    private Object value;
    private int style;

    public Cell() {
    }

    void write(Writer w, int r, int c) throws IOException {
        if(this.value != null || this.style != 0) {
            w.append("<c r=\"").append(Range.colToString(c)).append(r + 1).append('"');
            if(this.style != 0) {
                w.append(" s=\"").append(this.style).append('"');
            }

            if(this.value != null && !(this.value instanceof Formula)) {
                w.append(" t=\"").append((char)(this.value instanceof CachedString?'s':'n')).append('"');
            }

            w.append(">");
            if(this.value instanceof Formula) {
                w.append("<f>").append(((Formula)this.value).getExpression()).append("</f>");
            } else if(this.value != null) {
                w.append("<v>");
                if(this.value instanceof CachedString) {
                    w.append(((CachedString)this.value).getIndex());
                } else if(this.value instanceof Integer) {
                    w.append(((Integer)this.value).intValue());
                } else if(this.value instanceof Long) {
                    w.append(((Long)this.value).longValue());
                } else if(this.value instanceof Double) {
                    w.append(((Double)this.value).doubleValue());
                } else {
                    w.append(this.value.toString());
                }

                w.append("</v>");
            }

            w.append("</c>");
        }

    }

    void setValue(Workbook wb, Object v) {
        if(v instanceof String) {
            this.value = wb.cacheString((String)v);
        } else if(v != null && !(v instanceof Number)) {
            if(v instanceof Date) {
                this.value = TimestampUtil.convertDate((Date)v);
            } else if(v instanceof LocalDateTime) {
                this.value = TimestampUtil.convertDate(Date.from(((LocalDateTime)v).atZone(ZoneId.systemDefault()).toInstant()));
            } else if(v instanceof LocalDate) {
                this.value = TimestampUtil.convertDate((LocalDate)v);
            } else {
                if(!(v instanceof ZonedDateTime)) {
                    throw new IllegalArgumentException("No supported cell type for " + v.getClass());
                }

                this.value = TimestampUtil.convertZonedDateTime((ZonedDateTime)v);
            }
        } else {
            this.value = v;
        }

    }

    Object getValue() {
        Object result;
        if(this.value instanceof CachedString) {
            result = ((CachedString)this.value).getString();
        } else {
            result = this.value;
        }

        return result;
    }

    void setFormula(String expression) {
        this.value = new Formula(expression);
    }

    int getStyle() {
        return this.style;
    }

    void setStyle(int style) {
        this.style = style;
    }
}

