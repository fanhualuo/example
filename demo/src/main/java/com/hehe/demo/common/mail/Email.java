package com.hehe.demo.common.mail;

import lombok.Data;

/**
 * @author hehe
 * @date 2017/8/31 下午4:12
 * @email qinghe101@qq.com
 */
@Data
public class Email {


    /** 发件人 **/
    private String fromAddress;

    /** 收件人 **/
    private String toAddress;

    /** 邮件主题 **/
    private String subject;

    /** 邮件内容 **/
    private String content;

}
