package com.alisimsek.HumorousBlog.email;

import com.alisimsek.HumorousBlog.configuration.HumorousProperties;
import com.alisimsek.HumorousBlog.shared.Messages;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
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
        mailSender.setPort(humorousProperties.getEmail().port());
        mailSender.setUsername(humorousProperties.getEmail().username());
        mailSender.setPassword(humorousProperties.getEmail().password());

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable","true");
    }



    //Gönderilecek mail bilgilerinin generate edilmesi
    public void sendActivationMail(String mail, String activationToken) {
        String activationEmail = """
            <html>
                <body>
                    <h1>${title}</h1>
                    <a href="${url}">${clickHere}</a>
                </body>
            </html>
            """;
        var activaitonUrl = humorousProperties.getClient().host() + "/activation/" + activationToken;
        var title = Messages.getMessageForLocale("humorous.mail.user.created.title", LocaleContextHolder.getLocale());
        var clickHere = Messages.getMessageForLocale("humorous.mail.click.here",LocaleContextHolder.getLocale());

        var mailBody = activationEmail
                .replace("${url}",activaitonUrl)
                .replace("${title}",title)
                .replace("${clickHere}",clickHere);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage,"UTF-8");

        try {
            message.setFrom(humorousProperties.getEmail().from());
            message.setTo(mail);
            message.setSubject(title);
            message.setText(mailBody,true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        this.mailSender.send(mimeMessage);
    }
}
