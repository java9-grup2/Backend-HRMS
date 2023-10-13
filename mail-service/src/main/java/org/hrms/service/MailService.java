package org.hrms.service;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;


    public void sendActivationMail(ActivationMailModel model) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom("${java9mail}");
        helper.setTo(model.getPersonalEmail());
        helper.setSubject("Linke tiklayarak hesabinizi aktif ediniz");

        String htmlContent = "<div style=\"background-color: #007bff; color: white; text-align: center; padding: 10px; border-radius: 15px\">" +
                "<h2 style=\"margin: 0;\">Hesabınızı Aktive Edin</h2>" +
                "<p style=\"font-size: 18px;\">Hesabınızı aktive etmek için aşağıdaki linke tıklayın:</p>" +
//                "<a href=\"http://localhost:9090/api/v1/auth/activation?token=" + model.getActivationCode() + "\"" +
                "<a href=\"http://34.123.15.45/auth/activation?token=" + model.getActivationCode() + "\"" +
                " style=\"display: inline-block; padding: 10px 20px; background-color: #ff9900; color: white; text-decoration: none; border-radius: 5px;\">Aktivasyon Linki</a>" +
                "</div>";

        helper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
    }

    public void sendRegisterEmployeeMail(RegisterEmployeeMailModel model) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom("${java9mail}");
        helper.setTo(model.getPersonalEmail());
        helper.setSubject(model.getCompanyName());

        String htmlContent = "<div style=\"background-color: #007bff; color: white; text-align: center; padding: 10px; border-radius: 15px\">" +
                "<h2 style=\"margin: 0; background-color: orangered; color: white; border: 2px solid red; border-radius: 15px; \">Aramiza Hosgeldin "+model.getName().toUpperCase()+" ◕‿◕✿, Sirket hesap bilgilerin assagida paylasilmistir</h2>" +
                "<p style=\"font-size: 18px; color:white;\"> Emailin : "+model.getCompanyEmail()+"</p>" +
                "<p style=\"font-size: 18px; color:white;\"> Sifren : "+model.getPassword()+"</p>" +
                "</div>";

        helper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
    }

    public void sendApproveManagerMail(ApproveManagerMailModel model) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom("${java9mail}");
        helper.setTo(model.getPersonalEmail());
        helper.setSubject(model.getCompanyName());

        String htmlContent = "<div style=\"background-color: #007bff; color: white; text-align: center; padding: 10px; border-radius: 15px\">" +
                "<h2 style=\"margin: 0; background-color: orangered; color: white; border: 2px solid red; border-radius: 15px; \">Yonca uygulamasi deneyimine hosgeldin "+model.getName().toUpperCase()+" "+model.getSurname().toUpperCase()+". Hesabin admin tarafindan onaylandi ◕‿◕✿ Manager Bilgilerin Assagida Paylasilmistir</h2>" +
                "<p style=\"font-size: 18px;\"> Sahis Emailin : "+model.getPersonalEmail()+"</p>" +
                "<p style=\"font-size: 18px;\"> Sirket Emailin : "+model.getCompanyEmail()+"</p>" +
                "<p style=\"font-size: 18px;\"> Kullanici Adin : "+model.getUsername()+"</p>" +
                "<p style=\"font-size: 18px;\"> Sifren : "+model.getPassword()+"</p>" +
                "<p style=\"font-size: 18px;\"> Sirket Ismi : "+model.getCompanyName()+"</p>" +
                "<p style=\"font-size: 18px;\"> Sirket Vergi No : "+model.getTaxNo()+"</p>" +
                "</div>";

        helper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
    }

    public void sendForgotPasswordMail(ForgotPasswordMailModel model) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom("${java9mail}");
        helper.setTo(model.getEmail());
        helper.setSubject("Yonca Hesap Sifreniz");

        String htmlContent = "<div style=\"background-color: #007bff; color: white; text-align: center; padding: 10px; border-radius: 15px\">" +
                "<h2 style=\"margin: 0;\">Hesap Sifreniz Assagida Paylasilmistir</h2>"+
                "<p style=\"font-size: 18px; color:white; border: 1px solid black; background-color: orangered;\"> Hesap sifreniz : <span style=\"font-size: 18px; color:orangered;\">"+model.getPassword()+"</span></p>\n";


        helper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
    }

    public void sendDenyMessageToManager(ManagerDenyMailModel model) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom("${java9mail}");
        helper.setTo(model.getPersonalEmail());
        helper.setSubject("Yonca Uygulamasi Olarak Kotu Bir Haberimiz Var (·•᷄\u200Eࡇ•᷅ )");

        String htmlContent = "<div style=\"background-color: palegreen; color: darkblue; text-align: center; padding: 10px; border-radius: 15px\">" +
                "<h2 style=\"margin: 0;\">Sanırım admin bir şeyleri beğenmedi ki kayıt isteğiniz reddedildi.</h2>" +
                "<p style=\"margin: 0;\">Detaylı sorularınız için lütfen bu adrese mail gönderiniz.</p>" +
                "<p style=\"margin: 0;\">Yonca ailesi olarak iyi günler dileriz \uD83D\uDC4B</p>" +
                "</div>";
        helper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
    }
}
