package edu.school.e_EducationSystem.services;

import edu.school.e_EducationSystem.enums.EmailTemplateName;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class EmailService {

    private JavaMailSender mailSender;
    private SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(String to , String username, EmailTemplateName emailTemplate
            , String confirmationUrl
            , String activationCode, String subject) throws MessagingException {

        String templateName;
        if (emailTemplate==null){
            templateName="confirm-email";
        }else {
            templateName=emailTemplate.name();
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper =new MimeMessageHelper(mimeMessage
                ,MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name());

        Map<String,Object> prorieties = new HashMap<>();
        prorieties.put("username",username);
        prorieties.put("confirmationUrl",confirmationUrl);
        prorieties.put("activation_code",activationCode);

        Context context = new Context();
        context.setVariables(prorieties);

        helper.setFrom("othmanzarrouk30@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateName,context);

        helper.setText(template,true);

        mailSender.send(mimeMessage);
        System.out.println("Done mail");

    }





}
