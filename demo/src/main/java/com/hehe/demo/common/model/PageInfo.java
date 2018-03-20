package com.hehe.demo.common.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 分页页数工具类
 * @author hehe
 * @date 2017/8/12
 * @email qinghe101@qq.com
 */
public class PageInfo {
    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";
    private Integer offset;
    private Integer limit;

    public PageInfo() {
    }

    public static PageInfo of(Integer pageNo, Integer size) {
        return new PageInfo(pageNo, size);
    }

    public PageInfo(Integer pageNo, Integer size) {
        pageNo = (Integer) MoreObjects.firstNonNull(pageNo, Integer.valueOf(1));
        size = (Integer)MoreObjects.firstNonNull(size, Integer.valueOf(20));
        this.limit = size.intValue() > 0 ? size.intValue() : 20;
        this.offset = (pageNo.intValue() - 1) * size.intValue();
        this.offset = this.offset.intValue() > 0 ? this.offset.intValue() : 0;
    }

    public Integer getOffset() {
        return this.offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Map<String, Object> toMap() {
        return this.toMap((String)null, (String)null);
    }

    public Map<String, Object> toMap(String key1, String key2) {
        Map param = Maps.newHashMapWithExpectedSize(2);
        param.put(Strings.isNullOrEmpty(key1) ? "offset" : key1, this.offset);
        param.put(Strings.isNullOrEmpty(key2) ? "limit" : key2, this.limit);
        return param;
    }
}
