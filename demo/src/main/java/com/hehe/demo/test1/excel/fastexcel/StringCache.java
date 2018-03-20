package com.hehe.demo.test1.excel.fastexcel;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Date: 2017/10/11
 * Time: 上午11:21
 * Author: xieqinghe .
 */
class StringCache {
    private long count;
    private final HashMap<String, CachedString> strings = new HashMap();

    StringCache() {
    }

    CachedString cacheString(String s) {
        HashMap var3 = this.strings;
        synchronized(this.strings) {
            ++this.count;
            CachedString result = (CachedString)this.strings.get(s);
            if(result == null) {
                result = new CachedString(s, this.strings.size());
                this.strings.put(s, result);
            }

            return result;
        }
    }

    void write(Writer w) throws IOException {
        w.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><sst xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\" count=\"").append(this.count).append("\" uniqueCount=\"").append(this.strings.size()).append("\">");
        Stream<String> sortedStrings = this.strings.entrySet().stream().sorted((e1, e2) -> {
            return Integer.compare(((CachedString)e1.getValue()).getIndex(), ((CachedString)e2.getValue()).getIndex());
        }).map(Map.Entry::getKey);
        Iterator it = sortedStrings.iterator();

        while(it.hasNext()) {
            w.append("<si><t>").appendEscaped((String)it.next()).append("</t></si>");
        }

        w.append("</sst>");
    }
}
