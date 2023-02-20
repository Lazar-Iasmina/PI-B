package com.example.p3;

import java.io.File;
import java.util.Properties;
import javax.mail.*;
import javax.mail.Transport;
import javax.mail.internet.*;

public class JavaMailUtil {
    public boolean sendMail(String recepient,String mesaj,String about)  {
        boolean flag=false;
        try {
            //boolean flag=false;
            System.out.println("Preparing to sent email");
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", true);
            properties.put("mail.smtp.starttls.enable", true);
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            String myAccountEmail = "lazar.iasmina7@gmail.com";
            String password = "sxhzrxvewpebauts";
            String myAccountEmail1 = "lazar.iasmina7";
            //boon
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myAccountEmail1,password);
                }
            });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject(about);
            message.setText(mesaj);/*
            MimeBodyPart part1 = new MimeBodyPart();
            part1.setText("kabgfebfe");
            File file=new File("userPass.txt");
            MimeBodyPart part2 = new MimeBodyPart();
            part2.attachFile(file);
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(part1);
            mimeMultipart.addBodyPart(part2);
            message.setContent(mimeMultipart);*/

            Transport.send(message);
            flag=true;

        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("mesaj trimis");
        return flag;
    }

}
