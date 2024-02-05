package com.alisimsek.HumorousBlog.email;

import com.alisimsek.HumorousBlog.configuration.HumorousProperties;
import com.alisimsek.HumorousBlog.entity.User;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSenderImpl mailSender;
    private final HumorousProperties humorousProperties;


    //mail gönderilecek adresin konfigürasyonunun yapılması
    //Ethereal mail servisi kullanılmıştır
    @PostConstruct
    public void initialize(){
        mailSender.setHost(humorousProperties.getEmail().host());
        mailSender.setPort(587);
        mailSender.setUsername(humorousProperties.getEmail().username());
        System.out.println("-----------");
        System.out.println(humorousProperties.getEmail().password());
        System.out.println("-----------");
        mailSender.setPassword(humorousProperties.getEmail().password());

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable","true");
    }

    //Gönderilecek mail bilgilerinin generate edilmesi
    public void sendActivationMail(String mail, String activationToken) {
        var activaitonUrl = humorousProperties.getClient().host() + "/activation/" + activationToken;
        SimpleMailMessage message =new SimpleMailMessage();
        message.setFrom(humorousProperties.getEmail().from());
        message.setTo(mail);
        message.setSubject("Account Activation");
        message.setText(activaitonUrl);
        this.mailSender.send(message);
    }
}
