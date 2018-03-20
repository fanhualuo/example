package com.hehe.demo.test1.excel.fastexcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * Date: 2017/10/11
 * Time: 上午11:23
 * Author: xieqinghe .
 */
final class StyleCache {
    private final ConcurrentMap<String, Integer> valueFormattings = new ConcurrentHashMap();
    private final ConcurrentMap<Font, Integer> fonts = new ConcurrentHashMap();
    private final ConcurrentMap<Fill, Integer> fills = new ConcurrentHashMap();
    private final ConcurrentMap<Border, Integer> borders = new ConcurrentHashMap();
    private final ConcurrentMap<Style, Integer> styles = new ConcurrentHashMap();
    private final ConcurrentMap<Fill, Integer> dxfs = new ConcurrentHashMap();

    StyleCache() {
        this.mergeAndCacheStyle(0, (String)null, Font.DEFAULT, Fill.NONE, Border.NONE, (Alignment)null);
        this.cacheFill(Fill.GRAY125);
    }

    private static <T> int cacheStuff(ConcurrentMap<T, Integer> cache, T t, Function<T, Integer> indexFunction) {
        Integer index = (Integer)cache.get(t);
        if(index == null) {
            synchronized(cache) {
                index = (Integer)cache.computeIfAbsent(t, indexFunction);
            }
        }

        return index.intValue();
    }

    private static <T> int cacheStuff(ConcurrentMap<T, Integer> cache, T t) {
        return cacheStuff(cache, t, (k) -> {
            return Integer.valueOf(cache.size());
        });
    }

    int cacheValueFormatting(String s) {
        return s == null?0:cacheStuff(this.valueFormattings, s, (k) -> {
            return Integer.valueOf(this.valueFormattings.size() + 165);
        });
    }

    int cacheFont(Font f) {
        return cacheStuff(this.fonts, f);
    }

    int cacheFill(Fill f) {
        return cacheStuff(this.fills, f);
    }

    int cacheBorder(Border b) {
        return cacheStuff(this.borders, b);
    }

    int cacheDxf(Fill f) {
        return cacheStuff(this.dxfs, f);
    }

    int mergeAndCacheStyle(int currentStyle, String numberingFormat, Font font, Fill fill, Border border, Alignment alignment) {
        Style original = (Style)this.styles.entrySet().stream().filter((e) -> {
            return ((Integer)e.getValue()).equals(Integer.valueOf(currentStyle));
        }).map(Entry::getKey).findFirst().orElse(null);
        Style s = new Style(original, this.cacheValueFormatting(numberingFormat), this.cacheFont(font), this.cacheFill(fill), this.cacheBorder(border), alignment);
        return cacheStuff(this.styles, s);
    }

    private static <T> void writeCache(Writer w, Map<T, Integer> cache, String name, ThrowingConsumer<Entry<T, Integer>> consumer) throws IOException {
        w.append('<').append(name).append(" count=\"").append(cache.size()).append("\">");
        List<Entry<T, Integer>> entries = new ArrayList(cache.entrySet());
        entries.sort((e1, e2) -> {
            return Integer.compare(((Integer)e1.getValue()).intValue(), ((Integer)e2.getValue()).intValue());
        });
        Iterator var5 = entries.iterator();

        while(var5.hasNext()) {
            Entry<T, Integer> e = (Entry)var5.next();
            consumer.accept(e);
        }

        w.append("</").append(name).append('>');
    }

    void write(Writer w) throws IOException {
        w.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><styleSheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">");
        writeCache(w, this.valueFormattings, "numFmts", (e) -> {
            w.append("<numFmt numFmtId=\"").append(((Integer)e.getValue()).intValue()).append("\" formatCode=\"").append((String)e.getKey()).append("\"/>");
        });
        writeCache(w, this.fonts, "fonts", (e) -> {
            ((Font)e.getKey()).write(w);
        });
        writeCache(w, this.fills, "fills", (e) -> {
            ((Fill)e.getKey()).write(w);
        });
        writeCache(w, this.borders, "borders", (e) -> {
            ((Border)e.getKey()).write(w);
        });
        w.append("<cellStyleXfs count=\"1\"><xf numFmtId=\"0\" fontId=\"0\" fillId=\"0\" borderId=\"0\"/></cellStyleXfs>");
        writeCache(w, this.styles, "cellXfs", (e) -> {
            ((Style)e.getKey()).write(w);
        });
        writeCache(w, this.dxfs, "dxfs", (e) -> {
            w.append("<dxf>");
            ((Fill)e.getKey()).write(w);
            w.append("</dxf>");
        });
        w.append("</styleSheet>");
    }
}
