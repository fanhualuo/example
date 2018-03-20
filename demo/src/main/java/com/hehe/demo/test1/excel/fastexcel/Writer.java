package com.hehe.demo.test1.excel.fastexcel;

/**
 * Date: 2017/10/11
 * Time: 上午11:08
 * Author: xieqinghe .
 */

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Writer {
    private final OutputStream os;
    private final StringBuilder sb;

    Writer(OutputStream os) {
        this.os = os;
        this.sb = new StringBuilder(524288);
    }

    Writer append(String s) throws IOException {
        return this.append(s, false);
    }

    Writer appendEscaped(String s) throws IOException {
        return this.append(s, true);
    }

    private void escape(char c) {
        switch(c) {
            case '"':
                this.sb.append("&quot;");
                break;
            case '&':
                this.sb.append("&amp;");
                break;
            case '\'':
                this.sb.append("&apos;");
                break;
            case '<':
                this.sb.append("&lt;");
                break;
            case '>':
                this.sb.append("&gt;");
                break;
            default:
                if(c > 126) {
                    this.sb.append("&#x").append(Integer.toHexString(c)).append(';');
                } else {
                    this.sb.append(c);
                }
        }

    }

    private Writer append(String s, boolean escape) throws IOException {
        if(escape) {
            for(int i = 0; i < s.length(); ++i) {
                this.escape(s.charAt(i));
            }
        } else {
            this.sb.append(s);
        }

        this.check();
        return this;
    }

    private void check() throws IOException {
        if(this.sb.capacity() - this.sb.length() < 1024) {
            this.flush();
        }

    }

    Writer append(char c) throws IOException {
        this.sb.append(c);
        this.check();
        return this;
    }

    Writer append(int n) throws IOException {
        this.sb.append(n);
        this.check();
        return this;
    }

    Writer append(long n) throws IOException {
        this.sb.append(n);
        this.check();
        return this;
    }

    Writer append(double n) throws IOException {
        this.sb.append(n);
        this.check();
        return this;
    }

    void flush() throws IOException {
        this.os.write(this.sb.toString().getBytes(StandardCharsets.UTF_8));
        this.sb.setLength(0);
    }
}

