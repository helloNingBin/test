package main;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Email {
   public static void main(String[] args) {
	   // 收件人电子邮箱
	      String to = "124708709@qq.com";
	      String to2 = "417397579@qq.com";
	      // 发件人电子邮箱
	      String from = "ning5761355@126.com";
	 
	      // 指定发送邮件的主机为 localhost
	      String host = "smtp.126.com";
	 
	      // 获取系统属性
	      Properties properties = System.getProperties();
	 
	      // 设置邮件服务器
	      properties.setProperty("mail.smtp.host", host);
	      properties.put("mail.smtp.auth", "true");
	      Session session = Session.getDefaultInstance(properties,new Authenticator(){
	          public PasswordAuthentication getPasswordAuthentication()
	          {
	           return new PasswordAuthentication("ning5761355@126.com", "freedom668"); //发件人邮件用户名、密码
	          }
	         });
	   
	 
	      // 获取默认session对象
	 
	      try{
	         // 创建默认的 MimeMessage 对象
	         MimeMessage message = new MimeMessage(session);
	 
	         // Set From: 头部头字段
	         message.setFrom(new InternetAddress(from));
	 
	         // Set To: 单人发送
//	         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
	         //多人发送
	         message.addRecipients(Message.RecipientType.TO,new InternetAddress[]{new InternetAddress(to),new InternetAddress(to2)});
	 
	         // Set Subject:邮件主题
	         message.setSubject("This is the Subject Line!");
	 
	         // 设置消息体
	         message.setContent("<h1>test</h1></br></br>This is actual message","text/html");
	 
	         // 发送消息
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
}
}
