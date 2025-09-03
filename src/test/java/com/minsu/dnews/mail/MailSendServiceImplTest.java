package com.minsu.dnews.mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailSendServiceImplTest {
    @Autowired
    MailSendServiceImpl mailSendService;
//    @Test
//    void sendEmailToMe() {
//        mailSendService.sendHtmlEmail();
//    }
}