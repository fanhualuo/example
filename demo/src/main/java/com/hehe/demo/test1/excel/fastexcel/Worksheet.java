package com.hehe.demo.test1.excel.fastexcel;

/**
 * Date: 2017/10/11
 * Time: 上午11:05
 * Author: xieqinghe .
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Worksheet {
    public static final int MAX_ROWS = 1048576;
    public static final int MAX_COLS = 16384;
    private final Workbook workbook;
    private final String name;
    private final List<Cell[]> rows = new ArrayList();
    private final Set<Range> mergedRanges = new HashSet();
    private final List<AlternateShading> alternateShadingRanges = new ArrayList();
    private boolean finished;

    Worksheet(Workbook workbook, String name) {
        this.workbook = (Workbook)Objects.requireNonNull(workbook);
        this.name = (String)Objects.requireNonNull(name);
    }

    public String getName() {
        return this.name;
    }

    public Workbook getWorkbook() {
        return this.workbook;
    }

    Cell cell(int r, int c) {
        if(r >= 0 && r < 1048576 && c >= 0 && c < 16384) {
            while(r >= this.rows.size()) {
                this.rows.add(null);
            }

            Cell[] row = (Cell[])this.rows.get(r);
            int columns;
            if(row == null) {
                columns = Math.max(c + 1, r > 0 && this.rows.get(r - 1) != null?((Cell[])this.rows.get(r - 1)).length:c + 1);
                row = new Cell[columns];
                this.rows.set(r, row);
            } else if(c >= row.length) {
                columns = Math.max(c + 1, r > 0 && this.rows.get(r - 1) != null?((Cell[])this.rows.get(r - 1)).length:c + 1);
                Cell[] tmp = new Cell[columns];
                System.arraycopy(row, 0, tmp, 0, row.length);
                row = tmp;
                this.rows.set(r, tmp);
            }

            if(row[c] == null) {
                row[c] = new Cell();
            }

            return row[c];
        } else {
            throw new IllegalArgumentException();
        }
    }

    void merge(Range range) {
        this.mergedRanges.add(range);
    }

    void shadeAlternateRows(Range range, Fill fill) {
        this.alternateShadingRanges.add(new AlternateShading(range, this.getWorkbook().cacheAlternateShadingFillColor(fill)));
    }

    public void value(int r, int c, Object value) {
        this.cell(r, c).setValue(this.workbook, value);
    }

    public void value(int r, String[] data) throws IOException {
        Cell[] cells=new Cell[data.length];
        for (int i=0;i<data.length;i++){
            cells[i] =new Cell();
            cells[i].setValue(this.workbook,data[i]);
        }
        this.rows.add(cells);
    }

    Cell cell2(int r, int c) {
        if(r >= 0 && r < 1048576 && c >= 0 && c < 16384) {
//            if (r!=this.rows.size()){
//                throw new IllegalArgumentException();
//            }

            while(r >= this.rows.size()) {
                this.rows.add(null);
            }

            Cell[] row = this.rows.get(r);
            int columns;
            if(row == null) {
                columns = Math.max(c + 1, r > 0 && this.rows.get(r - 1) != null?((Cell[])this.rows.get(r - 1)).length:c + 1);
                row = new Cell[columns];
                this.rows.set(r, row);
            } else if(c >= row.length) {
                columns = Math.max(c + 1, r > 0 && this.rows.get(r - 1) != null?((Cell[])this.rows.get(r - 1)).length:c + 1);
                Cell[] tmp = new Cell[columns];
                System.arraycopy(row, 0, tmp, 0, row.length);
                row = tmp;
                this.rows.set(r, tmp);
            }

            if(row[c] == null) {
                row[c] = new Cell();
            }


            return row[c];
        } else {
            throw new IllegalArgumentException();
        }
    }
    public Object value(int r, int c) {
        Cell[] row = r < this.rows.size()?(Cell[])this.rows.get(r):null;
        Cell cell = row != null && c < row.length?row[c]:null;
        return cell == null?null:cell.getValue();
    }

    public void formula(int r, int c, String expression) {
        this.cell(r, c).setFormula(expression);
    }

    public StyleSetter style(int r, int c) {
        return (new Range(this, r, c, r, c)).style();
    }

    public Range range(int top, int left, int bottom, int right) {
        return new Range(this, top, left, bottom, right);
    }

    private boolean isCellInMergedRanges(int r, int c) {
        return this.mergedRanges.stream().filter((range) -> {
            return range.contains(r, c);
        }).findAny().isPresent();
    }

    private void writeCols(Writer w, int nbCols) throws IOException {
        w.append("<cols>");

        for(int c = 0; c < nbCols; ++c) {
            double maxWidth = 0.0D;

            for(int r = 0; r < this.rows.size(); ++r) {
                Object o = this.isCellInMergedRanges(r, c)?null:this.value(r, c);
                if(o != null && !(o instanceof Formula)) {
                    int length = o.toString().length();
                    maxWidth = Math.max(maxWidth, (double)((int)((double)(length * 7 + 10) / 7.0D * 256.0D)) / 256.0D);
                }
            }

            if(maxWidth > 0.0D) {
                w.append("<col min=\"").append(c + 1).append("\" max=\"").append(c + 1).append("\" width=\"").append(Math.min(255.0D, maxWidth)).append("\" customWidth=\"true\" bestFit=\"true\"/>");
            }
        }

        w.append("</cols>");
    }

    public void finish() throws IOException {
        if(!this.finished) {
            int index = this.workbook.getIndex(this);
            this.workbook.writeFile("xl/worksheets/sheet" + Integer.toString(index) + ".xml", (w) -> {
                w.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\"><dimension ref=\"A1\"/><sheetViews><sheetView workbookViewId=\"0\"/></sheetViews><sheetFormatPr defaultRowHeight=\"15.0\"/>");
                int nbCols = ((Integer)this.rows.stream().filter((r) -> {
                    return r != null;
                }).map((r) -> {
                    return Integer.valueOf(r.length);
                }).reduce(Integer.valueOf(0), Math::max)).intValue();
                if(nbCols > 0) {
                    this.writeCols(w, nbCols);
                }

                w.append("<sheetData>");

                for(int rx = 0; rx < this.rows.size(); ++rx) {
                    Cell[] row = (Cell[])this.rows.get(rx);
                    if(row != null) {
                        writeRow(w, rx, row);
                    }
                }

                w.append("</sheetData>");
                Iterator var5;
                if(!this.mergedRanges.isEmpty()) {
                    w.append("<mergeCells>");
                    var5 = this.mergedRanges.iterator();

                    while(var5.hasNext()) {
                        Range r = (Range)var5.next();
                        w.append("<mergeCell ref=\"").append(r.toString()).append("\"/>");
                    }

                    w.append("</mergeCells>");
                }

                var5 = this.alternateShadingRanges.iterator();

                while(var5.hasNext()) {
                    AlternateShading a = (AlternateShading)var5.next();
                    a.write(w);
                }

                w.append("<pageMargins bottom=\"0.75\" footer=\"0.3\" header=\"0.3\" left=\"0.7\" right=\"0.7\" top=\"0.75\"/></worksheet>");
            });
            this.rows.clear();
            this.finished = true;
        }
    }

    private static void writeRow(Writer w, int r, Cell... row) throws IOException {
        w.append("<row r=\"").append(r + 1).append("\">");

        for(int c = 0; c < row.length; ++c) {
            if(row[c] != null) {
                row[c].write(w, r, c);
            }
        }

        w.append("</row>");
    }
}

