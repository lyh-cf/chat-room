package com.utils;
import com.sun.mail.util.MailSSLSocketFactory;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.Random;

public class SendEmail {
    public static String s1;
    public static String send(String email) throws GeneralSecurityException {
        // 收件人电子邮箱
        String to = email;
        // 发件人电子邮箱
        String from = "1470511515@qq.com";
        // 指定发送邮件的主机为 smtp.qq.com
        String host = "smtp.qq.com"; //QQ 邮件服务器
        // 获取系统属性
        Properties properties = System.getProperties();
        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        // 关于QQ邮箱，还要设置SSL加密，加上以下代码即可
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("1470511515@qq.com", "uubegkbqeitafiec"); //发件人邮件用户名、密码
            }
        });

        try{
             // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);
             // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));
             // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: 头部头字段
            // message.setSubject("This is the Subject Line!");
            message.setSubject("验证码(请注意验证码有效时长为1分钟)");
            // 设置消息体
            Random random = new Random();
            int number = random.nextInt(1000000);
             s1=""+number;
            message.setText(s1);
            // 发送消息
            Transport.send(message);
            return s1;
        }catch (MessagingException mex) {
          return "-1";
        }

    }
}
