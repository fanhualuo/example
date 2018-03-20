package com.hehe.demo.common.mail;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.eventbus.Subscribe;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.Properties;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author hehe
 * @date 2017/9/1
 * @email qinghe101@qq.com
 */
@Component
@Slf4j
@ConfigurationProperties(prefix = "email")
public class MailEvent {
    @Setter
    private String host;
    @Setter
    private String username;
    @Setter
    private String password;
    @Setter
    private Integer timeout=25000;


    //发送邮件
    @Subscribe
    public  void sendMimeMail(Email mail) {
        try {
            checkNotNull(host,"email host not null");
            checkNotNull(username,"email username not null");
            checkNotNull(password,"email password not null");
            JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
            // 设定mail server
            javaMailSender.setHost(host);
            javaMailSender.setUsername(username); // 根据自己的情况,设置username
            javaMailSender.setPassword(password);// 根据自己的情况, 设置password（163邮箱的授权码）

            Properties prop = new Properties();
            prop.put(" mail.smtp.auth ", " true "); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
            prop.put(" mail.smtp.timeout ", timeout);
            prop.put("mail.smtp.socketFactory.port", "25");
            prop.put("mail.smtp.socketFactory.fallback", "false");
            javaMailSender.setJavaMailProperties(prop);

            // 建立邮件消息
            MimeMessageHelper mailMessage = new MimeMessageHelper(javaMailSender.createMimeMessage(),true, Charsets.UTF_8.toString());

            mailMessage.setFrom(mail.getFromAddress());//发件人
            mailMessage.setTo(mail.getToAddress());//收件人
            mailMessage.setSubject(mail.getSubject());//标题
            mailMessage.setText(mail.getContent());//内容

            // 发送邮件
            javaMailSender.send(mailMessage.getMimeMessage());

            //System.out.println("邮件发送成功");

        } catch (Exception e) {
            log.error("failed to handle mail, mail={}, cause:{}",
                    mail, Throwables.getStackTraceAsString(e));
        }
    }
}
