package com.minsu.dnews.mail;

import com.minsu.dnews.news.dto.NewsItemDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class MailSendServiceImpl implements MailSendService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String username;
    @Override
    public void sendHtmlEmail(String email, Map<String, List<NewsItemDto>> listMap) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            Context context = new Context();
            context.setVariable("themeNewsMap", listMap);

            String htmlContent = templateEngine.process("email-template", context);

            messageHelper.setFrom(username);
            messageHelper.setTo(email); //수신자
            messageHelper.setSubject(getCurrentDate());
            messageHelper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    private String getCurrentDate() {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("MM월 dd일");
        return today.format(timeFormatter) + " 뉴스입니다.";
    }
}
