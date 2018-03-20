package com.hehe.demo.test1.temp;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 活跃用户划分到产业
 *
 * @author xieqinghe .
 * @date 2018/1/11 上午10:22
 * @email xieqinghe@terminus.io
 */
@Data
public class HaierDataActiveUser implements Serializable {
    private static final long serialVersionUID = -2899911084060958078L;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 业务日期 yyyy-MM-dd
     */
    private String bizDate;

    /**
     * 登录用户ID
     */
    private Long userId;

    /**
     * 帖子所属产业
     */
    private String industry;


    /**
     * 创建时间
     */
    private Date createdAt;
}
