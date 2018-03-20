package com.hehe.demo.test1.excel.fastexcel;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Date: 2017/10/11
 * Time: 上午11:29
 * Author: xieqinghe .
 */
public class Range {
    private final Worksheet worksheet;
    private final int top;
    private final int left;
    private final int bottom;
    private final int right;

    Range(Worksheet worksheet, int top, int left, int bottom, int right) {
        this.worksheet = (Worksheet) Objects.requireNonNull(worksheet);
        if(top >= 0 && top < 1048576 && bottom >= 0 && bottom < 1048576) {
            if(left >= 0 && left < 16384 && right >= 0 && right < 16384) {
                this.top = top <= bottom?top:bottom;
                this.left = left <= right?left:right;
                this.bottom = bottom >= top?bottom:top;
                this.right = right >= left?right:left;
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Worksheet getWorksheet() {
        return this.worksheet;
    }

    public int getTop() {
        return this.top;
    }

    public int getLeft() {
        return this.left;
    }

    public int getBottom() {
        return this.bottom;
    }

    public int getRight() {
        return this.right;
    }

    @Override
    public int hashCode() {
        return Objects.hash(new Object[]{this.worksheet, Integer.valueOf(this.top), Integer.valueOf(this.left), Integer.valueOf(this.bottom), Integer.valueOf(this.right)});
    }

    @Override
    public boolean equals(Object obj) {
        boolean result;
        if(obj != null && obj.getClass() == this.getClass()) {
            Range other = (Range)obj;
            result = Objects.equals(this.worksheet, other.worksheet) && Objects.equals(Integer.valueOf(this.top), Integer.valueOf(other.top)) && Objects.equals(Integer.valueOf(this.left), Integer.valueOf(other.left)) && Objects.equals(Integer.valueOf(this.bottom), Integer.valueOf(other.bottom)) && Objects.equals(Integer.valueOf(this.right), Integer.valueOf(other.right));
        } else {
            result = false;
        }

        return result;
    }

    public static String colToString(int c) {
        StringBuilder sb;
        for(sb = new StringBuilder(); c >= 0; c = c / 26 - 1) {
            sb.append((char)(65 + c % 26));
        }

        return sb.reverse().toString();
    }

    @Override
    public String toString() {
        return colToString(this.left) + Integer.toString(this.top + 1) + ':' + colToString(this.right) + Integer.toString(this.bottom + 1);
    }

    public StyleSetter style() {
        return new StyleSetter(this);
    }

    public void merge() {
        this.worksheet.merge(this);
    }

    public boolean contains(int r, int c) {
        return r >= this.top && r <= this.bottom && c >= this.left && c <= this.right;
    }

    void shadeAlternateRows(Fill fill) {
        this.worksheet.shadeAlternateRows(this, fill);
    }

    Set<Integer> getStyles() {
        Set<Integer> result = new HashSet();

        for(int r = this.top; r <= this.bottom; ++r) {
            for(int c = this.left; c <= this.right; ++c) {
                result.add(Integer.valueOf(this.getWorksheet().cell(r, c).getStyle()));
            }
        }

        return result;
    }

    void applyStyle(Map<Integer, Integer> styles) {
        for(int r = this.top; r <= this.bottom; ++r) {
            for(int c = this.left; c <= this.right; ++c) {
                Cell cell = this.getWorksheet().cell(r, c);
                cell.setStyle(((Integer)styles.get(Integer.valueOf(cell.getStyle()))).intValue());
            }
        }

    }
}

